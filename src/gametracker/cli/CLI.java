package gametracker.cli;

import gametracker.data.AverageTimeAggregator;
import gametracker.data.CSVGamePersistenceManager;
import gametracker.data.CSVSessionPersistenceManager;
import gametracker.data.DateFilter;
import gametracker.data.Filter;
import gametracker.data.Game;
import gametracker.data.GameFilter;
import gametracker.data.GamePersistenceManager;
import gametracker.data.GameSet;
import gametracker.data.MedianTimeAggregator;
import gametracker.data.PlayAggregate;
<<<<<<< HEAD
import gametracker.data.PlayData;
=======
import gametracker.data.PlaySessionList;
>>>>>>> 882f2e8304f538578381aec1117faae697785a61
import gametracker.data.PlaySession;
import gametracker.data.SessionCountAggregator;
import gametracker.data.SessionPersistenceManager;
import gametracker.data.TotalTimeAggregator;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.joda.time.LocalDate;

/**
 *
 * Provides an interactive command-line interface to use the GameTracker system.
 * 
 * Allows the data to be viewed, filtered, aggregated and updated interactively
 * using a system of menus.
 * 
 */
public class CLI {

<<<<<<< HEAD
    public static final String VERSION = "0.2.1";
=======
    /**
     * Version number for the system.
     */
    public static final String VERSION = "0.2.0";
>>>>>>> 882f2e8304f538578381aec1117faae697785a61

    /**
     * Default preference name for the game data file.
     */
    private static final String GAME_FILE_PREF = "game_file";
    /**
     * Default preference name for the play session data file.
     */
    private static final String PLAY_FILE_PREF = "play_file";

    /**
     * Default file name for the game data file.
     */
    private static final String GAME_FILE_DEFAULT = "tracker.games";
    
    /**
     * Default file name for the play session data file.
     */
    private static final String PLAY_FILE_DEFAULT = "tracker.play";

    /**
     * 
     * Creates and runs a new CLI program. Configured from the command line
     * and the Java preferences to load and store the game and play session 
     * data.
     * 
     * @param args the standard set of arguments
     */
    public static void main(String[] args) {

        String gameFile = null;
        String playFile = null;

        try {
            Options options = new Options();
            options.addOption(
                    "g",
                    "gamefile",
                    true,
                    "the file name to get the games data from");
            options.addOption(
                    "p",
                    "playfile",
                    true,
                    "the file name to get the play data from");
            CommandLineParser parser = new DefaultParser();
            CommandLine cl = parser.parse(options, args);
            if (cl.hasOption("gamefile")) {
                gameFile = cl.getOptionValue("gamefile");
            }
            if (cl.hasOption("playfile")) {
                playFile = cl.getOptionValue("playfile");
            }
        } catch (ParseException ex) {
            Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
        }

        CLI cli = new CLI(gameFile, playFile);

        cli.printPreamble();

        cli.run();

        cli.save();

        cli.savePreferenceValues();
        cli.printFarewell();

    }

    /**
     * 
     * Menu Element that will print all games.
     * 
     */
    private final MenuElement listAllGames = new MenuElement(
            "1",
            "List All Games",
            () -> {

                listAllGames();

            });

    /**
     * 
     * Menu element that will print all play sessions.
     * 
     */
    private final MenuElement listAllSessions = new MenuElement(
            "2",
            "List All Play Sessions",
            () -> {

                listAllPlaySessions();

            });

    /**
     * 
     * 
     * Menu element that will print all aggregates of the current data.
     * 
     */
    private final MenuElement listAllStats = new MenuElement(
            "3",
            "Print Statistics",
            () -> {

                printPlayAggregate(generateAggregates());

            });

    /**
     * The Java preferences to load the game data and play data session file.
     */
    private final Preferences prefs;

    /**
     * The filename for the game data file.
     */
    private String gameFileName;
    /**
     * The filename for the play session data file.
     */
    private String playFileName;

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
     * The full list of all play sessions.
     */
    private PlaySessionList mainPlayData;
    /**
     * The full list of all games.
     */
    private GameSet mainGameSet;

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

