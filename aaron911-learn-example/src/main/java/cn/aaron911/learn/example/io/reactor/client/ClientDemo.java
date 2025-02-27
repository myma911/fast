package cn.aaron911.learn.example.io.reactor.client;


public class ClientDemo {

    public static void main(String[] args) {
        new Thread(new NIOClient("127.0.0.1", 2333)).start();
        new Thread(new NIOClient("127.0.0.1", 2333)).start();
    }

}
