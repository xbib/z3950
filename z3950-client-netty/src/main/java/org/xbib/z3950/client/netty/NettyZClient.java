package org.xbib.z3950.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import org.xbib.z3950.api.RecordListener;
import org.xbib.z3950.api.ScanListener;
import org.xbib.z3950.api.SearchListener;
import org.xbib.z3950.api.TimeoutListener;
import org.xbib.z3950.client.api.Client;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

/**
 *
 */
public class NettyZClient implements Client, Closeable {

    private final EventLoopGroup group;
    private final Bootstrap clientBootstrap;

    public NettyZClient() {
        this.group = new NioEventLoopGroup();
        clientBootstrap = new Bootstrap();
        clientBootstrap.group(group);
        clientBootstrap.channel(NioSocketChannel.class);
        clientBootstrap.remoteAddress(new InetSocketAddress("localhost", 9999));
        clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new Handler());
            }
        });
    }

    public void connect() throws InterruptedException {
        ChannelFuture channelFuture = clientBootstrap.connect().sync();
        channelFuture.channel().closeFuture().sync();

    }

    public void shutdown() throws InterruptedException {
        group.shutdownGracefully().sync();
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public int searchCQL(String query,
                         int offset,
                         int length,
                         SearchListener searchListener,
                         RecordListener recordListener,
                         TimeoutListener timoutListener) throws IOException {
        return 0;
    }

    @Override
    public int searchPQF(String query,
                         int offset,
                         int length,
                         SearchListener searchListener,
                         RecordListener recordListener,
                         TimeoutListener timeoutListener) throws IOException {
        return 0;
    }

    @Override
    public void scanPQF(String query,
                        int nTerms,
                        int step,
                        int position,
                        ScanListener scanListener,
                        TimeoutListener timeoutListener) throws IOException {

    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public String getUser() {
        return null;
    }

    @Override
    public String getPass() {
        return null;
    }

    @Override
    public long getTimeout() {
        return 0;
    }

    @Override
    public String getPreferredRecordSyntax() {
        return null;
    }

    @Override
    public String getResultSetName() {
        return null;
    }

    @Override
    public String getElementSetName() {
        return null;
    }

    @Override
    public String getEncoding() {
        return null;
    }

    @Override
    public String getFormat() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public List<String> getDatabases() {
        return null;
    }

    class Handler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        public void channelActive(ChannelHandlerContext channelHandlerContext){
            channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("Netty Rocks!", CharsetUtil.UTF_8));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause){
            cause.printStackTrace();
            channelHandlerContext.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        }
    }
}
