import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;

public class diningPhilospher implements Runnable {
    static int P;
    static int M;
    static Semaphore[] Chopsticks;
    static Thread[] diningPhilosphers;
    static Semaphore Meal;
    int id;
    static Scanner myScanner = new Scanner(System.in); 
    public static void main(String[] args) {
        try {
            P = getPhilosophers();
        } catch (NoSuchElementException e){
            System.out.print("Wrong input try again!");
        }
        if(P == 0){
            return;
        }
        try {
            M = getMeals();
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
        
        //Initianilize array of Philosphers and start each one
        diningPhilosphers = new Thread[P];
        for(int i = 0; i<P; i++){
            diningPhilosphers[i] = new Thread(new diningPhilospher(i));
            diningPhilosphers[i].start();
        }
    }   

    public diningPhilospher(int id){
            this.id =id;
        }

    // while meals > 0:
    // pick up right chopstick if available
    // pick up left chopstick if available
    
    // if both chopsticks acquired:
    //     eat (decrement meals)
    //     put down both chopsticks
    // else:
    //     put down any chopstick you picked up
    //     yield and try again

    public void run(){
        try{
            //since the philosphers can eat more that one meal
            while(true){
            Meal.acquire();
            if(M>0){
                //left chopstick
                if(Chopsticks[id].tryAcquire()){
                    //right chopstick
                    if(Chopsticks[(id+1) % P].tryAcquire()){
                        M--;
                        Meal.release();

                        //eating
                        int eating = 3 + (int) (Math.random() * 4);
                        for (int i = 0; i < eating; i++) {
                            Thread.yield();
                        }
                        
                        Chopsticks[id].release();
                        Chopsticks[(id+1) % P].release();

                        //thinking
                        int thinking = 3 + (int) (Math.random() * 4);
                        for (int i = 0; i < thinking; i++) {
                            Thread.yield();
                        }

                    }
                    else{
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
            System.out.print("How many Philoshopers/Chopsticks? (2-100): ");
            while(nC == 0){
                P = myScanner.nextInt();
                if((P<100) && (P>2)){
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
            System.out.print("How many Meals? (2-100): ");
            while(nC == 0){
                M = myScanner.nextInt();
                if((M<100) && (M>2)){
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