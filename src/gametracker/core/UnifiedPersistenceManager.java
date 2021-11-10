package gametracker.core;

import gametracker.cli.MenuElement;
import gametracker.data.GameSet;
import gametracker.data.PlaySessionList;

import java.util.List;

public interface UnifiedPersistenceManager {

    public void load();

    public void save();

    public GameSet getGameSet();
    public PlaySessionList getPlaySessionList();


    public PersistenceManagerMenu getMenu();

}
