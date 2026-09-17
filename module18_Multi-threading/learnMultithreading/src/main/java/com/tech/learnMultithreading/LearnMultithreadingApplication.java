package com.tech.learnMultithreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class LearnMultithreadingApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(LearnMultithreadingApplication.class, args);

		log.info("Before thread, Name of Thread: {}, State: {}",Thread.currentThread().getName(),Thread.currentThread().getState());

		Thread workerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				log.info("Inside the thread: {}, State: {}",Thread.currentThread().getName(),Thread.currentThread().getState());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
		});
		workerThread.start();

//		workerThread.join(); // block the main thread

//		log.info("Before thread, Name of Thread: {}, State: {}",Thread.currentThread().getName(),Thread.currentThread().getState());

		log.info("After worker thread, state of worker:{}",workerThread.getState());

		log.info("After Thread");
	}

}
