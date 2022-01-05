package gametracker.cli;

import gametracker.core.PersistenceManagerFactory;
import gametracker.core.PersistenceManager;
import gametracker.data.*;
import org.apache.commons.cli.*;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 * Provides an interactive command-line interface to use the GameTracker system.
 * <p>
 * Allows the data to be viewed, filtered, aggregated and updated interactively
 * using a system of menus.
 */
public class CLI {

    public static final String VERSION = "0.4.0";

    private static final String PERSISTENCE_MANAGER_PREF = "gametracker.persistence_manager";
    private static final String PERSISTENCE_MANAGER_DEFAULT = "CSV";


    /**
     * Creates and runs a new CLI program. Configured from the command line
     * and the Java preferences to load and store the game and play session
     * data.
     *
     * @param args the standard set of arguments
     */
    public static void main(String[] args) {

        CLI cli = new CLI(args);

        cli.run();

    }

    /**
     * Menu Element that will print all games.
     */
    private final MenuElement listAllGames = new MenuElement(
            "1",
            "List All Games",
            this::listAllGames);

    /**
     * Menu element that will print all play sessions.
     */
    private final MenuElement listAllSessions = new MenuElement(
            "2",
            "List All Play Sessions",
            this::listAllPlaySessions);

    /**
     * Menu element that will print all aggregates of the current data.
     */
    private final MenuElement listAllStats = new MenuElement(
            "3",
            "Print Statistics",
            () -> printPlayAggregate(generateAggregates()));


    /**
     * The main menu list of elements.
     */
    private final List<MenuElement> mainMenu;

    /**
     * The list of elements for the menu to organize data.
     */
    private final List<MenuElement> dataMenu;
    /**
     * The list of elements for the menu to organize filters.
     */
    private final List<MenuElement> filterMenu;

    /**
     * The list of all currently applied filters.
     */
    private final List<Filter> filters;
    /**
     * The game filter currently being applied.
     */
    private final GameFilter gameFilter;
    /**
     * The date filter currently being applied.
     */
    private final DateFilter dateFilter;


    private final PersistenceManager persistenceManager;

    /**
     *
     * Creates a new CLI with the provided arguments.
     *
     * @param args the arguments to configure the CLI
     */
    public CLI(String[] args) {

        try {
            Options options = new Options();
            options.addOption(
                    "d",
                    "datamanager",
                    true,
                    "the name of the data persistence manager");
            CommandLineParser parser = new DefaultParser();
            CommandLine cl = parser.parse(options, args);
            if (cl.hasOption("datamanager")) {
                Preferences.userNodeForPackage(CLI.class).
                        put(PERSISTENCE_MANAGER_PREF, cl.getOptionValue("datamanager"));
            }
        } catch (ParseException ex) {
            Logger.getLogger(CLI.class.getName()).log(Level.SEVERE,
                    "Error Parsing Command Line Arguments",
                    ex);
        }

        persistenceManager = PersistenceManagerFactory.getManager(
                Preferences.userNodeForPackage(CLI.class).get(
                        PERSISTENCE_MANAGER_PREF,
                        PERSISTENCE_MANAGER_DEFAULT), args);

        persistenceManager.load();

        filters = new ArrayList<>();
        gameFilter = new GameFilter();
        dateFilter = new DateFilter();
        filters.add(gameFilter);
        filters.add(dateFilter);

        mainMenu = setupMainMenu();
        dataMenu = setUpDataMenu();
        filterMenu = setUpFilterMenu();
    }





