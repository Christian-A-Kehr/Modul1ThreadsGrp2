package dat.sem2.threads;

/**
 The purpose of ThreadsSync01 is to...

 @author kasper
 */
public class ThreadsSyncUnsafe {
    public static void main( String[] args ) throws InterruptedException {
        final int noThreads = 2;
        final int noLoops = 1000;
        final int addNumber = 15;
        final int expectedSum = noThreads * noLoops * addNumber;
        final Counter c = new Counter(0);
        Thread[] threads = new Thread[noThreads];
        for(int index = 0; index < noThreads; index++){
            threads[index] = new Thread(){
                @Override
                public void run(){
                    for(int i = 0; i< noLoops; i++){
                        c.add( addNumber );
                    }
                }
            };
        }
        for (Thread t : threads) t.start();
        System.out.println( "All started" );
        for (Thread t : threads) t.join();
        System.out.println( "\nSum (expected " + expectedSum + ") is: " + c.getCounter() );
    }
}

class Counter {

    private int counter;

    public Counter( int counter ) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public void add( int amount ) {
        int old = counter;
        counter = old + amount;
    }
}
