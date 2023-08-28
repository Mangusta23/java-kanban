package Model;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected String status;


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return "'ID' " + id + " 'name' " + name + " 'Description' " + description + " 'Status' " + status;
    }
}
