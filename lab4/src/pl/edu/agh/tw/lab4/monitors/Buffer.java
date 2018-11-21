package pl.edu.agh.tw.lab4.monitors;

//import java.util.Random;


public class Buffer {
    //private int array[];
    private int ItemsLeft = 0;
    private int bufferSize;
    private int consumersCount;
    private int producersCount;

    public Buffer(int M, int m, int n){
      //  this.array = new int[2*bufferSize];
       // Random randomGenerator = new Random();
        //array = randomGenerator.ints(2*bufferSize).toArray();
        this.bufferSize = 2*M;
        this.consumersCount = m;
        this.producersCount = n;
    }

   /* public synchronized int getItemsLeft() {
        return ItemsLeft;
    }*/


    //returns true when n items can be inserted into the buffer, false otherwise
    public boolean managedToInsert(int n){
        if(ItemsLeft+n<=bufferSize){
            ItemsLeft+=n;
            System.out.println("Inserted "+n+" into buffer. Number of items: "+ItemsLeft);
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
            System.out.println("Consumed "+n+" from buffer. Number of items: "+ItemsLeft);
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

    public void quitConsuming(){

    }

}
