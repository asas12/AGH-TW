package pl.edu.agh.tw.lab4.monitors;

import java.util.Random;

public class Main {

    //buffer will have size 2*M
    static int M = 10000;
    //number of producers
    static int m = 100;
    //number of consumers
    static int n = 100;

    static Random randomGenerator = new Random();
    static final Buffer buffer = new Buffer(M, m, n);

    public static void main(String[] args) {

        for(int i=0; i<m; i++){
            new Producer().start();
        }
        for(int i=0; i<n; i++){
            new Consumer().start();
        }
    }
}
