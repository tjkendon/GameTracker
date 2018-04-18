
package gametracker.data;

import java.util.Objects;

/**
 *
 * Basic data about a game, including the name, the platform, and the year it 
 * was released.
 * 
 * @author tjkendon
 */
public class Game {
    
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
        Unknown
        
    }
    
    private final String name;
    private final Platform platform;
    private final int year;

    /**
     * 
     * Creates a new game with the given name, platform and year of creation
     * 
     * @param name
     * @param platform
     * @param year 
     */
    public Game(String name, Platform platform, int year) {
        this.name = name;
        this.platform = platform;
        this.year = year;
    }
    

    public String getName() {
        return name;
    }

    public Platform getPlatform() {
        return platform;
    }

    public int getYear() {
        return year;
    }
    
    public String toString() {
        return this.getName() + " (" + this.getPlatform() + ") - " + year;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.platform);
        hash = 79 * hash + this.year;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (this.year != other.year) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.platform != other.platform) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
