package Manager;
import Model.*;

import Manager.InMemoryTaskManager;
import Manager.TaskManager;

public class Managers<T> {
    public T element;
    public Managers(T element){
        this.element = element;
    }
    public TaskManager getDefault(){
        return new InMemoryTaskManager();
    };

    public static InMemoryHistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
