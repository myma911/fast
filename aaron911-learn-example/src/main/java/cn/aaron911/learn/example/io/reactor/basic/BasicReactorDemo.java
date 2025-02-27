package cn.aaron911.learn.example.io.reactor.basic;

import java.io.IOException;


public class BasicReactorDemo {

	public static void main(String[] args) throws IOException {
		new Thread(new Reactor(2333)).start();
	}

}
