package agh.tw;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.*;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());

    //buffer will have size 2*M
    static int M = 100000;
    //number of producers
    static int m = 1000;
    //number of consumers
    static int n = 1000;

    static AtomicLong P_time = new AtomicLong();
    static AtomicLong C_time = new AtomicLong();

    static Random randomGenerator = new Random();
    static final Buffer buffer = new Buffer(M, m, n);

    public static void main(String[] args) {
        logger.setLevel(Level.FINE);
        SimpleFormatter formatter = new SimpleFormatter();
        try {
            Handler h = new FileHandler("./lab4_monitors.log");
            h.setLevel(Level.FINE);
            logger.addHandler(h);
            h.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for(int i=0; i<m; i++){
            new Producer(buffer.bufferLock).start();
        }
        for(int i=0; i<n; i++){
            new Consumer(buffer.bufferLock).start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Producers' average wait time: "+(P_time.get()/m));
        logger.info("Consumers' average wait time: "+(C_time.get()/n));
        System.out.println("Producers' average wait time: "+(P_time.get()/m));
        System.out.println("Consumers' average wait time: "+(C_time.get()/n));
    }
}
