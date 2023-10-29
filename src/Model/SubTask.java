package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SubTask extends Task {

    public SubTask(String name, String description, int epicId, LocalDateTime startTime, int duration) {
        super(name, description, startTime, duration);
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

    @Override
    public String toString() {
        return id + ", " +
                type + ", " +
                name + ", " +
                status + ", " +
                description + ", " +
                epicId + ", " +
                (startTime != null ? startTime.format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm")):
                        "null") + ", " +
                (duration != null ? duration.toSeconds() : "null");
    }

}
