 
package gametracker.cli;


import java.util.Scanner;

/**
 *
 * Utility class to handle prompting as part of the command line 
 * interface
 * 
 * @author tjkendon
 */
public class UIHelper {

    private static Scanner kb = new Scanner(System.in);

    /**
     *
     * Prompts the user with the specified text, appended with a ": " and
     * returns the next keyboard input as a String.
     *
     * @param promptText the query to ask the user
     * @return the text entered by the user
     */
    public static String promptForString(String promptText) {

        System.out.print(promptText + ": ");
        String response = kb.nextLine();

        return response;
    }

    
     /**
     *
     * Prompts the user with the specified text, appended with a ": " and
     * returns the next keyboard input as a String or the defaultValue if 
     * the input is empty.
     *
     * @param promptText the query to ask the user
     * @param defaultValue the default value that will be returned if the
     * user enters an empty string on the keyboard
     * @return the text entered by the user
     */
    public static String promptForString(String promptText, String defaultValue) {

        System.out.print(promptText + " (default is " + defaultValue +") : ");
        String response = kb.nextLine();
        
        if (response.isEmpty()) {
            return defaultValue;
        }

        return response;
    }
    
    /**
     *
     * Prompts the user with the specified text, appended with a " (Yes/No): ".
     * Returns true if the user enters Yes or Y (in any case) or false if any
     * other text is entered.
     *
     * @param promptText the query to ask the user
     * @return true if the user enters some form of Yes and false otherwise
     */
    public static boolean promptForBoolean(String promptText) {

        System.out.print(promptText + " (Yes/No): ");
        String response = kb.nextLine();

        return (response.equalsIgnoreCase("Yes")
                || response.equalsIgnoreCase("Y"));
    }





    

}
