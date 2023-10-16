package Model;

import java.util.ArrayList;
public class Epic extends Task {
    public Epic(String name, String description) {
        super(name, description);
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
