package Manager;

import Manager.InMemoryTaskManager;
import Manager.TaskManager;

public class Managers {
    public TaskManager getDefault(){
        TaskManager taskManager = new InMemoryTaskManager();
        return taskManager;
    };
}
