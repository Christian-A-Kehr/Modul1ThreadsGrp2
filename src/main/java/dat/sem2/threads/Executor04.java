package dat.sem2.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The purpose of Executor04 is to...
 * @author kasper
 */
public class Executor04 {
    public static void main( String[] args ) {
        //ExecutorService workingJack = Executors.newSingleThreadExecutor();
        ExecutorService workingJack = Executors.newFixedThreadPool( 3);
        System.out.println( "Main starts" );
        for ( int count = 0; count < 25; count++ ) {
            Runnable task = new MyTask2( count );
            workingJack.submit( task );
        }
        System.out.println( "Main is done" );
        workingJack.shutdown();
    }

}

class MyTask2 implements Runnable {

    private int count = 0;
    private int sleepTime = 0;

    MyTask2( int cnt ) {
        sleepTime = (int)(Math.random()*800+200); // At least 200 ms, up to one sec
        count = cnt;
    }

    @Override
    public void run() {
        try {
            Thread.sleep( sleepTime ); // simulate some external job taking time
            System.out.println( "Task: " + count );
        } catch ( InterruptedException ex ) {
            System.out.println( "We got interrupted" );
        }
    }
}