package com.fxiaoke.dzb.dzb.concurrent.thread;

/**
 * @author: dongzhb
 * @date: 2019/5/17
 * @Description:
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        FixedSizeThreadPool threadPool = new FixedSizeThreadPool(3,6);
        for (int i = 0; i <19 ; i++) {
            System.out.println(i);
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("task is excute...");
                    System.out.println("=======================");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
