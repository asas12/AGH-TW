package agh.tw;

import java.util.concurrent.locks.ReentrantLock;
import static agh.tw.Main.*;

public class Producer extends  Thread {
    ReentrantLock bufferLock;

    public Producer(ReentrantLock lock){
        this.bufferLock = lock;
    }

    public void run() {
        int x = randomGenerator.nextInt(M - 1) + 1;

        //start timer
        long startTime = System.nanoTime();

        bufferLock.lock();
        try {
            while (buffer.bufferSize<buffer.ItemsLeft+x && buffer.thereAreConsumers()) {
                //czekaj na konsumentów
                buffer.notFull.await();
            }

            long endTime = System.nanoTime();
            buffer.ItemsLeft+=x;
            buffer.notEmpty.signalAll();

            //notify that you inserted
            logger.fine("Produced " + x + " items, " + Thread.currentThread());
            //let consumers know that you may be the last producer
            buffer.quitProducing();
            P_time.getAndAdd(endTime - startTime);
            logger.info("Producer: " + Thread.currentThread() + " " + (endTime - startTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            bufferLock.unlock();
        }
    }
}
