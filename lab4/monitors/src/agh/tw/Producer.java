package agh.tw;

import java.util.logging.*;
import static agh.tw.Main.*;

public class Producer extends  Thread {
    public void run() {
        int x = randomGenerator.nextInt(M - 1) + 1;

        //start timer
        long startTime = System.nanoTime();

        synchronized (buffer) {
            while (!buffer.managedToInsert(x) && buffer.thereAreConsumers()) {
                //wait for consumers
                try {
                    buffer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            long endTime = System.nanoTime();
            //notify that you inserted
            logger.fine("Produced " + x + " items, " + Thread.currentThread());
            //let consumers know that you may be the last producer
            buffer.quitProducing();
            buffer.notifyAll();
            P_time+=endTime-startTime;
            logger.info("Producer: "+ Thread.currentThread() +" "+ (endTime- startTime));
        }
    }
}
