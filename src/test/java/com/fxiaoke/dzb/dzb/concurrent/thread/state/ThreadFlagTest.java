package com.fxiaoke.dzb.dzb.concurrent.thread.state;

/**
 * @author: dongzhb
 * @date: 2019/5/17
 * @Description:
 */
public class ThreadFlagTest {

    private volatile  static boolean flag =true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag){
                    System.out.println("task is begin......");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        Thread.sleep(3000L);
        flag=false;
        System.out.println("task is finish .....");
    }
}
