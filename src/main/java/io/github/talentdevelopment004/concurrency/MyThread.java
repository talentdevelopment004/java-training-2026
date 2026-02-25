package io.github.talentdevelopment004.concurrency;

public class MyThread  extends Thread{
    @Override
    public void run() {
        System.out.println("My thread is running "+this.getName());

    }
}
