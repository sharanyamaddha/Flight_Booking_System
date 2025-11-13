package com.multithreading;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Worker w=new Worker();
		ExecutorService service=Executors.newFixedThreadPool(3);
		service.execute(w);
		service.execute(w);
		service.shutdown();
		

	}

}
