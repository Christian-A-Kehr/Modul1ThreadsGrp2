package dat.sem2.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 The purpose of Executor03 is to...

 @author kasper
 */
public class Executor03 {

    public static void main( String[] args ) {
        //ExecutorService workingJack = Executors.newSingleThreadExecutor();
        ExecutorService workingJack = Executors.newFixedThreadPool( 3);
        System.out.println( "Main starts" );
        for ( int count = 0; count < 25; count++ ) {
            Runnable task = new MyTask( count );
            workingJack.submit( task );
        }
        System.out.println( "Main is done" );
        workingJack.shutdown();
    }

}

class MyTask implements Runnable {

    private int count = 0;

    MyTask( int cnt ) {
        count = cnt;
    }

    @Override
    public void run() {
        System.out.println( "Task: " + count );
    }
}
