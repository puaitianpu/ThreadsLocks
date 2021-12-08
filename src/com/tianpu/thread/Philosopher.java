package com.tianpu.thread;

import java.util.Random;

public class Philosopher extends Thread {

    private Chopstick left, right;
    private Random random;

    public Philosopher(Chopstick left, Chopstick right) {
        this.left = left;
        this.right = right;
        random = new Random();
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextInt(1000));         // 思考一段时间
                synchronized (left) {                              // 拿起左边筷子
                    synchronized (right) {                         // 拿起右边筷子
                        Thread.sleep(random.nextInt(1000)); // 进餐一段时间
                    }
                }
            }
        } catch (InterruptedException e) {}
    }

}
