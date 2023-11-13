package Manager;
import Model.*;

import Manager.InMemoryTaskManager;
import Manager.TaskManager;
import java.io.File;
import java.io.IOException;

public class Managers {
    private static final String SERVER_NAME = "http://localhost:8080";
    public static TaskManager getDefault() {
        try {
            return new HttpTaskManager(SERVER_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TaskManager getDefaultIMTM() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
