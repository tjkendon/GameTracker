package gametracker.core;

import gametracker.cli.CLI;
import gametracker.cli.MenuElement;
import gametracker.cli.UIHelper;

import java.util.List;
import java.util.prefs.Preferences;

public class TypedJSONPersistenceManagerMenu extends FilePersistenceManagerMenu  {

    public TypedJSONPersistenceManagerMenu(TypedJSONPersistenceManager manager) {
        super(manager);

    }


}
