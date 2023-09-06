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
        if (element instanceof Task || element instanceof SubTask || element instanceof Epic) {
            InMemoryTaskManager taskManager = new InMemoryTaskManager();
            return taskManager;
        }else{
            return null;
        }
    };
}
