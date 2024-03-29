package org.xbib.sru.client.jdk.util;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import static java.nio.charset.CodingErrorAction.REPLACE;

/**
 * See RFC 3986, RFC 1738 and <a href="http://www.lunatech-research.com/archives/2009/02/03/what-every-web-developer-must-know-about-url-encoding">...</a>.
 */
public final class UrlPercentEncoders {

    /**
     * an encoder for RFC 3986 reg-names
     */

    private static final BitSet REG_NAME_BIT_SET = new BitSet();

    private static final BitSet PATH_BIT_SET = new BitSet();
    private static final BitSet MATRIX_BIT_SET = new BitSet();
    private static final BitSet UNSTRUCTURED_QUERY_BIT_SET = new BitSet();
    private static final BitSet QUERY_PARAM_BIT_SET = new BitSet();
    private static final BitSet FRAGMENT_BIT_SET = new BitSet();

    static {
        // RFC 3986 'reg-name'. This is not very aggressive... it's quite possible to have DNS-illegal names out of this.
        // Regardless, it will at least be URI-compliant even if it's not HTTP URL-compliant.
        addUnreserved(REG_NAME_BIT_SET);
        addSubdelims(REG_NAME_BIT_SET);

        // Represents RFC 3986 'pchar'. Remove delimiter that starts matrix section.
        addPChar(PATH_BIT_SET);
        PATH_BIT_SET.clear((int) ';');

        // Remove delims for HTTP matrix params as per RFC 1738 S3.3. The other reserved chars ('/' and '?') are already excluded.
        addPChar(MATRIX_BIT_SET);
        MATRIX_BIT_SET.clear((int) ';');
        MATRIX_BIT_SET.clear((int) '=');

        /*
         * At this point it represents RFC 3986 'query'. http://www.w3.org/TR/html4/interact/forms.html#h-17.13.4.1 also
         * specifies that "+" can mean space in a query, so we will make sure to say that '+' is not safe to leave as-is
         */
        addQuery(UNSTRUCTURED_QUERY_BIT_SET);
        UNSTRUCTURED_QUERY_BIT_SET.clear((int) '+');

        /*
         * Create more stringent requirements for HTML4 queries: remove delimiters for HTML query params so that key=value
         * pairs can be used.
         */
        QUERY_PARAM_BIT_SET.or(UNSTRUCTURED_QUERY_BIT_SET);
        QUERY_PARAM_BIT_SET.clear((int) '=');
        QUERY_PARAM_BIT_SET.clear((int) '&');

        addFragment(FRAGMENT_BIT_SET);
    }

    public static PercentEncoder getRegNameEncoder() {
        return new PercentEncoder(REG_NAME_BIT_SET, StandardCharsets.UTF_8.newEncoder().onMalformedInput(REPLACE)
                .onUnmappableCharacter(REPLACE));
    }

    public static PercentEncoder getPathEncoder() {
        return new PercentEncoder(PATH_BIT_SET, StandardCharsets.UTF_8.newEncoder().onMalformedInput(REPLACE)
                .onUnmappableCharacter(REPLACE));
    }

    public static PercentEncoder getMatrixEncoder() {
        return new PercentEncoder(MATRIX_BIT_SET, StandardCharsets.UTF_8.newEncoder().onMalformedInput(REPLACE)
                .onUnmappableCharacter(REPLACE));
    }

    public static PercentEncoder getUnstructuredQueryEncoder() {
        return new PercentEncoder(UNSTRUCTURED_QUERY_BIT_SET, StandardCharsets.UTF_8.newEncoder().onMalformedInput(REPLACE)
                .onUnmappableCharacter(REPLACE));
    }

    public static PercentEncoder getQueryParamEncoder() {
        return new PercentEncoder(QUERY_PARAM_BIT_SET, StandardCharsets.UTF_8.newEncoder().onMalformedInput(REPLACE)
                .onUnmappableCharacter(REPLACE));
    }

    public static PercentEncoder getFragmentEncoder() {
        return new PercentEncoder(FRAGMENT_BIT_SET, StandardCharsets.UTF_8.newEncoder().onMalformedInput(REPLACE)
                .onUnmappableCharacter(REPLACE));
    }

    private UrlPercentEncoders() {
    }

    /**
     * Add code points for 'fragment' chars
     *
     * @param fragmentBitSet bit set
     */
    private static void addFragment(BitSet fragmentBitSet) {
        addPChar(fragmentBitSet);
        fragmentBitSet.set((int) '/');
        fragmentBitSet.set((int) '?');
    }

    /**
     * Add code points for 'query' chars
     *
     * @param queryBitSet bit set
     */
    private static void addQuery(BitSet queryBitSet) {
        addPChar(queryBitSet);
        queryBitSet.set((int) '/');
        queryBitSet.set((int) '?');
    }

    /**
     * Add code points for 'pchar' chars.
     *
     * @param bs bitset
     */
    private static void addPChar(BitSet bs) {
        addUnreserved(bs);
        addSubdelims(bs);
        bs.set((int) ':');
        bs.set((int) '@');
    }

    /**
     * Add codepoints for 'unreserved' chars
     *
     * @param bs bitset to add codepoints to
     */
    private static void addUnreserved(BitSet bs) {

        for (int i = 'a'; i <= 'z'; i++) {
            bs.set(i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            bs.set(i);
        }
        for (int i = '0'; i <= '9'; i++) {
            bs.set(i);
        }
        bs.set((int) '-');
        bs.set((int) '.');
        bs.set((int) '_');
        bs.set((int) '~');
    }

    /**
     * Add codepoints for 'sub-delims' chars
     *
     * @param bs bitset to add codepoints to
     */
    private static void addSubdelims(BitSet bs) {
        bs.set((int) '!');
        bs.set((int) '$');
        bs.set((int) '&');
        bs.set((int) '\'');
        bs.set((int) '(');
        bs.set((int) ')');
        bs.set((int) '*');
        bs.set((int) '+');
        bs.set((int) ',');
        bs.set((int) ';');
        bs.set((int) '=');
    }
}
