/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.cli;

import gametracker.data.Game;
import gametracker.data.GameSet;
import gametracker.data.PlaySession;
import gametracker.data.PlaySet;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author tjkendon
 */
public class CLI {

    //TODO - Comment Everything    
    
    /**
     *
     */
    public static DateTimeFormatter SESSION_DATE_FORMAT = DateTimeFormat.forPattern("yyyy/MM/dd");

    public static void main(String[] args) {

        CLI cli = new CLI();

        cli.run();

    }

    private final List<MenuElement> mainMenu;

    private final PlaySet mainPlaySet;
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

    public final PlaySet loadPlaySet() {
        return new PlaySet();
    }

    public final List<MenuElement> setupMainMenu() {

        List<MenuElement> menu = new ArrayList<>();

        menu.add(new MenuElement("A", "Add new Menu Session", () -> {
            String dateStr = UIHelper.promptForString(
                    "Enter Date (YYYY/mm/dd) (Blank for today)");
            String gameStr = UIHelper.promptForString("Enter Game");
            String timeStr = UIHelper.promptForString(
                    "Enter Time Played (in hours)");

            Game game = mainGameSet.getGame(gameStr);

            DateTime date;
            if (dateStr.isEmpty()) {
                date = new DateTime();
            } else {
                try {
                    date = DateTime.parse(dateStr, SESSION_DATE_FORMAT);
                } catch (IllegalArgumentException e) {
                    System.err.println("Not able to parse date " + dateStr);
                    return;
                }
            }

            Double time = Double.parseDouble(timeStr);

            mainPlaySet.addPlaySession(new PlaySession(game, date, time));

        }));

        menu.add(new MenuElement("1", "List All Play Sessions", () -> {

            for (PlaySession s : mainPlaySet.getPlaySessions()) {
                System.out.println(s);
            }

        }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("Q", "Quit Tracker", true));

        return menu;

    }

    public void run() {

        boolean keepRunning = true;
        do {
            UIHelper.showMenu("Main Menu", mainMenu);
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
