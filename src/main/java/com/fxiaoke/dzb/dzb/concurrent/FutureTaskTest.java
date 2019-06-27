package com.fxiaoke.dzb.dzb.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/***
 *@author lenovo
 *@date 2019/5/24 22:04
 *@Description:
 *@version 1.0
 */
public class FutureTaskTest {
    public static void main(String[] args) {
        CallableTask  callableTask  =    new CallableTask();
        FutureTask future = new FutureTask(callableTask);
        new Thread(future).start();
        try {
            System.out.println("进入get方法，阻塞等待结果。。。");
            String result = (String)future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

   static class  CallableTask implements   Callable{
        @Override
        public Object call() throws Exception {
            return "hello word";
        }
    }

}
