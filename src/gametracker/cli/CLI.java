/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.cli;

import gametracker.data.AverageTimeAggregator;
import gametracker.data.CSVGamePersistenceManager;
import gametracker.data.CSVSessionPersistenceManager;
import gametracker.data.DateFilter;
import gametracker.data.Filter;
import gametracker.data.Game;
import gametracker.data.GameFilter;
import gametracker.data.GameSet;
import gametracker.data.MedianTimeAggregator;
import gametracker.data.PlayAggregate;
import gametracker.data.PlaySession;
import gametracker.data.PlayData;
import gametracker.data.SessionCountAggregator;
import gametracker.data.TotalTimeAggregator;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;
import org.joda.time.DateTime;

/**
 *
 * @author tjkendon
 */
public class CLI {

    public static final String VERSION = "0.2.0";

    private static final String GAME_FILE_PREF = "game_file";
    private static final String PLAY_FILE_PREF = "play_file";

    private static final String GAME_FILE_DEFAULT = "tracker.games";
    private static final String PLAY_FILE_DEFAULT = "tracker.play";

    public static void main(String[] args) {

        CLI cli = new CLI();

        cli.printPreamble();

        cli.run();

        if (!cli.save()) {
            UIHelper.promptForBoolean("Data Not Saved, quit anyway?");
        }

        cli.savePreferenceValues();
        cli.printFarewell();

    }

    private final MenuElement listAllGames = new MenuElement(
            "1",
            "List All Games",
            () -> {

                listAllGames();

            });

    private final MenuElement listAllSessions = new MenuElement(
            "2",
            "List All Play Sessions",
            () -> {

                listAllPlaySessions();

            });

    private final MenuElement listAllStats = new MenuElement(
            "3",
            "Print Statistics",
            () -> {

                printPlayAggregate(generateAggregates());

            });

    private final Preferences prefs;

    private String gameFileName;
    private String playFileName;

    private final List<MenuElement> mainMenu;
    private final List<MenuElement> dataMenu;

    private final List<MenuElement> filterMenu;

    private PlayData mainPlayData;
    private GameSet mainGameSet;

    private final List<Filter> filters;
    private final GameFilter gameFilter;
    private final DateFilter dateFilter;

    private CSVSessionPersistenceManager sessionManager;
    private CSVGamePersistenceManager gameManager;

    public CLI() {

        prefs = Preferences.userNodeForPackage(CLI.class);
        loadPreferenceValues();

        mainGameSet = loadGames();

        mainPlayData = loadPlaySet();

        filters = new ArrayList<>();
        gameFilter = new GameFilter();
        dateFilter = new DateFilter();
        filters.add(gameFilter);
        filters.add(dateFilter);

        mainMenu = setupMainMenu();
        dataMenu = setUpDataMenu();
        filterMenu = setUpFilterMenu();

    }

    public final GameSet loadGames() {

        GameSet gs;

        try {

            gameManager = new CSVGamePersistenceManager(
                    new File(gameFileName));
            gs = gameManager.load();

        } catch (IllegalStateException e) {
            System.out.println(
                    "Not able to load game file: " + e.getMessage());
            System.out.println();
            gs = new GameSet();
        }
        return gs;

    }

    public final String promptForFile(String purpose) {
        String fileName = UIHelper.promptForString(
                "Enter " + purpose + " Filename (Blank to skip)");

        return fileName;
    }

    public final PlayData loadPlaySet() {

        if (mainGameSet.isEmpty()) {
            System.out.println();
            System.out.println(
                    "Cowardly skipping loading play set without game data"
                    + " available");
            sessionManager = new CSVSessionPersistenceManager();
            sessionManager.setGameSet(mainGameSet);
            return new PlayData();
        }

        PlayData data;

        try {

            sessionManager = new CSVSessionPersistenceManager(
                    new File(playFileName),
                    mainGameSet);

            data = sessionManager.load();

        } catch (IllegalStateException e) {
            System.out.println(
                    "Not able to load session file: " + e.getMessage());
            System.out.println();
            data = new PlayData();
        }
        return data;

    }

