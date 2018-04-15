/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.cli;

import java.util.List;
import java.util.Scanner;

/**
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
     * Shows a menu with a given menu title. Handles special cases of Menu blank
     * or divider.
     *
     * @param menuName the name to print for the menu
     * @param menu the list of menu elements
     */
    public static void showMenu(String menuName, List<MenuElement> menu) {
        System.out.println(menuName);
        for (MenuElement element : menu) {
            if (element.equals(MenuElement.BLANK)) {
                System.out.println();
            } else if (element.equals(MenuElement.DIVIDER)) {
                System.out.println("----");
            } else {
                System.out.println(element);
            }
        }
    }

}
