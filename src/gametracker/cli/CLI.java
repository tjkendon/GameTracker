/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.cli;

import gametracker.data.CSVGamePersistenceManager;
import gametracker.data.CSVSessionPersistenceManager;
import gametracker.data.Filter;
import gametracker.data.Game;
import gametracker.data.GameFilter;
import gametracker.data.GameSet;
import gametracker.data.PlaySession;
import gametracker.data.PlayData;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author tjkendon
 */
public class CLI {

    public static final String VERSION = "0.2.0";

    public static void main(String[] args) {

        CLI cli = new CLI();

        cli.printPreamble();

        cli.run();

        if (!cli.save()) {
            UIHelper.promptForBoolean("Data Not Saved, quit anyway?");
        }

        cli.printFarewell();

    }

    private final List<MenuElement> mainMenu;
    private final List<MenuElement> dataMenu;

    private final List<MenuElement> filterMenu;

    private PlayData mainPlayData;
    private GameSet mainGameSet;

    private List<Filter> filters;

    private CSVSessionPersistenceManager sessionManager;
    private CSVGamePersistenceManager gameManager;

    public CLI() {

        mainGameSet = loadGames();

        mainPlayData = loadPlaySet();

        filters = new ArrayList<>();

        mainMenu = setupMainMenu();
        dataMenu = setUpDataMenu();
        filterMenu = setUpFilterMenu();

    }

    public final GameSet loadGames() {
        System.out.println();

        do {
            try {
                String fileName = UIHelper.promptForString(
                        "Enter Game File (S to skip)");

                if (UIHelper.checkFor(fileName, "S", "Skip")) {
                    System.out.println("Starting with no game data");
                    return new GameSet();
                }

                gameManager = new CSVGamePersistenceManager(new File(fileName));

                GameSet gs = gameManager.load();
                return gs;
            } catch (IllegalStateException e) {
                System.out.println(
                        "Not able to load game file: " + e.getMessage());
                System.out.println();
            }
        } while (true);

    }

    public final PlayData loadPlaySet() {
        System.out.println();

        if (mainGameSet.isEmpty()) {
            System.out.println(
                    "Cowardly skipping loading play set without game data"
                    + " available");
            sessionManager = new CSVSessionPersistenceManager();
            sessionManager.setGameSet(mainGameSet);
            return new PlayData();
        }

        do {
            try {

                String fileName = UIHelper.promptForString(
                        "Enter Session File (S to skip)");

                if (UIHelper.checkFor(fileName, "S", "Skip")) {
                    System.out.println("Starting with no session data");
                    return new PlayData();
                }

                sessionManager = new CSVSessionPersistenceManager(
                        new File(fileName),
                        mainGameSet);

                PlayData data = sessionManager.load();

                return data;
            } catch (IllegalStateException e) {
                System.out.println(
                        "Not able to load session file: " + e.getMessage());
                System.out.println();
            }

        } while (true);

    }

