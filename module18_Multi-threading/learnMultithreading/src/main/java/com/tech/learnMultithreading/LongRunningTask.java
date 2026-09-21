package com.tech.learnMultithreading;

public class LongRunningTask implements Runnable{

    private String cmd;

    public LongRunningTask(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public void run() {
        System.out.println("Starting Task " +cmd+Thread.currentThread().getName());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Ending Task " +cmd+Thread.currentThread().getName());
    }
}
