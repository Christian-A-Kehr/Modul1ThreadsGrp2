package dat.sem2.threads;

/**
 The purpose of Threads02 is to...

 @author kasper
 */
public class Threads03 {

    public static void main( String[] args ) throws Exception {

        Thread t1 = new Thread( new TaskWithConstructor("Hej", "Hallo", "Halløj", "Hejsa", "Davs") );
        Thread t2 = new Thread( new TaskWithConstructor("Mandag", "Tirsdag","Onsdag","Torsdag","Fredag") );
        Thread t3 = new Thread( new TaskWithConstructor("Carlsberg", "Grimbergen", "Budweiser", "Thy", "Heineken") );
        System.out.println( "Starter tråd" );
        t1.start(); t2.start(); t3.start();
        System.out.println( "Main er færdig" );
    }
}

class TaskWithConstructor implements Runnable {

    private String[] messages = null;

    public TaskWithConstructor(String... messages) {
        this.messages = messages;
    }
    
        
    @Override
    public void run() {
        for ( String msg : messages ) {
            System.out.println( msg );
            try {
                Thread.sleep( 1000 );
            } catch ( InterruptedException ex ) {
                System.out.println( "Hov - jeg blev afbrudt" );
            }
        }
    }

}
