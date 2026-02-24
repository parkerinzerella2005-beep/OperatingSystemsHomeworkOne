import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;

public class diningPhilospher implements Runnable {
    static int P;
    static int M;
    static int totalM;
    static Thread[] diningPhilosphers;
    static Semaphore[] Chopsticks;
    static Semaphore Meal;
    //barrier things for entry 
    static Semaphore mutex;
    static Semaphore semaphoreHold;
    static int threadCount;

    //barrier things for entry 
    static Semaphore exitMutex;
    static Semaphore exitSemaphoreHold;
    static int exitThreadCount;

    int id;
    static Scanner myScanner = new Scanner(System.in); 
    public static void main(String[] args) {
        System.out.println("Program Arguments: -A 1");
        System.out.println("Dining Philosophers Started");

        try {
            P = getPhilosophers();
        } catch (NoSuchElementException e){
            System.out.print("Wrong input try again!");
        }
        if(P == 0){
            return;
        }
        else if(P == 1){
            System.out.println("PROGRAM TERMINATED");
            System.out.println("Philosophers need 2 chopsticks to eat.");
            return;
        }
        try {
            M = getMeals();
            totalM = M;
        } catch (NoSuchElementException e){
            System.out.print("Wrong input try again!");
        }
        if(M == 0){
            return;
        }        

        //setting the Chopsticks semaphore array length equal to number of Philosophers and intializing semaphores
        Chopsticks = new Semaphore[P];
        for (int i = 0; i < P; i++) {
            Chopsticks[i] = new Semaphore(1);
        }

        //setting Meal
        Meal = new Semaphore(1);

        //setting up barrier so the philosphpers arive at the same time
        mutex = new Semaphore(1);
        semaphoreHold = new Semaphore(0);
        threadCount = 0;

        //setting up barrier so the philosophers all exit at the same time
        exitMutex = new Semaphore(1);
        exitSemaphoreHold = new Semaphore(0);
        exitThreadCount = 0;
        
        //Initianilize array of Philosphers and start each one
        diningPhilosphers = new Thread[P];
        for(int i = 0; i<P; i++){
            diningPhilosphers[i] = new Thread(new diningPhilospher(i));
            System.out.println("-Philosopher " + (i+1) + " starting.");
            diningPhilosphers[i].start();
        }
    }   

    public diningPhilospher(int id){
            this.id =id;
        }

    public void run(){
        try{
            mutex.acquire();
            threadCount++;
            if(threadCount == P){
                System.out.println("--All Philosophers have arrived");
                for(int i = 0; i<P;i++){
                    semaphoreHold.release();
                }
                mutex.release();
            }
            else{
                mutex.release();
                semaphoreHold.acquire();
            }
            //since the philosphers can eat more that one meal
            while(true){
            Meal.acquire();
            if(M>0){
                //left chopstick
                if(Chopsticks[id].tryAcquire()){
                    System.out.println("---Philosopher " + (id+1) + "'s left chopstick IS available.");
                    //right chopstick
                    if(Chopsticks[(id+1) % P].tryAcquire()){
                        System.out.println("---Philosopher " + (id+1) + "'s rigt chopstick IS available.");
                        System.out.println("----Philosopher " + (id+1) + " grabs both chopsticks");
                        System.out.println("----Philosopher " + (id+1) + " has a pair of chopsticks.");

                        
                        System.out.println("-----Philosopher " + (id+1) +  " is eating.");
                        Meal.release();
                        
                        //eating
                        int eating = 3 + (int) (Math.random() * 4);
                        for (int i = 0; i < eating; i++) {
                            Thread.yield();
                        }


                        M--;
                        System.out.println("Meals ate: " + (totalM-M));
                        
                        //drops both chopsticks
                        System.out.println("-------Philosopher " + (id+1) + " dropped his left chopstick.");
                        Chopsticks[id].release();
                        System.out.println("-------Philosopher " + (id+1) + " dropped his right chopstick.");
                        Chopsticks[(id+1) % P].release();

                        //thinking
                        System.out.println("--------Philosopher " + (id+1) + " is thinking.");
                        int thinking = 3 + (int) (Math.random() * 4);
                        for (int i = 0; i < thinking; i++) {
                            Thread.yield();
                        }

                    }
                    else{
                        System.out.println("---Philosopher " + (id+1) + "'s right chopstick IS NOT available.");
                        Chopsticks[id].release();
                        Meal.release();
                        Thread.yield();
                    }
                }   
                else{
                    Meal.release();     
                    Thread.yield();
                }
            }
            else{
                Meal.release();
                exitMutex.acquire();
                exitThreadCount++;
                if(exitThreadCount == P){
                    System.out.println("---------All Philosophers have finished eating.");
                    for(int i = 0; i<P;i++){
                        exitSemaphoreHold.release();
                    }
                    exitMutex.release();
                }
                else{
                    exitMutex.release();
                    exitSemaphoreHold.acquire();
                }
                System.out.println("----------Philosopher " + (id+1) + " has left the table.");
                return;
            }
        }
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public static int getPhilosophers(){
        int P = 0;
        int nC = 0;
        try {
            System.out.print("How many philosophers should be created? (integer from 1-10000): ");
            while(nC == 0){
                P = myScanner.nextInt();
                if((P<=10000) && (P>=1)){
                    nC = 1;
                }
                else{
                    System.out.print("Must enter number 2-100! Try again: ");
                }
            }
        } catch (InputMismatchException e) {
            myScanner.next();
            P=0;
            System.out.println("You didnt enter a number or your number was too big! Please reattempt!");
        }
        return P;
    }
    
    public static int getMeals(){
        int M = 0;
        int nC = 0;
        try {
            System.out.print("How many meals should the philosophers eat? (integer from 1-10000): ");
            while(nC == 0){
                M = myScanner.nextInt();
                if((M<=10000) && (M>=1)){
                    nC = 1;
                }
                else{
                    System.out.print("Must enter number 2-100! Try again: ");
                }
            }
        } catch (InputMismatchException e) {
            myScanner.next();
            M=0;
            System.out.println("You didnt enter a number please reattempt!");
        }
        return M;
    }
}