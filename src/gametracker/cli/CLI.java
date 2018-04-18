/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.cli;

import gametracker.data.Game;
import gametracker.data.GameSet;
import gametracker.data.PlaySession;
import gametracker.data.PlayData;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author tjkendon
 */
public class CLI {


    public static void main(String[] args) {

        CLI cli = new CLI();

        cli.run();

    }

    private final List<MenuElement> mainMenu;

    private final PlayData mainPlaySet;
    private final GameSet mainGameSet;

    public CLI() {

        mainGameSet = loadGames();

        mainPlaySet = loadPlaySet();

        mainMenu = setupMainMenu();

    }

    public final GameSet loadGames() {
        GameSet gs = new GameSet();

        gs.addGame(new Game("Stardew Valley", Game.Platform.PC_Steam, 2016));

        return gs;
    }

    public final PlayData loadPlaySet() {
        return new PlayData();
    }

    public final List<MenuElement> setupMainMenu() {

        List<MenuElement> menu = new ArrayList<>();

        menu.add(new MenuElement("1", "List All Games", () -> {

            for (Game g : mainGameSet.getGames()) {
                System.out.println(g);
            }

        }));

        menu.add(new MenuElement("2", "List All Play Sessions", () -> {

            for (PlaySession s : mainPlaySet.getPlaySessions()) {
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

                mainPlaySet.addPlaySession(new PlaySession(game, date, time));
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

}