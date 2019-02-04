package dat.sem2.threads;

/**
 The purpose of Threads02 is to...

 @author kasper
 */
public class Threads02 {

    public static void main( String[] args ) throws Exception {
        /*
        New thread by making a runnable (green level)
         */
        Thread t = new Thread( new HelloTask() );
        System.out.println( "Starter tråd" );
        t.start();
//        t.join();
//       t.join(3000);
        Thread.sleep( 2500 );
        t.interrupt();
        System.out.println( "Main er færdig" );
    }
}

class HelloTask implements Runnable {

    @Override
    public void run() {
        String[] messages = { "Hej", "Hallo", "Halløj", "Hejsa", "Davs" };
        try {
            for ( String msg : messages ) {
                System.out.println( msg );
                Thread.sleep( 1000 );
            }
        } catch ( InterruptedException ex ) {
            System.out.println( "Hov - jeg blev afbrudt" );
        }
    }

}
