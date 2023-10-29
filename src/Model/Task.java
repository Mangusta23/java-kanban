package Model;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected TaskStatus status;
    protected Types type;
    protected LocalDateTime startTime;
    protected Duration duration;

    public Task(String name, String description, LocalDateTime startTime, int duration) {
        this.name = name;
        this.status = TaskStatus.NEW;
        this.description = description;
        this.type = Types.TASK;
        this.startTime = startTime;
        this.duration = Duration.ofMinutes(duration);
    }

    public void setType(Types type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return id + ", " +
                type + ", " +
                name + ", " +
                status + ", " +
                description + ", " +
                (startTime != null ? startTime.format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm")):
                        "null") + ", " +
                (duration != null ? duration.toSeconds() : "null");
    }
    public void setStatus(String s) {
        if (s.equals(TaskStatus.NEW)) {
            status = TaskStatus.NEW;
        } else if (s.equals(TaskStatus.DONE)) {
            status = TaskStatus.DONE;
        } else if (s.equals(TaskStatus.IN_PROGRESS)) {
            status = TaskStatus.IN_PROGRESS;
        }
    }
    public Types getType() {
        return type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        if (startTime == null) return null;
        else return startTime.plus(duration);
    }
}
