 
package gametracker.cli;


import java.util.Scanner;

/**
 *
 * Allows user to be prompted for various types of information. Provides static
 * methods to do prompting.
 * 
 */
public class UIHelper {

    private static final Scanner kb = new Scanner(System.in);

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
    
    /**
     * 
     * Tests if the argument string matches (ignoring case) any of the
     * example files.
     * 
     * @param arg the string being tested
     * @param matches an arbitrary set of strings to checkFor against arg
     * @return true if arg matches (ignoring case) one of the example strings
     */
    public static boolean checkFor(String arg, String... matches) {
        for (String m : matches) {
            if (arg.equalsIgnoreCase(m)) {
                return true;
            }
        }
        return false;
    }

 




    

}
