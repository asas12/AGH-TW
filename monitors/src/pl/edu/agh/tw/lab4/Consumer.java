package pl.edu.agh.tw.lab4.monitors;


import static pl.edu.agh.tw.lab4.monitors.Main.*;

public class Consumer extends Thread{
    public void run(){

        int x = randomGenerator.nextInt(M-1)+1;

        //start timer
        long startTime = System.nanoTime();

        synchronized(buffer) {
            while (!buffer.managedToConsume(x) && buffer.thereAreProducers()) {
                //czekaj na konsumentów
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