    /**
     * Creates a list of menu items for the actions needed by the main menu.
     * <p>
     * Includes the three "common" items:
     * 1) list games,
     * 2) list play sessions
     * 3) list aggregate data.
     * <p>
     * Then items to add:
     * A) new play session
     * S) new game
     * <p>
     * Then to run the other menus:
     * F) manage filters
     * M) manage data
     * <p>
     * Then the Q) Quit command
     *
     * @return a list of new menu items for the main menu.
     */
    public final List<MenuElement> setupMainMenu() {

        List<MenuElement> menu = new ArrayList<>();

        menu = addCommonElements(menu);

        menu.add(new MenuElement("A", "Add new Play Session", () -> {
            try {
                LocalDate date = promptForDate(
                        "Enter Date (YYYY/mm/dd) (Blank for today)");
                if (date == null) {
                    date = LocalDate.now();
                }


                List<Game> games = promptForGame();
                if (games.size() != 1) {
                    System.out.println("Not able to add session");
                    return;
                }

                String timeStr = UIHelper.promptForString(
                        "Enter Time Played (in hours)");

                double time = PlaySession.parsePlayTime(timeStr);

                persistenceManager.getPlaySessionList().addPlaySession(new PlaySession(
                        games.get(0),
                        date,
                        time));
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

                persistenceManager.getGameSet().addGame(new Game(gameStr, platform, year));
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

    /**
     * Creates a list of menu elements needed for the data menu.
     * <p>
     * Includes the three "common" items:
     * 1) list games,
     * 2) list play sessions
     * 3) list aggregate data.
     * <p>
     * Then the commands to:
     * L) load new data
     * S) save all data
     * <p>
     * Then the commands to:
     * D) change the game data file
     * F) change the session data file
     * <p>
     * Then the command to:
     * X) reset all data
     * <p>
     * Then the Q) Quit command to return to the main menu.
     *
     * @return a list of new menu items for the data menu.
     */
    public final List<MenuElement> setUpDataMenu() {

        List<MenuElement> menu = new ArrayList<>();

        menu = addCommonElements(menu);

        //menu.add(MenuElement.BLANK);
        menu.addAll(persistenceManager.getMenu().getMenuElements(this));
        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("Q", "Quit Data Menu", true));

    return menu;

    }


    /**
     * Creates a list of menu elements needed for the filter menu.
     * <p>
     * Includes the three "common" items:
     * 1) list games,
     * 2) list play sessions
     * 3) list aggregate data.
     * <p>
     * Then the command to
     * L) list all filters
     * <p>
     * Then the commands to:
     * A) add a new game filter
     * S) add a new date filter
     * <p>
     * Then the commands to:
     * X) removes a game filter
     * Z) removes a date filter
     * C) remove all filters
     * <p>
     * Then the Q) Quit command to return to the main menu.
     *
     * @return a list of new menu items for the data menu.
     */
    public final List<MenuElement> setUpFilterMenu() {

        List<MenuElement> menu = new ArrayList<>();

        menu = addCommonElements(menu);

        // list all filters
        menu.add(new MenuElement("L", "List All Filters", this::listFilters));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("A", "Add Game Filter", () -> {
            List<Game> filterGame = promptForGame();
            gameFilter.addAllGames(filterGame);

        }));

        menu.add(new MenuElement("S", "Add Date Filter", () -> {
            LocalDate opening = promptForDate(
                    "Beginning Date (Blank for no start date)");

            LocalDate end = promptForDate(
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

        menu.add(new MenuElement("C", "Clear All Filters", () -> {
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


    /**
     * Adds the common menu elements wanted in every menu.
     * <p>
     * These include the elements to list all games, list all sessions and
     * list all aggregate date.
     *
     * @param menu the menu to add the elements to.
     * @return the menu with the elements added.
     */
    public List<MenuElement> addCommonElements(List<MenuElement> menu) {
        menu.add(listAllGames);

        menu.add(listAllSessions);

        menu.add(listAllStats);

        menu.add(MenuElement.BLANK);
        return menu;
    }

    /**
     * Runs the main menu.
     */
    public void run() {
        printPreamble();
        runMenu("Main Menu", mainMenu);
        persistenceManager.save();
        printFarewell();
    }

    /**
     * Executes a menu with the given name.
     * <p>
     * Loops, printing all of the menu elements and then prompting for an input.
     * Executes the action hook of the element and stops looping if the
     * element is set to return true to isQuitAfter.
     *
     * @param menuName the identifier for the menu
     * @param menu     the list of menu elements that form the menu
     */
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

    /**
     * Prints text for when the program is first started.
     */
    private void printPreamble() {
        printStarLine();
        System.out.println();
        printSystemName();
        System.out.println();
    }

    /**
     * Prints text when the program is exiting.
     */
    private void printFarewell() {

        System.out.println();
        printSystemName();
        printCopyRight();
        printStarLine();

    }

    private void printCopyRight() {
        System.out.println("   TJ Kendon  @tjkendon  2018 - 2019");
    }

    private void printSystemName() {
        System.out.println("   Game Tracker  Text Terminal Version  " +
                VERSION);
    }

    private void printStarLine() {
        System.out.println("*************************************************");
    }



    /**
     * Lists all of the active filters.
     */
    private void listFilters() {
        System.out.println("Active Filters:");
        int i = 1;
        for (Filter f : filters) {
            System.out.println("\t" + i + " : " + f);
            i++;
        }
        if (filters.isEmpty()) {
            System.out.println("\tNo Active Filters");
        }
    }


    private List<Game> promptForGame() {
        List<Game> returnSet = new ArrayList<>();
        String gameStr = UIHelper.promptForString("Enter Game Name");
        int matchCount = persistenceManager.getGameSet().getGamesPartialCount(gameStr);
        if (matchCount == 0) {
            System.out.println("No games match " + gameStr);

        } else if (matchCount == 1) {
            returnSet.add(persistenceManager.getGameSet().getGame(gameStr));
        } else if (matchCount > 1) {
            Set<Game> gamesPartial = persistenceManager.getGameSet().getGamesPartial(gameStr);
            System.out.println("Several Matches:");
            printNumberedGameList(gamesPartial);
            returnSet.addAll(gamesPartial);
        }
        return returnSet;

    }

    /**
     * Prompts the user to enter a date, with the given prompt.
     *
     * @param prompt the prompt to ask the user for (specifying date meaning).
     * @return the date parsed from the user's entry
     */
    private LocalDate promptForDate(String prompt) {
        return PlaySession.parseDateTime(UIHelper.promptForString(prompt));
    }

    /**
     * Lists all games.
     */
    private void listAllGames() {
        for (Game g : persistenceManager.getGameSet().getGames()) {
            System.out.println(g);
        }
    }

    /**
     * Lists all play sessions visible with the current filter.
     */
    private void listAllPlaySessions() {
        for (PlaySession s : filterPlayData().getPlaySessions()) {
            System.out.println(s);
        }
    }

    /**
     * Filters the full list of session data by the currently set filters.
     *
     * @return all play sessions that meet the currently loaded filters.
     */
    private PlaySessionList filterPlayData() {
        PlaySessionList filteredPlayData = new PlaySessionList(persistenceManager.getPlaySessionList());
        for (Filter f : filters) {
            if (!f.isEmpty()) {
                filteredPlayData = f.filter(filteredPlayData);
            }
        }
        return filteredPlayData;
    }

    /**
     * Prints a list of games with numbers, their position in the
     * list of games
     *
     * @param games the list of games to print.
     */
    private void printNumberedGameList(Collection<Game> games) {
        int i = 0;
        for (Game g : games) {
            System.out.println("\t" + i + ": " + g.getName());
            i++;
        }

    }

    /**
     * Prints a list of windows with numbers, their position in the list of
     * windows.
     *
     * @param windows the list of filter windows to print
     */
    private void printNumberedWindowList(List<DateFilter.Window> windows) {
        for (int i = 0; i < windows.size(); i++) {
            System.out.println((i + 1) + " - " + windows.get(i));
        }
    }

    /**
     * Prints all aggregate data.
     *
     * @param data the aggregate data to print.
     */
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

    /**
     * Generates all available aggregates for the filtered list of play
     * sessions.
     *
     * @return
     */
    private PlayAggregate generateAggregates() {
        PlaySessionList filteredData = filterPlayData();

        TotalTimeAggregator total
                = new TotalTimeAggregator(filteredData);
        PlayAggregate totalData = total.aggregate();

        SessionCountAggregator session
                = new SessionCountAggregator(filteredData);
        PlayAggregate sessionData = session.aggregate();

        AverageTimeAggregator average
                = new AverageTimeAggregator(filteredData);
        PlayAggregate averageData = average.aggregate();

        MedianTimeAggregator median
                = new MedianTimeAggregator(filteredData);
        PlayAggregate medianData = median.aggregate();

        totalData.mergeAggregates(sessionData, averageData, medianData);

        return totalData;
    }

}
