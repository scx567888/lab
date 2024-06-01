package cool.scx.lab.netty5.http_static_file_server;


import io.netty5.bootstrap.ServerBootstrap;
import io.netty5.channel.Channel;
import io.netty5.channel.EventLoopGroup;
import io.netty5.channel.MultithreadEventLoopGroup;
import io.netty5.channel.nio.NioHandler;
import io.netty5.channel.socket.nio.NioServerSocketChannel;
import io.netty5.handler.logging.LogLevel;
import io.netty5.handler.logging.LoggingHandler;
import io.netty5.handler.ssl.SslContext;
import io.netty5.handler.ssl.SslContextBuilder;
import io.netty5.handler.ssl.SslProvider;
import io.netty5.handler.ssl.util.SelfSignedCertificate;

 public final class HttpStaticFileServer {

     static final boolean SSL = true;
     static final int PORT = Integer.parseInt(System.getProperty("port", SSL? "8443" : "8080"));

     public static void main(String[] args) throws Exception {
         // Configure SSL.
         final SslContext sslCtx;
         if (SSL) {
             SelfSignedCertificate ssc = new SelfSignedCertificate();
             sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
                 .sslProvider(SslProvider.JDK).build();
         } else {
             sslCtx = null;
         }

         EventLoopGroup bossGroup = new MultithreadEventLoopGroup(1, NioHandler.newFactory());
         EventLoopGroup workerGroup = new MultithreadEventLoopGroup(NioHandler.newFactory());
         try {
             ServerBootstrap b = new ServerBootstrap();
             b.group(bossGroup, workerGroup)
              .channel(NioServerSocketChannel.class)
              .handler(new LoggingHandler(LogLevel.INFO))
              .childHandler(new HttpStaticFileServerInitializer(sslCtx));

             Channel ch = b.bind(PORT).asStage().get();

             System.err.println("Open your web browser and navigate to " +
                     (SSL? "https" : "http") + "://127.0.0.1:" + PORT + '/');

             ch.closeFuture().asStage().sync();
         } finally {
             bossGroup.shutdownGracefully();
             workerGroup.shutdownGracefully();
         }
     }

 }
