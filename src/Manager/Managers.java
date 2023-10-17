package Manager;
import Model.*;

import Manager.InMemoryTaskManager;
import Manager.TaskManager;
import java.io.File;

public class Managers {
    public static TaskManager getDefault() {
        File file = new File(".\\src\\Resources", "Tasks.txt");
        return new FileBackedTasksManager(file);
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
