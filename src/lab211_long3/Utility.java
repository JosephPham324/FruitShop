package lab211_long3;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Pham Nhat Quang CE170036
 */
public class Utility {

    private final Scanner input;

    /**
     * Create new Utility object
     *
     * @param input Scanner to initialize
     */
    public Utility(Scanner input) {
        this.input = input; //Initialize input field
    }

    /**
     * Display a message and get input String from user
     *
     * @param msg Message to display
     * @param length Maximum length of String
     * @param lengthMessage Decision to display maximum length warning
     * @return a String
     */
    public String getString(String msg, int length, boolean lengthMessage) {
        String result = ""; //Store result
        do {
            System.out.print(msg); //Print message
            result = input.nextLine().trim(); //Get input
            if (result.equals("")) {//If blank, print warning
                System.out.println("Error: This cannot be blank!");
            }
            if (result.length() > length && lengthMessage) { //If exceeds maximum length, print warning
                System.out.println("Sorry, you cannot enter more than " + length + " characters");
            }
        } while (result.equals("") || result.length() > length); //Loop until valid
        return result;
    }

    /**
     * Get an int from user until int is valid, with message and possible error
     * message
     *
     * @param msg Message to display
     * @param errorMsg Error message to display
     * @param min Minimum value acceptable
     * @param max Maximum value acceptable
     * @return an integer
     */
    public int getInt(String msg, String errorMsg, int min, int max) {
        int result = Integer.MIN_VALUE; //Store result
        boolean got; //Flag to check if input is correct
        do {
            try {
                result = Integer.parseInt(getString(msg, 255, false)); //Get input from String
                got = true;
                if (result < min || result > max) { //Warn user if input exceeds range
                    System.out.println("Error: This must be an integer from " + min + " to " + max);
                }
            } catch (NumberFormatException nfe) { //Incorrect input
                System.out.println(errorMsg);//Print error message
                got = false;//Set flag to false
            }

        } while (!got || result < min || result > max);//Loop until flag is true
        return result; //Return result
    }

    /**
     * Get a name from user until name is valid, display message before getting
     * input
     *
     * @param msg Message to display
     * @param length Maximum length of name
     * @param lengthMessage To determine whether or not to display maximum
     * length warning
     * @return A validated name String
     */
    public String getName(String msg, int length, boolean lengthMessage) {
        String result = "";
        do {
            result = getString(msg, length, lengthMessage);
        } while (!nameValidation(result));
        return result;
    }

    /**
     * Check name with the rules: Student name consists of alphanumeric
     * characters (a-zA-Z0-9), lowercase, or uppercase. Student name allowed of
     * the dot (.), underscore (_), and hyphen (-) and space ( ). The dot (.),
     * underscore (_), hyphen (-), or space ( ) must not be the first or last
     * character. The dot (.), underscore (_), or hyphen (-) or space ( ) does
     * not appear consecutively The number of characters must be between 2 to
     * 50.
     *
     * @param name: name to check
     * @return valid status of the name
     */
    public boolean nameValidation(String name) {
        if (name.equals("")) { //If name is blank return false
            return false;
        }

        //Regular expression to check
        String namePattern = "^[a-zA-Z]([._-](?![._-])|[a-zA-Z0-9\\s]){0,48}[a-zA-Z0-9.]$";

        //If names don't follow rule, show message and return false
        if (!Pattern.compile(namePattern).matcher(name).matches()) { //Check if name follows rules
            System.out.println("Error: Invalid name."
                    + "\nName must be at least 2 characters, start with at least a letter and can only have '_', '.', '-' as special characters.");
            return false;
        }
        //Return true
        return true;
    }
}
