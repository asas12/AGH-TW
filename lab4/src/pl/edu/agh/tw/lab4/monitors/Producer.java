package pl.edu.agh.tw.lab4.monitors;

import static pl.edu.agh.tw.lab4.monitors.Main.*;

public class Producer extends  Thread {
    public void run() {
        int x = randomGenerator.nextInt(M - 1) + 1;

        //start timer

        synchronized (buffer) {
            while (!buffer.managedToInsert(x) && buffer.thereAreConsumers()) {
                //czekaj na konsumentów
                try {
                    buffer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //notify that you inserted
            if (buffer.thereAreConsumers())
            {
                System.out.println("Produced " + x + " items, " + Thread.currentThread());
            }else{
                System.out.println("Didn't produce "+x+" items, "+Thread.currentThread());
            }

            System.out.println("Finished producing, "+(Thread.currentThread()));
            //let consumers know that you may be the last producer
            buffer.quitProducing();
            buffer.notifyAll();
        }

        System.out.println("Finished producing, "+(Thread.currentThread()));
    }


}
