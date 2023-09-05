package Manager;
import java.util.ArrayList;
import Model.*;

public interface TaskManager {

    void addTask (Task task);
    void updateTask (Task task);
    void addSubTask (SubTask subTask);
    void updateSubTask (SubTask subTask);
    void addEpic (Epic epic);
    void updateEpic (Epic epic);
    void deleteTask(int id);
    void deleteAllTasks();
    void deleteEpic(Epic epic);
    ArrayList<Integer> getEpicTasks(int id);
    ArrayList<Task> getAllTasks();
    ArrayList<SubTask> getAllSubTasks();
}
