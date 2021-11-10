package gametracker.core;

import gametracker.cli.CLI;
import gametracker.cli.MenuElement;
import gametracker.cli.UIHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class FilePersistenceManagerMenu implements PersistenceManagerMenu {

    private final FilePersistenceManager manager;

    public FilePersistenceManagerMenu(FilePersistenceManager manager) {
        this.manager = manager;
    }

    @Override
    public List<MenuElement> getMenuElements(CLI cli) {

        List<MenuElement> menu = new ArrayList<>();

        menu.add(new MenuElement("L", "Load new Data", () -> {

            String filename = promptForFile("data");
            if (!filename.isEmpty()) {
                manager.setFile(filename);
                manager.load();

                System.out.println();
                System.out.println("Data Loaded from " + manager.getFileName());


            } else {
                System.out.println("Skipping loading new data");
            }

        }));

        menu.add(new MenuElement("S", "Save all Data", () -> {
            manager.save();
            System.out.println("Data Saved to " + manager.getFileName());
        }));

        menu.add(new MenuElement("F", "Change File Name", () -> {
            String filename = promptForFile("data");
            manager.setFile(filename);

            manager.save();
            System.out.println("Data Saved to " + manager.getFileName());

        }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("X", "Clear game and session data", () -> {

            boolean sure = UIHelper.promptForBoolean(
                    "Are you sure you want to clear all data");
            if (sure) {
                manager.clear();
                manager.save();
            }

            System.out.println("Data reset for " + manager.getFileName());

        }));


        return menu;

    }

    /**
     * Prompts the user to enter a file name. Prompts for a string with the
     * given purpose.
     *
     * @param purpose the purpose of file the user is prompted for.
     * @return the string to use as a filename
     */
    public final String promptForFile(String purpose) {

        return UIHelper.promptForString(
                "Enter " + purpose + " Filename (Blank to skip)");
    }




}
