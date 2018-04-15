
package gametracker;

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

    public Game(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    
    
}
