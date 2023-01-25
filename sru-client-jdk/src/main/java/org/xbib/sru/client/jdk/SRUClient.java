package org.xbib.sru.client.jdk;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xbib.sru.client.jdk.util.UrlBuilder;

public class SRUClient {

    private static final Logger logger = Logger.getLogger(SRUClient.class.getName());

    private final Builder builder;

    private final HttpClient httpClient;

    private SRUClient(Builder builder) {
        this.builder = builder;
        this.httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public void searchRetrieve(String query,
                               Integer startRecord,
                               Integer maximumRecords,
                               Consumer<InputStream> consumer) throws IOException, InterruptedException {
        UrlBuilder url = UrlBuilder.fromUrl(builder.baseURL);
        url.queryParam(SRUConstants.OPERATION_PARAMETER, "searchRetrieve");
        url.queryParam(SRUConstants.VERSION_PARAMETER, builder.version);
        url.queryParam(SRUConstants.RECORD_SCHEMA_PARAMETER, builder.recordSchema);
        url.queryParam(SRUConstants.START_RECORD_PARAMETER, Integer.toString(startRecord));
        url.queryParam(SRUConstants.MAXIMUM_RECORDS_PARAMETER, Integer.toString(maximumRecords));
        url.queryParam(SRUConstants.QUERY_PARAMETER, query);
        URI uri = URI.create(url.build().toExternalForm());
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .header("accept", "utf-8")
                .header("user-agent", builder.userAgent != null ? builder.userAgent : "xbib SRU client")
                .GET()
                .build();
        logger.log(Level.FINE, "sending " + httpRequest);
        HttpResponse<InputStream> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
        int status = httpResponse.statusCode();
        logger.log(Level.FINE, "response status = " + status + " headers = " + httpResponse.headers());
        if (status == 200) {
            InputStream inputStream = httpResponse.body();
            consumer.accept(inputStream);
        }
    }

    public static class Builder {

        private String baseURL;

        private String version;

        private String recordSchema;

        private String userAgent;

        private Builder() {
            this.version = "1.1";
            this.recordSchema = "marcxml";
        }

        public Builder setBaseURL(String baseURL) {
            this.baseURL = baseURL;
            return this;
        }

        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder setRecordSchema(String recordSchema) {
            this.recordSchema = recordSchema;
            return this;
        }

        public Builder setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public SRUClient build() {
            return new SRUClient(this);
        }
    }
}
