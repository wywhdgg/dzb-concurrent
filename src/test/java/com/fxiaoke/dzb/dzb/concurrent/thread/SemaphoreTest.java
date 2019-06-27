package com.fxiaoke.dzb.dzb.concurrent.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

/***
 *@author lenovo
 *@date 2019/5/23 22:39
 *@Description:
 *@version 1.0
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore sp =new  Semaphore(100);
        for (int i = 0; i <1000 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sp.acquire();
                        getDB();
                        sp.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

    public static void getDB(){
        System.out.println("开始请求DB。。。。。。。");
        LockSupport.parkNanos(1000*1000*1000L);
    }
}
