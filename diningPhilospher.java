import java.util.Scanner;
import java.math.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;

public class diningPhilospher implements Runnable {
        static int P;
        static int M;
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
       
    }

    public diningPhilospher(int id){
            this.id =id;
        }
    
    public void run(){

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