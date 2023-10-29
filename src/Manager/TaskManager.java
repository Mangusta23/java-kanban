package Manager;
import java.util.Collection;
import java.util.List;
import Model.*;
import java.util.TreeSet;

public interface TaskManager {

    void addTask (Task task);
    void updateTask (Task task);
    void addSubTask (SubTask subTask);
    void updateSubTask (SubTask subTask);
    void addEpic (Epic epic);
    void updateEpic (Epic epic);
    void deleteTask(int id);
    void deleteAllTasks();
    void deleteTasks();
    void deleteSubtasks();
    void deleteEpics();
    void deleteEpic(int id);
    List<Integer> getEpicTasks(int id);
    List<Task> getAllTasks();
    List<SubTask> getAllSubTasks();
    Task getTask(int id);
    SubTask getSubTask(int id);
    Epic getEpic(int id);
    List<Task> getHistory();

    TreeSet<Task> getPrioritizedTasks();
}
