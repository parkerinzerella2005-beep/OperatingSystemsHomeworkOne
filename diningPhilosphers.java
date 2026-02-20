import java.util.Scanner;
import java.math.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class diningPhilosphers {
        static int P;
        static int M;
        static int C;

        static Scanner myScanner = new Scanner(System.in);


    public static void main(String[] args) {
        

        try {
            P = getPhilosophers();
        } catch (NoSuchElementException e){
            System.out.print("Wrong input try again!");
        }
        try {
            M = getMeals();
        } catch (NoSuchElementException e){
            System.out.print("Wrong input try again!");
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
            System.out.println("You didnt enter a number please reattempt!");
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
            System.out.println("You didnt enter a number please reattempt!");
        }

        return M;
    }
}
