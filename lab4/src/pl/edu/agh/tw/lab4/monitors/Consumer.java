package pl.edu.agh.tw.lab4.monitors;


import static pl.edu.agh.tw.lab4.monitors.Main.*;

public class Consumer extends Thread{
    public void run(){

        int x = randomGenerator.nextInt(M-1)+1;

        //start timer

        synchronized(buffer) {
            while (!buffer.managedToConsume(x) && buffer.thereAreProducers()) {
                //czekaj na konsumentów
                try {
                    buffer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(buffer.thereAreProducers()) {
                System.out.println("Consumed " + x + " items, " + Thread.currentThread());
            }else
            {
                System.out.println("Didn't consume " + x + " items, " + Thread.currentThread());
            }

            System.out.println("Finished consuming, "+(Thread.currentThread()));
            buffer.quitConsuming();
            buffer.notifyAll();
        }
        System.out.println("Finished consuming, "+(Thread.currentThread()));
    }
}
