package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.handler.inBound.EchoServerHandler;
import netty.handler.outBound.EchoOutBoundHandler;

import java.net.InetSocketAddress;

public class NettyDemo {

    public static void main(String[] args) {

    }

    public class EchoServer{


        private int port;

        public EchoServer(int port) {
            this.port = port;
        }

        public void start(){
            EventLoopGroup group = new NioEventLoopGroup();

            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<ServerSocketChannel>() {
                        @Override
                        protected void initChannel(ServerSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler());
                            ch.pipeline().addLast(new EchoOutBoundHandler());
                        }
                    });

        }



    }
}
