package agh.tw;

import java.util.concurrent.locks.ReentrantLock;
import static agh.tw.Main.*;

public class Consumer extends Thread{
    ReentrantLock bufferLock;

    public Consumer(ReentrantLock lock){
        this.bufferLock = lock;
    }
    public void run(){

        int x = randomGenerator.nextInt(M-1)+1;

        //start timer
        long startTime = System.nanoTime();

        bufferLock.lock();
        try {
            while (!((buffer.bufferSize-x)>0) && buffer.thereAreProducers()) {
                //czekaj na konsumentów
                buffer.notEmpty.await();
            }

            long endTime = System.nanoTime();

            buffer.ItemsLeft-=x;
            buffer.notFull.signalAll();

            logger.fine("Consumed or tried to consume " + x + " items, " + Thread.currentThread());

            buffer.quitConsuming();

            C_time.getAndAdd(endTime-startTime);
            logger.info("Consumer: "+ Thread.currentThread() +" "+ (endTime - startTime));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            bufferLock.unlock();
        }


    }
}
