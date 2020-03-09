package gametracker.data;

import java.util.Objects;

/**
 *
 * Holds data about a game, including the name, the platform, and the year it
 * was released. A game is identified uniquely if it does not match another
 * by name, platform and year.
 *
 */
public class Game implements Comparable<Game>{

    /**
     * Enum covering platforms for games which I own :-)
     */
    public enum Platform {
        PC_Steam,
        PC_Other,
        Wii_U,
        Wii,
        GameCube,
        Nintendo_64,
        Super_Nintendo,
        DS_3DS,
        DS,
        GameBoy_Advance,
        Playstation_3,
        Playstation_2,
        Playstation,
        Mobile,
        Web,
        Unknown

    }

    /**
     * Full name of the game.
     */
    private final String name;
    /**
     * Platform the game is released on.
     */
    private final Platform platform;
    /**
     * Year the game was released.
     */
    private final int year;

    /**
     *
     * Creates a new game with the given name, platform and year of creation
     *
     * @param name full name of the game
     * @param platform platform the game was released on
     * @param year the year in which the game was released
     */
    public Game(String name, Platform platform, int year) {
        this.name = name;
        this.platform = platform;
        this.year = year;
    }

    /**
     * 
     * Returns the name of the game.
     * 
     * @return the full name of the game
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * Returns the Platform the game was released on.
     * 
     * @return the platform the game is on
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * 
     * Returns the year the game was released
     * 
     * @return the year in which the game was released
     */
    public int getYear() {
        return year;
    }

    /**
     * 
     * Returns the name, platform and year the game was released formatted
     * <code> name (platform) year</code>
     * 
     * @return a string representation of the game.
     */
    @Override
    public String toString() {
        return this.getName() + " (" + this.getPlatform() + ") - " + year;
    }

    /**
     * 
     * Returns the  hash code for the game, based on the name,
     * platform and year.
     * 
     * @return hash code calculated on the has codes of the name, the platform
     * and the year.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.platform);
        hash = 79 * hash + this.year;
        return hash;
    }

    /**
     * 
     * Compares this game with the other, returning true if the name, platform
     * and year.
     * 
     * @param other the object to compare to
     * @return true if name, platform and year match.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        final Game otherGame = (Game) other;
        if (!Objects.equals(this.name, otherGame.name)) {
            return false;
        }
        if (this.year != otherGame.year) {
            return false;
        }
        if (this.platform != otherGame.platform) {
            return false;
        }
        return true;
    }
    
    
    /**
     * 
     * Compares this game to the other, comparing the name first, then the year
     * and then the platform.
     * 
     * @param other the Game to compare
     * @return negative if this game should be first, zero if they should be 
     * the same and positive if the other game should be first.
     * 
     */
    @Override
    public int compareTo(Game other) {
        int diff =this.name.compareTo(other.name);
        if (diff != 0) {
            return diff;
        }
        diff = this.year - other.year;
        if (diff != 0) {
            return diff;
        }
        diff = this.platform.compareTo(other.platform);
        return diff;
    }

    /**
     * Parses the platform from a given string. Will return the platform which
     * matches the valueOf for the string, if possible, or throws an
     * IllegalArgumentException otherwise.
     *
     * @param platformStr the source string to try to parse a platform from
     * @return the Platform, if it can be parsed.
     */
    public static Game.Platform parsePlatform(String platformStr) {

        try {
            return Game.Platform.valueOf(platformStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Not able to parse platform from " + platformStr, e);
        }

    }

    /**
     *
     * Parses the year from a given string. Will return the year provided the
     * string describes a year (is a 4 digit int) and throws an
     * IllegalArgumentException.
     *
     * @param yearStr the source string to try to parse a year from
     * @return the year, if it can be parsed.
     */
    public static int parseYear(String yearStr) {
        int year = 0;
        if (yearStr.length() == 4) {
            try {
                return Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Not able to parse year from " + yearStr, e);
            }
        } else {
            throw new IllegalArgumentException(
                    "Not able to parse year from " + yearStr);
        }
    }

}
