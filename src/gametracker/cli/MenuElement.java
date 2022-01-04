/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.cli;

import java.util.List;

/**
 *
 * Provides an mechanism to tie options in a menu to code actions. Allows for
 * menu to be constructed offering different options which can be executed
 * tied to a particular character the user can enter.
 * 
 * Each element combines a key (character to enter in the menu), a
 * description, a hook to execute when the element is selected and an exist 
 * status.
 * 
 */
public class MenuElement {

    /**
     * Menu Element that provides a blank line between other elements.
     */
    public static final MenuElement BLANK = new MenuElement("BLANK", "");

    /**
     * Menu Element that provides a divider between other elements.
     */
    public static final MenuElement DIVIDER = new MenuElement("DIV", "");

    /**
     * The thing typed by the user to run the hook
     */
    private final String key;
    /**
     * The text explanation of what the hook will do.
     */
    private final String explanation;
    /**
     * The snippit of code that will be executed
     */
    private final UIHook hook;
    /**
     * boolean to determine if the menu should quit after the element
     * is selected.
     */
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

    /**
     * Returns the key for this element.
     * 
     * @return the string that should be matched for this element hook to run
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the explanation for this element.
     * 
     * @return the string describing this element.
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * 
     * Returns the key and explanation for this element.
     * 
     * @return the key and explanation divided by " - " 
     */
    @Override
    public String toString() {
        return key + " - " + explanation;
    }

    /**
     * 
     * Checks if the given string matches the key of this element.
     * 
     * @param s the string to compare to the key
     * @return true if the key equals the string (ignoring case)
     */
    public boolean matches(String s) {
        return key.equalsIgnoreCase(s);
    }

    /**
     * 
     * Returns the isQuitAfter value of the element. If this is true, the menu
     * should stop running after the element is chosen.
     * 
     * @return true if the menu should quite after this element is selected
     */
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