    /**
     * The manager to save session data.
     */
    private SessionPersistenceManager sessionManager;
    /**
     * The manager to save game data.
     */
    private GamePersistenceManager gameManager;

    /**
     * Creates a new CLI, using the arguments as values for filenames
     * for the game and play session data files. 
     * 
     * Sets the CLI's gameFileName and playFileName to the value saved in
     * Java preferences for this node, but will over ride those with the
     * values passed to the function.
     * 
     * @param gameFile the file name for the game data file.
     * @param playFile the file name for the play session data file.
     */
    public CLI(String gameFile, String playFile) {
        prefs = Preferences.userNodeForPackage(CLI.class);
        loadPreferenceValues();

        if (gameFile != null) {
            gameFileName = gameFile;
        }
        if (playFile != null) {
            playFileName = playFile;
        }
        mainGameSet = loadGames();

        mainPlayData = loadPlayData();

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
     * 
     * Loads games from the CLI's game data file and returns it.
     * 
     * @return the {@link GameSet} of all games loadable from the game file.
     */
    public final GameSet loadGames() {

        GameSet gs;

        try {

            System.out.println("Loading game file: " + gameFileName);
            
            gameManager = new CSVGamePersistenceManager(
                    new File(gameFileName));
            gs = gameManager.load();

        } catch (IllegalStateException e) {

            System.err.println(
                    "Not able to load game file: " + e.getMessage());
            System.out.println();
            gs = new GameSet();

        }
        return gs;

    }

    /**
     * 
     * Prompts the user to enter a file name. Prompts for a string with the
     * given purpose.
     * 
     * @param purpose the purpose of file the user is prompted for.
     * 
     * @return the string to use as a filename
     */
    public final String promptForFile(String purpose) {
        String fileName = UIHelper.promptForString(
                "Enter " + purpose + " Filename (Blank to skip)");

        return fileName;
    }

    /**
     * 
     * Loads the play session data from the CLI's session data file and
     * returns it. If the CLI doesn't have an  {@link GameSet} then
     * it will refuse to load, as it cannot link the games in the session
     * data file back to the main game data.
     * 
     * @return a list of the play session data from the file or a new 
     * {@link PlaySessionList} if it cannot load.
     */
    public final PlaySessionList loadPlayData() {

        if (mainGameSet.isEmpty()) {
            System.out.println();
            System.err.println(
                    "Cowardly skipping loading play set without game data"
                    + " available, creating new data instead.");

            sessionManager = new CSVSessionPersistenceManager(
                    new File(playFileName),
                    mainGameSet);

            return new PlaySessionList();
        }

        PlaySessionList data;

        try {

            sessionManager = new CSVSessionPersistenceManager(
                    new File(playFileName),
                    mainGameSet);

            System.out.println("Loading session file: " + playFileName);
            
            data = sessionManager.load();

        } catch (IllegalStateException e) {
            System.out.println(
                    "Not able to load session file: " + e.getMessage());
            System.out.println();
            data = new PlaySessionList();
        }
        return data;

    }

    /**
     * 
     * Creates a list of menu items for the actions needed by the main menu.
     * 
     * Includes the three "common" items:
     *  1) list games,
     *  2) list play sessions
     *  3) list aggregate data. 
     * 
     * Then items to add:
     *  A) new play session
     *  S) new game
     * 
     * Then to run the other menus:
     *  F) manage filters
     *  M) manage data
     *  
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

                Double time = PlaySession.parsePlayTime(timeStr);

                mainPlayData.addPlaySession(new PlaySession(
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

    /**
     * 
     * Creates a list of menu elements needed for the data menu.
     * 
     * Includes the three "common" items:
     *  1) list games,
     *  2) list play sessions
     *  3) list aggregate data. 
     * 
     * Then the commands to:
     *  L) load new data
     *  S) save all data
     * 
     * Then the commands to:
     *  D) change the game data file
     *  F) change the session data file
     * 
     * Then the command to:
     *  X) reset all data
     * 
     * Then the Q) Quit command to return to the main menu.
     * 
     * @return a list of new menu items for the data menu.
     */
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
                    mainPlayData = loadPlayData();

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
                gameFileName = newName;
                gameManager = new CSVGamePersistenceManager(
                        new File(gameFileName));
            }

        }));

        menu.add(new MenuElement("F", "Change Session Data File", () -> {

            String newName = UIHelper.promptForString(
                    "Enter new Session Data File Name "
                    + "(Blank to leave unchanged)");
            if (!newName.isEmpty()) {
                playFileName = newName;
                sessionManager
                        = new CSVSessionPersistenceManager(
                                new File(playFileName),
                                mainGameSet);
            }

        }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("X", "Clear game and session data", () -> {

            boolean sure = UIHelper.promptForBoolean(
                    "Are you seure you want to clear all data");
            if (sure) {
                mainGameSet = new GameSet();

                mainPlayData = new PlaySessionList();

                sessionManager = new CSVSessionPersistenceManager(
                        new File(playFileName),
                        mainGameSet);

            }

        }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("Q", "Quit Data Menu", true));

        return menu;

    }

    
    /**
     * 
     * Creates a list of menu elements needed for the filter menu.
     * 
     * Includes the three "common" items:
     *  1) list games,
     *  2) list play sessions
     *  3) list aggregate data. 
     * 
     * Then the command to 
     *  L) list all filters
     * 
     * Then the commands to:
     *  A) add a new game filter
     *  S) add a new date filter
     * 
     * Then the commands to:
     *  X) removes a game filter
     *  Z) removes a date filter
     *  C) remove all filters
     * 
     * Then the Q) Quit command to return to the main menu.
     * 
     * @return a list of new menu items for the data menu.
     */
    public final List<MenuElement> setUpFilterMenu() {

        List<MenuElement> menu = new ArrayList<>();

        menu = addCommonElements(menu);

        // list all filters
        menu.add(new MenuElement("L", "List All Filters", () -> {

            listFilters();

        }));

        menu.add(MenuElement.BLANK);

        menu.add(new MenuElement("A", "Add Game Filter", () -> {
            List<Game> filterGame = promptForGame();
            gameFilter.addAllGames(filterGame);

        }));

        menu.add(new MenuElement("S", "Add Date Filter", () -> {
            LocalDate opening = promptForDate(
                    "Begining Date (Blank for no start date)");

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
     * 
     * Adds the common menu elements wanted in every menu.
     * 
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
     * 
     * Runs the main menu.
     * 
     */
    
    public void run() {
        runMenu("Main Menu", mainMenu);
    }

    /**
     * 
     * Executes a menu with the given name.
     * 
     * Loops, printing all of the menu elements and then prompting for an input.
     * Executes the action hook of the element and stops looping if the
     * element is set to return true to isQuitAfter.
     * 
     * @param menuName the identifier for the menu
     * @param menu the list of menu elements that form the menu
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
     * 
     * Prints text for when the program is first started.
     * 
     */
    private void printPreamble() {
        printStarLine();
        System.out.println();
        printSystemName();
        System.out.println();
    }

    /**
     * 
     * Prints text when the program is exiting.
     * 
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
     * 
     * Saves both the game data and the session data.
     * 
     * @return 
     */
    private boolean save() {
        
        
        
        return saveGameData() & saveSessionData();
    }

    /**
     * 
     * Saves the game data with the game persistence manager.
     * 
     * @return true if the data could be saved, false if there were any errors
     */
    private boolean saveGameData() {
        try {
       
            System.out.println("Saving Game Data to: " + gameFileName);
            
            gameManager.saveGameSet(mainGameSet);
            System.out.println("Game data saved");

        } catch (IllegalStateException e) {
            System.out.println("Not able to save data - "
                    + e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    /**
     * 
     * Saves the session data with the session persistence manager.
     * 
     * @return 
     */
    private boolean saveSessionData() {

        try {

            System.out.println("Saving Session Data to: " +  playFileName);
            
            sessionManager.savePlayData(mainPlayData);
            System.out.println("Session data saved");

        } catch (IllegalStateException e) {
            System.out.println("Not able to save data - "
                    + e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    /**
     * 
     * Lists all of the active filters.
     * 
     */
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

<<<<<<< HEAD
    private List<Game> promptForGame() {
        List<Game> returnSet = new ArrayList<>();
=======
    /**
     * 
     * Prompts the user to enter a game name.
     * 
     * @return the game name entered by the user.
     */
    private Game promptForGame() {
>>>>>>> 882f2e8304f538578381aec1117faae697785a61
        String gameStr = UIHelper.promptForString("Enter Game Name");
        int matchCount = mainGameSet.getGamesPartialCount(gameStr);
        if (matchCount == 0) {
          System.out.println("No games match " + gameStr);  
          
        } else if (matchCount == 1) {
            returnSet.add(mainGameSet.getGame(gameStr));
        } else if (matchCount > 1) {
            Set<Game> gamesPartial = mainGameSet.getGamesPartial(gameStr);
            System.out.println("Several Matches:");
            printNumberedGameList(gamesPartial);
            returnSet.addAll(gamesPartial);
        }
        return returnSet;
        
    }

<<<<<<< HEAD
    
    
    private DateTime promptForDate(String prompt) {
=======
    /**
     * 
     * Prompts the user to enter a date, with the given prompt.
     * 
     * @param prompt the prompt to ask the user for (specifying date meaning).
     * @return the date parsed from the user's entry
     */
    private LocalDate promptForDate(String prompt) {
>>>>>>> 882f2e8304f538578381aec1117faae697785a61
        String dateStr = UIHelper.promptForString(prompt);
        LocalDate date = PlaySession.parseDateTime(dateStr);

        return date;
    }

    /**
     * 
     * Lists all games.
     * 
     */
    private void listAllGames() {
        for (Game g : mainGameSet.getGames()) {
            System.out.println(g);
        }
    }

    /**
     * 
     * Lists all play sessions visible with the current filter.
     * 
     */
    private void listAllPlaySessions() {
        for (PlaySession s : filterPlayData().getPlaySessions()) {
            System.out.println(s);
        }
    }

    /**
     * 
     * Filters the full list of session data by the currently set filters.
     * 
     * @return all play sessions that meet the currently loaded filters.
     */
    private PlaySessionList filterPlayData() {
        PlaySessionList filteredPlayData = new PlaySessionList(mainPlayData);
        for (Filter f : filters) {
            if (!f.isEmpty()) {
                filteredPlayData = f.filter(filteredPlayData);
            }
        }
        return filteredPlayData;
    }

<<<<<<< HEAD
    private void printNumberedGameList(Collection<Game> games) {
        int i = 1;
        for (Game g : games) {
            System.out.println("\t" + i++ +": " + g.getName());
=======
    /**
     * 
     * Prints a list of games with numbers, their position in the
     * list of games
     * 
     * @param games the list of games to print.
     * 
     */
    private void printNumberedGameList(List<Game> games) {
        for (int i = 0; i < games.size(); i++) {
            System.out.println((i + 1) + " - " + games.get(i));
>>>>>>> 882f2e8304f538578381aec1117faae697785a61
        }
        
    }

    /**
     * 
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
     * 
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
     * 
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

    /**
     * 
     * Loads the game data and play session data file names from Java
     * preferences.
     * 
     */
    private void loadPreferenceValues() {

        gameFileName = prefs.get(GAME_FILE_PREF, GAME_FILE_DEFAULT);
        playFileName = prefs.get(PLAY_FILE_PREF, PLAY_FILE_DEFAULT);

    }

    /**
     * 
     * Saves the game data and play session file names to Java
     * preferences.
     * 
     */
    private void savePreferenceValues() {

        prefs.put(GAME_FILE_PREF, gameFileName);
        prefs.put(PLAY_FILE_PREF, playFileName);

    }

}
