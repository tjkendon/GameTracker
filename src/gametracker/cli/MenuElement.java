/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.cli;

import java.util.List;

/**
 *
 * Element of a menu combining a key (character to enter in the menu), a
 * description, a hook to execute when the element is selected and boolean
 * to be returned after the hook has been executed (which allows you to define
 * which elements cause the menu to quit).
 * 
 * @author tjkendon
 */
public class MenuElement {

    /**
     * Menu Element to be blank
     */
    public static final MenuElement BLANK = new MenuElement("BLANK", "");

    /**
     * Menu Element to be used to divide two groups of menu elements
     */
    public static final MenuElement DIVIDER = new MenuElement("DIV", "");

    private final String key;
    private final String explanation;
    private final UIHook hook;
    private final boolean quitAfter;

    
    /**
     * Creates a menu element with no action when selected which
     * does tell the menu to stop after being chosen.
     * 
     * @param key the label to display
     * @param explanation the description of the element
     */
    public MenuElement(String key, String explanation) {
        this.key = key;
        this.explanation = explanation;
        this.hook = null;
        this.quitAfter = false;

    }

    /**
     * 
     * Creates a menu element with no action when selected which
     * tells the menu to stop after being chosen if quit after is true.
     * 
     * @param key the label to display
     * @param explanation the description of the element
     * @param quitAfter boolean to be returned after the element is chosen
     */
    public MenuElement(String key, String explanation, boolean quitAfter) {
        this.key = key;
        this.explanation = explanation;
        this.hook = null;
        this.quitAfter = quitAfter;

    }

     /**
     * 
     * Creates a menu element with the specified action when selected which
     * will tell the menu to continue after.
     * 
     * @param key the label to display
     * @param explanation the description of the element
     * @param h the action the menu should take when it is selected
     */
    public MenuElement(String key, String explanation, UIHook h) {
        this.key = key;
        this.explanation = explanation;
        this.hook = h;
        quitAfter = false;
    }

    
    /**
     * 
     * Creates a menu element with the specified action when selected  which
     * tells the menu to stop after being chosen if quit after is true.
     * 
     * @param key the label to display
     * @param explanation the description of the element
     * @param quitAfter boolean to be returned after the element is chosen
     * @param h the action the menu should take when it is selected
     */
    public MenuElement(String key, String explanation, boolean quitAfter, UIHook h) {
        this.key = key;
        this.explanation = explanation;
        this.hook = h;
        this.quitAfter = quitAfter;
    }

    public String getKey() {
        return key;
    }

    public String getExplanation() {
        return explanation;
    }

    @Override
    public String toString() {
        return key + " - " + explanation;
    }

    public boolean matches(String s) {
        return key.equalsIgnoreCase(s);
    }

    public boolean isQuitAfter() {
        return quitAfter;
    }

    /**
     * 
     * If the input matches, calls the hook (provided one has been defined) and
     * then returns the value defined in quit after to let the menu know if it
     * should continue.
     * 
     * @param input the string to test
     * @return whether the menu should continue running or not
     */
    public boolean act(String input) {

        if (matches(input)) {
            if (hook != null) {
                hook.act();
            }
            return true;
        }
        return false;
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
                System.out.println("------------------------");
            } else {
                System.out.println(element);
            }
        }
    }


}