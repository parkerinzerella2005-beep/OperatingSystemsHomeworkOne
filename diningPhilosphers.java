import java.util.Scanner;
import java.math.*;
import java.util.InputMismatchException;

public class diningPhilosphers {
    public static void main(String[] args) {
        int P;
        int M;
        int C;
        P = getPhilosophers();
    }
    public static int getPhilosophers(){
        Scanner pScanner = new Scanner(System.in);
        int P = 0;
        int nC = 0;
        try {
            System.out.print("How many Philoshopers/Chopsticks? (2-100): ");
            while(nC == 0){
                P = pScanner.nextInt();
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
        pScanner.close();
        return P;
    }
}
