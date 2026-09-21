package com.tech.learnMultithreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.*;

@SpringBootApplication
@Slf4j
public class LearnMultithreadingApplication implements CommandLineRunner {

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

	@Override
	public void run(String... args) throws Exception {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
				4, 6, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.out.println("Thread Rejected:.... Retrying..");

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
						executor.submit(r);
                    }
				}
		);

		ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(6, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				System.out.println();
				return new Thread(r,"thread "+System.nanoTime());
			}
		});
		scheduledThreadPoolExecutor.schedule(new LongRunningTask("schedule task"),4,TimeUnit.SECONDS);

		for(int i = 1; i <= 20; i++){
			int task = i;
//			threadPoolExecutor.submit(new LongRunningTask(task+" "));
//			Thread.sleep(1000);
		}
		threadPoolExecutor.shutdown();
	}
}
