package agh.tw;

//import java.util.Random;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static agh.tw.Main.logger;

public class Buffer {

    // TODO should be atomic
    int ItemsLeft = 0;
    int bufferSize;
    int consumersCount;
    int producersCount;

    final ReentrantLock bufferLock = new ReentrantLock();
    final Condition notFull = bufferLock.newCondition();
    final Condition notEmpty = bufferLock.newCondition();


    public Buffer(int M, int m, int n){
        this.bufferSize = 2*M;
        this.producersCount = m;
        this.consumersCount = n;
    }

    public boolean thereAreConsumers() {
        return consumersCount>0;
    }

    public boolean thereAreProducers(){
        return producersCount > 0;
    }

    public void quitProducing(){
        producersCount--;
    }

    public void quitConsuming(){ consumersCount--;

    }

}
