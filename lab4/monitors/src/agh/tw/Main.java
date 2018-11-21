package agh.tw;

import java.io.IOException;
import java.util.Random;
import java.util.logging.*;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());

    //buffer will have size 2*M
    static int M = 10000;
    //number of producers
    static int m = 100;
    //number of consumers
    static int n = 100;

    static long P_time = 0;
    static long C_time = 0;

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
            new Producer().start();
        }
        for(int i=0; i<n; i++){
            new Consumer().start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Producers' average wait time: "+(P_time/m));
        logger.info("Consumers' average wait time: "+(C_time/n));
        System.out.println("Producers' average wait time: "+(P_time/m));
        System.out.println("Consumers' average wait time: "+(C_time/n));
    }
}
