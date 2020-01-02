package AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AIOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        final AsynchronousSocketChannel client= AsynchronousSocketChannel.open();
        //建立通道
        client.connect(new InetSocketAddress("localhost", 8000), null,
                new CompletionHandler<Void, Object>() {
            //先连接，连接过程中传入一个CompletionHandler写入
                    @Override
                    public void completed(Void result, Object attachment) {
                        client.write(ByteBuffer.wrap("hello!".getBytes()),null,
                                new CompletionHandler<Integer,Object>(){
                        //开始写入，完了接着往回read
                                    @Override
                                    public void completed(Integer result, Object attachment) {
                                        try {
                                            ByteBuffer buffer=ByteBuffer.allocate(1024);
                                            client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                                             //开始读取，读取完了关闭
                                                @Override
                                                public void completed(Integer result, ByteBuffer buffer) {
                                                    buffer.flip();
                                                    System.out.println(new String(buffer.array()));
                                                    try {
                                                        client.close();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                @Override
                                                public void failed(Throwable exc, ByteBuffer attachment) {

                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void failed(Throwable exc, Object attachment) {

                                    }
                                });
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {

                    }
                });
        Thread.sleep(1000);
    }
}
