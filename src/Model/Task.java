package Model;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected TaskStatus status;
    protected Types type;

    public Task(String name, String description) {
        this.name = name;
        this.status = TaskStatus.NEW;
        this.description = description;
        this.type = Types.TASK;
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
        return "'ID' " + id + " 'name' " + name + " 'Description' " + description + " 'Status' " + status;
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
}
