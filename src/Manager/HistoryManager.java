package Manager;
import java.util.List;

import Model.*;

public interface HistoryManager {
    void add(Task task);
    void remove(int taskId);
    List<Task> getHistory();
}
