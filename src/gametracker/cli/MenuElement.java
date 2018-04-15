/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.cli;

/**
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

    public MenuElement(String key, String explanation) {
        this.key = key;
        this.explanation = explanation;
        this.hook = null;
        this.quitAfter = false;

    }

    public MenuElement(String key, String explanation, boolean quitAfter) {
        this.key = key;
        this.explanation = explanation;
        this.hook = null;
        this.quitAfter = quitAfter;

    }

    public MenuElement(String key, String explanation, UIHook h) {
        this.key = key;
        this.explanation = explanation;
        this.hook = h;
        quitAfter = false;
    }

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

    public boolean act(String s) {

        if (matches(s)) {
            if (hook != null) {
                hook.act();
            }
            return true;
        }
        return false;
    }

}
