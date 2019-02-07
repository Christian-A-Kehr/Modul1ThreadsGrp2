/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opgaver_Onsdag;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Mads Egevang Jensen
 */
public class Opgave3_rød {

    public static void main(String[] args) {
        ExecutorService workingJack = Executors.newFixedThreadPool(17);
        MyTask5.init();
        MyTask5.showList();
        System.out.println("Main starts");
        for (int count = 0; count < 100; count++) {
            Runnable task = new MyTask5(count);
            workingJack.submit(task);
        }
        System.out.println("Main is done");
        workingJack.shutdown();
        MyTask5.showList();

    }

}

class MyTask5 implements Runnable {

    private int count = 0;
    private int sleepTime = 0;

    final private static List<Integer> list = new ArrayList<>();

    public static void init() {
        for (int i = 0; i < 100; ++i) {
            list.add(-1);
        }
    }

    public static void showList() {
        for (int i : list) {
            System.out.println("List element: " + i);
        }
    }

    MyTask5(int cnt) {
        sleepTime = (int) (Math.random() * 800 + 200); // At least 200 ms, up to one sec
        count = cnt;
    }

    @Override
    public void run() {
        list.set(count, count);
        System.out.println("Task: " + count + ": List size = " + list.size());
    }
}