    public final List<MenuElement> setupMainMenu() {

        List<MenuElement> menu = new ArrayList<>();

        menu.add(new MenuElement("1", "List All Games", () -> {

            for (Game g : mainGameSet.getGames()) {
                System.out.println(g);
            }

        }));

        menu.add(new MenuElement("2", "List All Play Sessions", () -> {

            for (PlaySession s : mainPlayData.getPlaySessions()) {
                System.out.println(s);
            }

        }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("A", "Add new Play Session", () -> {
            try {
                String dateStr = UIHelper.promptForString(
                        "Enter Date (YYYY/mm/dd) (Blank for today)");

                DateTime date = PlaySession.parseDateTime(dateStr);

                Game game = promptForGame();

                String timeStr = UIHelper.promptForString(
                        "Enter Time Played (in hours)");

                Double time = PlaySession.parsePlayTime(timeStr);

                mainPlayData.addPlaySession(new PlaySession(game, date, time));
            } catch (Exception e) {
                System.err.println("Session not added - " + e.getMessage());
            }

        }));

        menu.add(new MenuElement("S", "Add new Game", () -> {
            try {
                String gameStr = UIHelper.promptForString("Game Name");

                String platformStr = UIHelper.promptForString("Game Platform");

                Game.Platform platform = Game.parsePlatform(platformStr);

                String yearStr = UIHelper.promptForString(
                        "Game Year");
                int year = Game.parseYear(yearStr);

                mainGameSet.addGame(new Game(gameStr, platform, year));
            } catch (Exception e) {
                System.err.println("Game not added - " + e.getMessage());
            }

        }));

        menu.add(new MenuElement("F", "Filter Data",
                () -> {
                    runMenu("Filter Menu", filterMenu);

                }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("M", "Manage Data",
                () -> {
                    runMenu("Data Menu", dataMenu);

                }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("Q", "Quit Tracker", true));

        return menu;

    }

    public final List<MenuElement> setUpDataMenu() {

        List<MenuElement> menu = new ArrayList<>();

        menu.add(new MenuElement("S", "Save all Data", () -> {

            try {
                saveGameData();
            } catch (IllegalStateException e) {
                System.out.println("Not able to save game data - "
                        + e.getLocalizedMessage());
            }

            try {
                saveSessionData();
            } catch (IllegalStateException e) {
                System.out.println("Not able to save play data - "
                        + e.getLocalizedMessage());
            }

        }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("D", "Change Game Data File", () -> {

            String newName = UIHelper.promptForString(
                    "Enter new Game Data File Name (Blank to leave unchanged)");
            if (!newName.isEmpty()) {
                gameManager.setDatafile(new File(newName));
            }

        }));

        menu.add(new MenuElement("F", "Change Session Data File", () -> {

            String newName = UIHelper.promptForString(
                    "Enter new Session Data File Name "
                    + "(Blank to leave unchanged)");
            if (!newName.isEmpty()) {
                sessionManager.setDatafile(new File(newName));
            }

        }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("X", "Clear game and session data", () -> {

            boolean sure = UIHelper.promptForBoolean(
                    "Are you seure you want to clear all data");
            if (sure) {
                mainGameSet = new GameSet();
                mainPlayData = new PlayData();
                sessionManager.clearDataFile();
                sessionManager.setGameSet(mainGameSet);
            }

        }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("Q", "Quit Data Menu", true));

        return menu;

    }

    public final List<MenuElement> setUpFilterMenu() {

        List<MenuElement> menu = new ArrayList<>();

        // list all filters
        menu.add(new MenuElement("L", "List All Filters", () -> {

            listFilters();

        }));
        
        menu.add(new MenuElement("A", "Add Game Filter", () -> {
            Game filterGame = promptForGame();
            filters.add(new GameFilter(filterGame));
                    
        }));

        
        // add window to filter
        // remove game from filter
        // remove window from filter
        // clear filter
        menu.add(new MenuElement("Q", "Quit Filter Menu", true));

        return menu;

    }

    public void run() {
        runMenu("Main Menu", mainMenu);
    }

    public void runMenu(String menuName, List<MenuElement> menu) {

        boolean keepRunning = true;
        do {

            System.err.flush();
            System.out.flush();

            MenuElement.showMenu(menuName, menu);
            System.out.println();
            String choice = UIHelper.promptForString("Enter Menu Option");
            for (MenuElement m : menu) {
                if (m.act(choice)) {
                    keepRunning = !m.isQuitAfter();
                }
            }
            System.out.println();

        } while (keepRunning);

    }

    private void printPreamble() {
        System.out.println("****************************************");
        System.out.println();
        System.out.println("Game Tracker - Text Terminal Version - " + VERSION);
        System.out.println();
    }

    private void printFarewell() {

        System.out.println();
        System.out.println("Game Tracker - Text Terminal Version - " + VERSION);
        System.out.println("TJ Kendon - @tjkendon - 2018");
        System.out.println("****************************************");

    }

    private boolean save() {
        return saveGameData() && saveSessionData();
    }

    private boolean saveGameData() {
        try {
            if (mainGameSet.hasChanged()) {
                gameManager.saveGameSet(mainGameSet);
                System.out.println("Game data saved");
            } else {
                System.out.println("Game data has not changed");
            }
        } catch (IllegalStateException e) {
            System.out.println("Not able to save data - "
                    + e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    private boolean saveSessionData() {

        try {

            if (mainPlayData.hasChanged()) {
                sessionManager.savePlayData(mainPlayData);
                System.out.println("Session data saved");
            } else {
                System.out.println("Session data has not changed");
            }

        } catch (IllegalStateException e) {
            System.out.println("Not able to save data - "
                    + e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    private void listFilters() {
        System.out.println("Active Filters:");
        for (Filter f : filters) {
            System.out.println("\t" + f);
        }
        if (filters.isEmpty()) {
            System.out.println("\tNo Active Filters");
        }
    }
    
    private Game promptForGame(){
        String gameStr = UIHelper.promptForString("Enter Game Name");
        Game game = mainGameSet.getGame(gameStr);
        return game;
    }

}
