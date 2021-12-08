package com.tianpu.thread;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread {

    private ReentrantLock leftChopstick, rightChopstick;
    private Random random;

    public Philosopher(ReentrantLock leftChopstick, ReentrantLock rightChopstick) {
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        random = new Random();
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextInt(1000)); // 思考一段时间
                leftChopstick.lock();
                try {
                    if (rightChopstick.tryLock(1000, TimeUnit.MILLISECONDS)) {
                        // 获取右手边的筷子
                        try {
                            Thread.sleep(random.nextInt(1000)); // 进餐一段时间
                        } finally {
                            rightChopstick.unlock();
                        }
                    } else {
                        // 没有获取到右手边的筷子，放弃并继续思考
                        System.out.println("timeout");
                    }
                } finally {
                    leftChopstick.unlock();
                }
            }
        } catch (InterruptedException e) { }
    }


    /*private Chopstick left, right;
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
    }*/

}
