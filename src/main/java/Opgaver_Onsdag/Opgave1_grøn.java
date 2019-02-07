/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opgaver_Onsdag;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Christian Ambjørn Kehr
 */
public class Opgave1_grøn {

    public static void main(String[] args) throws InterruptedException {
        //ExecutorService workingJack = Executors.newSingleThreadExecutor();
        ExecutorService workingJack = Executors.newFixedThreadPool(4);
        System.out.println("Main starts");
        for (int i = 0; i <= 25; i++) {
            Runnable task = new MyTask(i);
            Thread.sleep(250);
            // Thread.sleep() er no go!
            workingJack.submit(task);
        }
        System.out.println("Main is done");
        workingJack.shutdown();
    }

}

class MyTask implements Runnable {

    private int count = 0;

    MyTask(int cnt) {
        count = cnt;
    }

    @Override
    public synchronized void run() {
        System.out.println("Task: - " + String.valueOf((char) (count + 65)) + String.valueOf((char) (count + 65)) + String.valueOf((char) (count + 65)));
    }
}
