package agh.tw;

//import java.util.Random;


import static agh.tw.Main.logger;

public class Buffer {
    private int ItemsLeft = 0;
    private int bufferSize;
    private int consumersCount;
    private int producersCount;

    public Buffer(int M, int m, int n){
        this.bufferSize = 2*M;
        this.producersCount = m;
        this.consumersCount = n;
    }

    //returns true when n items can be inserted into the buffer, false otherwise
    public boolean managedToInsert(int n){
        if(ItemsLeft+n<=bufferSize){
            ItemsLeft+=n;
            logger.fine("Inserted "+n+" into buffer. Number of items: "+ItemsLeft + " P: "+producersCount+" C: "+consumersCount);
            return true;
        }
        else{
            return false;
        }
    }

    //returns true when n items can be consumed from the buffer, false otherwise
    public boolean managedToConsume(int n){
        if(ItemsLeft-n>=0){
            ItemsLeft-=n;
            logger.fine("Consumed "+n+" from buffer. Number of items: "+ItemsLeft+ " P: "+producersCount+" C: "+consumersCount);
            return true;
        }
        else{
            return false;
        }
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
