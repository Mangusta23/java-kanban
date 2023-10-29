package Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
public class Epic extends Task {
    public Epic(String name, String description, LocalDateTime startTime, int duration) {
        super(name, description, startTime,
                duration);
        this.type = Types.EPIC;
    }
    protected ArrayList<Integer> tasksIds = new ArrayList<>();

    public ArrayList<Integer> getTasksIds() {
        return tasksIds;
    }

    public void setTasksIds(ArrayList<Integer> tasksIds) {
        this.tasksIds = tasksIds;
    }
    public void cleanSubTaskIds(){
        tasksIds.clear();
    }
}
