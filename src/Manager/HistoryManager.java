package Manager;
import java.util.List;

import Model.*;

public interface HistoryManager {
    static final int MAX_SIZE = 10;
    void add(Task task);
    List<Task> getHistory();
}
