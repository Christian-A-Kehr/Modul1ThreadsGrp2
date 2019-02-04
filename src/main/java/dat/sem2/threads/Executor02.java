package dat.sem2.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Executor02 {

    public static void main( String[] args ) {
        ExecutorService workingJack = Executors.newSingleThreadExecutor();
        for ( int count = 0; count < 25; count++ ) {
            workingJack.submit( () -> {
                // Det er en rød opgave at forklare hvad denne fejl skyldes
                // Fjern udkommenteringen i næste linje
                //System.out.println( "Hello "+ count + " to us" );
            } );
        }
        workingJack.shutdown();
    }
}
