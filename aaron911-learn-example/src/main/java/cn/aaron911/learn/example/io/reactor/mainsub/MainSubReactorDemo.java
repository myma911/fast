package cn.aaron911.learn.example.io.reactor.mainsub;

import java.io.IOException;


public class MainSubReactorDemo {

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(2333)).start();
    }

}
