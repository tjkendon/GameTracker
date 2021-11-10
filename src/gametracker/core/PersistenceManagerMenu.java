package gametracker.core;

import gametracker.cli.CLI;
import gametracker.cli.MenuElement;

import java.util.List;

public interface PersistenceManagerMenu {
    List<MenuElement> getMenuElements(CLI cli);
}
