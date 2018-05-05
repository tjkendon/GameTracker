/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.cli;

import gametracker.data.CSVGamePersistenceManager;
import gametracker.data.CSVSessionPersistenceManager;
import gametracker.data.Game;
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

    public static final String VERSION = "0.1.0";

    public static void main(String[] args) {

        CLI cli = new CLI();

        cli.run();

    }

    private final List<MenuElement> mainMenu;

    private final PlayData mainPlayData;
    private final GameSet mainGameSet;

    private CSVSessionPersistenceManager sessionManager;
    private CSVGamePersistenceManager gameManager;

    public CLI() {

        printPreamble();

        mainGameSet = loadGames();

        mainPlayData = loadPlaySet();

        mainMenu = setupMainMenu();

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

                String gameStr = UIHelper.promptForString("Enter Game");
                Game game = mainGameSet.getGame(gameStr);

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

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("M", "Manage Data",
                () -> {
                    try {

                        String gameStr;

//                if (gameManager != null) {
//                    gameStr = UIHelper.promptForString("Game Data File", 
//                            gameManager.getFile().getName());
//                }
                        String platformStr = UIHelper.promptForString(
                                "Session Data File");

                    } catch (Exception e) {
                        System.err.println("Game not added - " + e.getMessage());
                    }

                }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("Q", "Quit Tracker", true));

        return menu;

    }

    public void run() {

        System.err.flush();
        System.out.flush();

        boolean keepRunning = true;
        do {
            MenuElement.showMenu("Main Menu", mainMenu);
            System.out.println();
            String choice = UIHelper.promptForString("Enter Menu Option");
            for (MenuElement m : mainMenu) {
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

}
