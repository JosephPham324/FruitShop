package lab211_long3;

import java.util.Scanner;

/**
 *
 * @author Pham Nhat Quang CE170036
 */
public class LAB211_Long3 {

    static Scanner input; //To get input

    /**
     * Prompt user to press ENTER to continue
     */
    static void enterToContinue() {
        System.out.println("Please press enter to continue!");
        input.nextLine();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        input = new Scanner(System.in); //Get input
        Utility util = new Utility(input); //Utility object
        ShopManagement sm = new ShopManagement(util); //Manage fruits and orders
        int menuChoice = 0; //Store menu choice
        do {
            //Print menu
            System.out.println("\tFRUIT SHOP SYSTEM");
            System.out.println("1. Create fruit");
            System.out.println("2. View orders");
            System.out.println("3. Shopping (for buyer)");
            System.out.println("4. Exit");
            menuChoice = util.getInt("\tPlease choose: ", "Error: This must be an integer from 1 to 4!", 1, 4);//Get menu choice

            switch (menuChoice) {//Perform functions
                case 1: //Create fruit
                    sm.addFruit();
                    enterToContinue();
                    break;

                case 2://View orders
                    sm.viewOrders();

                    //Pause until user press enter
                    enterToContinue();
                    break;

                case 3://Shopping
                    if (!sm.areFruitsUnavailable()) {//If fruits are available to buy
                        sm.shopping();//Do shopping
                    } else {
                        System.out.println("Sorry, there are no fruits to buy!");//Else inform user
                    }
                    enterToContinue();
                    break;

                case 4://Exit
                    System.out.println("Goodbye, please come again!");
                    break;
            }
        } while (menuChoice != 4); //While function choice is not exit
    }

}
