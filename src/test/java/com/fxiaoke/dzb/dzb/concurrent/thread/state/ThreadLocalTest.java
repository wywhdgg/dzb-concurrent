package com.fxiaoke.dzb.dzb.concurrent.thread.state;

/**
 * @author: dongzhb
 * @date: 2019/5/17
 * @Description:
ThreadLocal是Java里一种特殊的变量。
它是一个线程级别变量，每个线程都有一个ThreadLocal就是每个线程都拥有了自己独立的一个变量，
竞争条件被彻底消除了，在并发模式下是绝对安全的变量。
用法： ThreadLocal<T> var = new ThreadLocal<T>();
会自动在每一个线程上创建一个T的副本，副本之间彼此独立，互不影响。
可以用ThreadLocal存储一些参数，以便在线程中多个方法中使用，用来代替方法传参的做法。
实在难以理解的， 可以理解为， JVM维护了一个Map<Thread, T>， 每个线程要用这个T的时候， 用当前的线程去Map
里面取。 仅作为一个概念理解
 */
public class ThreadLocalTest {
    /** threadLocal变量，每个线程都有一个副本，互不干扰 */
    public static ThreadLocal<String> value = new ThreadLocal<String>();

    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            public void run() {
                value.set("111111111");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程-1："+value.get());
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000L);
                    System.out.println("线程-2："+value.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
