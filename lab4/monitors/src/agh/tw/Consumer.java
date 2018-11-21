package agh.tw;


import static agh.tw.Main.*;

public class Consumer extends Thread{
    public void run(){

        int x = randomGenerator.nextInt(M-1)+1;

        //start timer
        long startTime = System.nanoTime();

        synchronized(buffer) {
            while (!buffer.managedToConsume(x) && buffer.thereAreProducers()) {
                //wait for producers
                try {
                    buffer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            long endTime = System.nanoTime();
            logger.fine("Consumed or tried to consume " + x + " items, " + Thread.currentThread());

            buffer.quitConsuming();
            buffer.notifyAll();

            C_time+=endTime-startTime;
            logger.info("Consumer: "+ Thread.currentThread() +" "+ (endTime - startTime));

        }



    }
}
