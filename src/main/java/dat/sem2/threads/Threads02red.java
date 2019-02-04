package dat.sem2.threads;

/**
 The purpose of Threads02 is to...

 @author kasper
 */
public class Threads02red {

    public static void main( String[] args ) throws Exception {
        /*
        New thread by making a runnable (red level)
        The level red is because it uses lambda expressions
         */
        Thread t = new Thread( () -> {
            String[] messages = { "Hej", "Hallo", "Halløj", "Hejsa", "Davs" };
            for ( String msg : messages ) {
                System.out.println( msg );
                try {
                    Thread.sleep( 1000 );
                } catch ( InterruptedException ex ) {
                    System.out.println( "Hov - jeg blev afbrudt" );
                }
            }
        } );
        System.out.println( "Starter tråd" );
        t.start();
//        t.join();
//        t.join(3000);
//        t.interrupt();
        System.out.println( "Main er færdig" );
    }
}
