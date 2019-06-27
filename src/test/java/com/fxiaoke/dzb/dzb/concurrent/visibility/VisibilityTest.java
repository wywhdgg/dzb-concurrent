package com.fxiaoke.dzb.dzb.concurrent.visibility;

/***
 *@author lenovo
 *@date 2019/5/19 21:10
 *@Description:
 *@version 1.0
 */
public class VisibilityTest {

    private  int i=0;
    private volatile  boolean isRuning = true;

    public static void main(String[] args) {
        VisibilityTest visibilityTest = new VisibilityTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("循环......");
                while (visibilityTest.isRuning){
                    visibilityTest.i++;
                }
                System.out.println("i======"+visibilityTest.i);
            }
        }).start();

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        visibilityTest.isRuning =false;
        System.out.println("shutdown......");

    }


}
