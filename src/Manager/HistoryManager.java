package Manager;
import java.util.ArrayList;
import Model.*;

public interface HistoryManager {
    void add(Task task);
    ArrayList<Task> getHistory();
}
