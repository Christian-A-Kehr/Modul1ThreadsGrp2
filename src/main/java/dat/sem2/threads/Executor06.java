package dat.sem2.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 The purpose of Executor04 is to...

 @author kasper
 */
public class Executor06 {

    public static void main( String[] args ) {
        ExecutorService workingJack = Executors.newFixedThreadPool( 17 );
        System.out.println( "Main starts" );
        for ( int count = 0; count < 100; count++ ) {
            Runnable task = new MyTask4( count );
            workingJack.submit( task );
        }
        System.out.println( "Main is done" );
        workingJack.shutdown();

    }

}

class MyTask4 implements Runnable {

    private int count = 0;
    private int sleepTime = 0;

    private static List<Integer> list = new ArrayList<>();

    MyTask4( int cnt ) {
        sleepTime = (int) ( Math.random() * 800 + 200 ); // At least 200 ms, up to one sec
        count = cnt;
    }

    @Override
    public void run() {
        list.add( count );
        System.out.println( "Task: " + count + ": List size = " + list.size() );
    }
}
