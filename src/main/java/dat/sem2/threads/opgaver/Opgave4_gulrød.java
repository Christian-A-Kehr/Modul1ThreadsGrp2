/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat.sem2.threads.opgaver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Mads Egevang Jensen
 */
public class Opgave4_gulrød {
    
    public static void main( String[] args ) {
        //ExecutorService workingJack = Executors.newSingleThreadExecutor();
        ExecutorService workingJack = Executors.newFixedThreadPool( 8);
        System.out.println( "Main starts" );
        for ( int count = 0; count < 25; count++ ) {
            Runnable task = new MyTask2( count );
            workingJack.submit(task);
        }
        System.out.println( "Main is done" );
        workingJack.shutdown();
    }

}

class MyTask2 implements Runnable {

    private int count = 0;

    MyTask2( int cnt ) {
        count = cnt;
    }

    @Override
    public synchronized void run() {
        long startTime = System.currentTimeMillis();
       // try {
            // Loop for the given duration
            long currentTime = System.currentTimeMillis();
            while ((currentTime - startTime) < 100000) {
                // Every 100ms, sleep for the percentage of unladen time
                //if ((currentTime % 100) == 0) {
                  //  Thread.sleep(0);
                //}
                currentTime = System.currentTimeMillis();
                System.out.println( "Task: " + count );
            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        
    }
}
