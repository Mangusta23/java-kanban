package Manager;
import java.util.ArrayList;
import java.util.List;

import Model.*;
public class InMemoryHistoryManager implements HistoryManager {

    private ArrayList<Task> history = new ArrayList<>();
    @Override
    public void add(Task task){
        if (history.size() > MAX_SIZE) {
            history.remove(0);
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
