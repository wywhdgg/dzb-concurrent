package com.fxiaoke.dzb.dzb.concurrent.thread;

/**
 * @author: dongzhb
 * @date: 2019/5/21
 * @Description:
 */
public class ThreadTest {
    private int count = 0;
    private  volatile boolean isRuning = true;

    public static void main(String[] args) {
        ThreadTest threadTest = new ThreadTest();
        System.out.println("开始执行任务....");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (threadTest.isRuning) {
                    threadTest.count++;
                }
                System.out.println("count="+threadTest.count);
            }
        }).start();
        try {
            Thread.sleep(3000);
            threadTest.isRuning = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("close thread .......");
    }
}
