/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.cli;

import static gametracker.cli.CLI.SESSION_DATE_FORMAT;
import gametracker.data.Game;
import gametracker.data.Game.Platform;
import java.util.List;
import java.util.Scanner;
import org.joda.time.DateTime;

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

    /**
     *
     * Helper method to parse a JodaTime DateTime from an input string. If the
     * string is blank then returns the DateTime for now. Otherwise attepmpts to
     * parse from the format in CLI - SESSION_DATE_FORMAT. If it fails it throws
     * an IllegalArgumentException with information.
     *
     * @param dateStr the string that will be parsed
     * @return a correct date time if the string is empty or can be parsed
     */
    public static DateTime parseDateTime(String dateStr) {
        DateTime date;
        if (dateStr.isEmpty()) {
            date = new DateTime();
        } else {
            try {
                date = DateTime.parse(dateStr, SESSION_DATE_FORMAT);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Not able to parse date from " + dateStr, e);
            }
        }
        return date;
    }

    /**
     *
     * Helper method to parse a double from an input string, which is intended
     * to represent the play time. If it can't be parsed throws a
     * IllegalArgumentException with an explanation.
     *
     * @param timeStr the string to be parsed as a double
     * @return the time played if possible
     */
    public static double parsePlayTime(String timeStr) {
        double time = -1.0;
        try {
            time = Double.parseDouble(timeStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Not able to parse play time from " + timeStr, e);
        }
        return time;
    }

    public static Platform parsePlatform(String platformStr) {

        try {
            return Game.Platform.valueOf(platformStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Not able to parse platform from " + platformStr, e);
        }

    }

    public static int parseYear(String yearStr) {
        int year = 0;
        if (yearStr.length() == 4) {
            try {
                return Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Not able to parse year from " + yearStr, e);

            }
        } else {
            throw new IllegalArgumentException(
                    "Not able to parse year from " + yearStr);
        } 
        
    }

}