    public final List<MenuElement> setupMainMenu() {

        List<MenuElement> menu = new ArrayList<>();

        menu = addCommonElements(menu);

        menu.add(new MenuElement("A", "Add new Play Session", () -> {
            try {
                DateTime date = promptForDate(
                        "Enter Date (YYYY/mm/dd) (Blank for today)");
                if (date == null) {
                    date = DateTime.now();
                }

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

        menu.add(new MenuElement("F", "Manage Filters",
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

        menu = addCommonElements(menu);

        menu.add(new MenuElement("L", "Load new Data", () -> {

            String filename = promptForFile("game");
            if (!filename.isEmpty()) {
                gameFileName = filename;
                mainGameSet = loadGames();

                System.out.println();
                System.out.println("Game Data Loaded");

                filename = promptForFile("play");
                if (!filename.isEmpty()) {
                    playFileName = filename;
                    mainPlayData = loadPlaySet();

                    System.out.println();
                    System.out.println("Play Data Loaded");

                } else {

                }

            } else {
                System.out.println("Skipping loading all data");
            }

        }));

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

        menu = addCommonElements(menu);

        // list all filters
        menu.add(new MenuElement("L", "List All Filters", () -> {

            listFilters();

        }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("A", "Add Game Filter", () -> {
            Game filterGame = promptForGame();
            gameFilter.addAllGames(filterGame);

        }));

        menu.add(new MenuElement("S", "Add Date Filter", () -> {
            DateTime opening = promptForDate(
                    "Begining Date (Blank for no start date)");

            DateTime end = promptForDate(
                    "End Date (Blank for no end date)");

            dateFilter.addWindow(opening, end);

        }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("X", "Remove Game Filter", () -> {
            List<Game> games = gameFilter.getGames();
            printNumberedGameList(games);

            try {

                String filterString
                        = UIHelper.promptForString(
                                "Enter Game Number to Remove");
                int index = Integer.parseInt(filterString) - 1;
                Game removeGame = games.get(index);
                gameFilter.removeAllGames(removeGame);

            } catch (NumberFormatException
                    | java.lang.IndexOutOfBoundsException e) {
                System.out.println("Not able to remove game.");
            }

        }));

        menu.add(new MenuElement("Z", "Remove Date Filter", () -> {
            List<DateFilter.Window> windows = dateFilter.getWindows();
            printNumberedWindowList(windows);

            try {

                String filterString
                        = UIHelper.promptForString(
                                "Enter Window Number to Remove");
                int index = Integer.parseInt(filterString) - 1;
                DateFilter.Window window = windows.get(index);
                dateFilter.removeWindow(window);

            } catch (NumberFormatException
                    | java.lang.IndexOutOfBoundsException e) {
                System.out.println("Not able to remove window.");
            }

        }));

        menu.add(new MenuElement("C", "Clear Filters", () -> {
            boolean doit = UIHelper.promptForBoolean(
                    "Are you sure you want to remove all filters?");

            if (doit) {
                for (Filter f : filters) {
                    f.clear();
                }
                System.out.println("All filters removed");
            } else {
                System.out.println("Filters not removed");
            }

        }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("Q", "Quit Filter Menu", true));

        return menu;

    }

    public List<MenuElement> addCommonElements(List<MenuElement> menu) {
        menu.add(listAllGames);

        menu.add(listAllSessions);

        menu.add(listAllStats);

        menu.add(MenuElement.BLANK);
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
        int i = 1;
        for (Filter f : filters) {
            System.out.println("\t" + i++ + " : " + f);
        }
        if (filters.isEmpty()) {
            System.out.println("\tNo Active Filters");
        }
    }

    private Game promptForGame() {
        String gameStr = UIHelper.promptForString("Enter Game Name");
        Game game = mainGameSet.getGame(gameStr);
        return game;
    }

    private DateTime promptForDate(String prompt) {
        String dateStr = UIHelper.promptForString(prompt);
        DateTime date = PlaySession.parseDateTime(dateStr);

        return date;
    }

    private void listAllGames() {
        for (Game g : mainGameSet.getGames()) {
            System.out.println(g);
        }
    }

    private void listAllPlaySessions() {
        for (PlaySession s : filterPlayData().getPlaySessions()) {
            System.out.println(s);
        }
    }

    private PlayData filterPlayData() {
        PlayData filteredPlayData = new PlayData(mainPlayData);
        for (Filter f : filters) {
            if (!f.isEmpty()) {
                filteredPlayData = f.filter(filteredPlayData);
            }
        }
        return filteredPlayData;
    }

    private void printNumberedGameList(List<Game> games) {
        for (int i = 0; i < games.size(); i++) {
            System.out.println((i + 1) + " - " + games.get(i));
        }
    }

    private void printNumberedWindowList(List<DateFilter.Window> windows) {
        for (int i = 0; i < windows.size(); i++) {
            System.out.println((i + 1) + " - " + windows.get(i));
        }
    }

    public static void printPlayAggregate(PlayAggregate data) {

        System.out.format("%40s  %5s  %5s  %5s  %5s%n",
                "Game",
                "Total",
                "Num.",
                "Mean",
                "Med.");

        List<Game> sortedGames = new ArrayList<>(
                data.getAggregates().keySet());
        Collections.sort(sortedGames);

        for (Game g : sortedGames) {

            Double totalTime = data.getAggregatesForGame(g).get(
                    PlayAggregate.AggregateType.TOTAL_TIME);
            String totalTimeStr = "-";
            if (totalTime != null) {
                totalTimeStr = String.format("%.2f", totalTime);
            }

            Double totalCount = data.getAggregatesForGame(g).get(
                    PlayAggregate.AggregateType.TOTAL_COUNT);
            String totalCountStr = "-";
            if (totalCount != null) {
                totalCountStr = String.format("%.2f", totalCount);
            }

            Double averageTime = data.getAggregatesForGame(g).get(
                    PlayAggregate.AggregateType.AVERAGE_TIME);
            String averageTimeStr = "-";
            if (averageTime != null) {
                averageTimeStr = String.format("%.2f", averageTime);
            }

            Double medianTime = data.getAggregatesForGame(g).get(
                    PlayAggregate.AggregateType.MEDIAN_TIME);
            String medianTimeStr = "-";
            if (medianTime != null) {
                medianTimeStr = String.format("%.2f", medianTime);
            }

            System.out.format("%40s  %5s  %5s  %5s  %5s%n",
                    g.getName(),
                    totalTimeStr,
                    totalCountStr,
                    averageTimeStr,
                    medianTimeStr
            );

        }

    }

    private PlayAggregate generateAggregates() {
        PlayData filteredData = filterPlayData();

        TotalTimeAggregator total = new TotalTimeAggregator(filteredData);
        PlayAggregate totalData = total.aggregate();

        SessionCountAggregator session = new SessionCountAggregator(filteredData);
        PlayAggregate sessionData = session.aggregate();

        AverageTimeAggregator average = new AverageTimeAggregator(filteredData);
        PlayAggregate averageData = average.aggregate();

        MedianTimeAggregator median = new MedianTimeAggregator(filteredData);
        PlayAggregate medianData = median.aggregate();

        totalData.mergeAggregates(sessionData, averageData, medianData);

        return totalData;
    }

    private void loadPreferenceValues() {

        gameFileName = prefs.get(GAME_FILE_PREF, GAME_FILE_DEFAULT);
        playFileName = prefs.get(PLAY_FILE_PREF, PLAY_FILE_DEFAULT);

    }

    public void savePreferenceValues() {

        prefs.put(GAME_FILE_PREF, gameFileName);
        prefs.put(PLAY_FILE_PREF, playFileName);

    }

}
