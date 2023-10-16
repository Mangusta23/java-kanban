package Model;

public class SubTask extends Task {

    public SubTask(String name, String description, int epicId) {
        super(name,
                description);
        this.epicId = epicId;
        this.type = Types.SUBTASK;
    }
    protected int epicId;

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    //public void setStatus(String s) {
    //    if (s.equals(TaskStatus.NEW)) {
    //        status = TaskStatus.NEW;
    //    } else if (s.equals(TaskStatus.DONE)) {
    //        status = TaskStatus.DONE;
    //    } else if (s.equals(TaskStatus.IN_PROGRESS)) {
    //        status = TaskStatus.IN_PROGRESS;
    //    }
    //}
}
