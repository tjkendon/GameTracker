
package gametracker.data;

import org.joda.time.DateTime;

/**
 *
 * Basic data about a game
 * 
 * @author tjkendon
 */
public class Game {
    
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
        
        
    }
    
    private final String name;
    private final Platform platform;
    private final int year;

    public Game(String name, Platform platform, int year) {
        this.name = name;
        this.platform = platform;
        this.year = year;
    }

    public String getName() {
        return name;
    }
    
    
    
}
