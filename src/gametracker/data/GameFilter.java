/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametracker.data;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author tjkendon
 */
public class GameFilter implements Filter {

    public List<Game> filterGames = new ArrayList<>();

    /**
     * 
     * Creates a new filter with no games to filter 
     * 
     * If used to filter it will return an empty PlayData.
     * 
     */
    GameFilter() {

    }

    /**
     *
     * Creates a new filter with the games listed in the arguments in the filter
     *
     * When used, it will a data set with any session using any game in the
     * filter included.
     *
     * @param games the game (or games) to add
     */
    public GameFilter(Game... games) {
        addAllGames(games);
    }

    /**
     *
     * Creates a new filter with the games in the list in the filter
     *
     * When used, it will a data set with any session using any game in the
     * filter included.
     *
     * @param games the games to add
     */
    GameFilter(List<Game> games) {
        addAllGames(games);
    }

    /**
     *
     * Adds all games to the filter. Sessions with any of the games will be
     * included when the filter is called.
     *
     * @param games the games to add to this filter
     */
    public final void addAllGames(Game... games) {
        addAllGames(Arrays.asList(games));

    }

    /**
     *
     * Adds all games in the list to the filter. Sessions with any of the games
     * will be included when the filter is called.
     *
     * @param games the list of games add to this filter
     */
    public final void addAllGames(List<Game> games) {
        filterGames.addAll(games);
    }

    /**
     *
     * Removes games from the filter. Session with any of the games will not be
     * included when the filter is called.
     *
     * @param games the games to remove from the filter
     *
     * @return true if the games could be removed
     */
    public final boolean removeAllGames(Game... games) {
        return removeAllGames(Arrays.asList(games));
    }

    /**
     *
     * Removes games in the list from the filter. Session with any of the games
     * will not be included when the filter is called.
     *
     * @param games the games to remove from the filter
     *
     * @return true if the games could be removed
     */
    public final boolean removeAllGames(List<Game> games) {
        return filterGames.removeAll(games);
    }

    /**
     *
     * Resets the filter by removing all games from it. After calling the filter
     * will be empty and if filter is called it will return an empty PlayData.
     *
     */
    @Override
    public final void clear() {
        filterGames.clear();
    }

    /**
     *
     * Returns a data set which is a copy subset of the source, including only
     * play sessions with games that have been added to the filter. A filter
     * with no games will return an empty data set.
     *
     * @param source the original play data
     *
     * @return a new play data object with deep copies of the play sessions
     */
    @Override
    public PlayData filter(PlayData source) {

        PlayData returnData = new PlayData();

        source.getPlaySessions().stream().filter((session)
                -> (filterGames.contains(
                        session.getGame()))).forEachOrdered((session)
                -> {
            returnData.addPlaySession(session);
        });

        return returnData;

    }

    /**
     *
     * Reports a list of the games in the filter.
     *
     * @return
     *
     */
    @Override
    public String toString() {
        StringBuilder br = new StringBuilder("Game Filter: [");
        // fun with java joiner example from API
        String commaSeparatedGames = filterGames.stream()
                .map(i -> i.toString())
                .collect(Collectors.joining(", "));

        br.append(commaSeparatedGames).append("]");
        return br.toString();

    }

    public List<Game> getGames() {
        return new ArrayList<>(filterGames);
    }

    @Override
    public boolean isEmpty() {
        return filterGames.isEmpty();
    }

}
