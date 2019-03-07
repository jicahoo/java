package com.jichao;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Main {

    public static synchronized void hello (){
        try {
            System.out.println("Before sleep");
            Thread.sleep(600*1000);
            System.out.println("After sleep");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void synchronizedClassDemo() throws InterruptedException {
        Runnable task = Main::hello;
        Thread threadFoo = new Thread(task);
        threadFoo.start();
        Thread threadBar = new Thread(task);
        threadBar.start();

        threadBar.join();
        threadFoo.join();
    }

    public synchronized void world() {
        try {
            System.out.println("Before sleep");
            Thread.sleep(600*1000);
            System.out.println("After sleep");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void synchronizedObjectDemo() throws InterruptedException {
        Main main = new Main();
        Runnable task = main::world;
        Thread threadFoo = new Thread(task);
        threadFoo.start();
        Thread threadBar = new Thread(task);
        threadBar.start();

        threadBar.join();
        threadFoo.join();
    }

    private static void semaPhoreDemo() throws InterruptedException {
        Semaphore sema = new Semaphore(10);
        List<Thread> threads = new LinkedList<>();
        IntStream.range(0,20).forEach(
                x -> {
                    Runnable task = () -> {
                        try {
                            sema.acquire();
                            System.out.println("Before sleep");
                            Thread.sleep(600*1000);
                            System.out.println("After sleep");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            sema.release();
                        }
                    };
                    Thread threadFoo = new Thread(task);
                    threadFoo.start();
                    threads.add(threadFoo);
                }
        );
        threads.forEach(x -> {
                    try {
                        x.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                );
    }

    private static void reentrantLockDemo() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        Runnable task = () -> {
            try {
                reentrantLock.lock();
                System.out.println("Before sleep");
                Thread.sleep(600*1000);
                System.out.println("After sleep");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        };
        Thread threadFoo = new Thread(task);
        threadFoo.start();
        Thread threadBar = new Thread(task);
        threadBar.start();

        threadBar.join();
        threadFoo.join();
    }
    public static void main(String[] args) throws InterruptedException {
        synchronizedClassDemo();
    }
}
