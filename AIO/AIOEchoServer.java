package AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AIOEchoServer {
    public final static int port=8000;
    private AsynchronousServerSocketChannel server;
    public AIOEchoServer() throws IOException {
        server=AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port));
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new AIOEchoServer().start();
        while (true){
            Thread.sleep(1000);
        }
    }

    public void start(){
        System.out.println("服务端口为："+port);

        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                System.out.println("执行的线程为："+Thread.currentThread().getName());
                Future<Integer> writeResult=null;
                //使用future模式，接收到数据便立即返回，在返回中处理，便可异步的使用
                try {
                    byteBuffer.clear();
                    //清除上次的缓存
                    result.read(byteBuffer).get(100, TimeUnit.SECONDS);
                    byteBuffer.flip();
                    writeResult=result.write(byteBuffer);
                    //将数据立即写回给客户端
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        server.accept(null,this);
                        writeResult.get();
                        //服务器将进行下一次客户端接收的准备，
                        // 使用future.get，通过等待，保证write操作写完，再关闭
                        result.close();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("失败"+exc);
            }


        });
    }
}
