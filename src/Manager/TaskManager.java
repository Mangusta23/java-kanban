package Manager;

import java.util.ArrayList;
import java.util.HashMap;

import Model.Epic;
import Model.SubTask;
import Model.Task;

public class TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private int nextId = 1;

    public void addTask(Task task) {
        task.setId(nextId);
        tasks.put(task.getId(), task);
        nextId++;
    }
    public void updateTask(Task task) {
        final Task savedTask = tasks.get(task.getId());
        savedTask.setName(task.getName());
        savedTask.setDescription(task.getDescription());
        savedTask.setStatus(task.getStatus());
    }

    public void updateSubTask(SubTask subtask) {
        final SubTask savedSubTask = subTasks.get(subtask.getId());
        savedSubTask.setName(subtask.getName());
        savedSubTask.setDescription(subtask.getDescription());
        savedSubTask.setStatus(subtask.getStatus());
        checkEpicStatus(subtask.getId());
    }

    public void updateEpic(Epic epic) {
        final Epic savedEpic = epics.get(epic.getId());
        savedEpic.setName(epic.getName());
        savedEpic.setDescription(epic.getDescription());
    }

    public void addSubTask(SubTask subTask) {
        subTask.setId(nextId);
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        epic.getTasksIds().add(subTask.getId());
        checkEpicStatus(subTask.getId());
        nextId++;
    }

    public void addEpic(Epic epic) {
        epic.setId(nextId);
        epic.setStatus("new");
        epics.put(epic.getId(), epic);
        nextId++;
    }

    public void deleteTask(int id) {
        if (subTasks.containsKey(id)) {subTasks.remove(id);}
        if (tasks.containsKey(id)) {tasks.remove(id);}
    }

    public void deleteAllTasks() {
        subTasks.clear();
        epics.clear();
        tasks.clear();
    }

    public void deleteEpic(Epic epic) {
        Epic deletedEpic = epics.remove(epic.getId());
    }

    public void checkEpicStatus(int taskId){
        if (subTasks.containsKey(taskId)) {
            int epicID = 0;
            for (SubTask x : subTasks.values()) {
                if (x.getId() == taskId) {
                    epicID = x.getEpicId();
                }
            }
            int calcSubTasks = 0;
            int calcDone = 0;
            int calcNew = 0;
            for (SubTask y : subTasks.values()) {
                if(y.getEpicId() == epicID){
                    calcSubTasks++;
                    if (y.getStatus().equals("Done")) {
                        calcDone++;
                    } else if (y.getStatus().equals("new")) {
                        calcNew++;
                    }
                }
            }
            if (calcDone == calcSubTasks) {
                Epic epic = epics.get(epicID);
                epic.setStatus("Done");
                epics.replace(epicID, epic);
            }else if (calcNew != calcSubTasks) {
                Epic epic = epics.get(epicID);
                epic.setStatus("In progress");
                epics.replace(epicID, epic);
            }

        }
    }

    public Epic getEpic(int id){
        Epic epic = epics.get(id);
        return epic;
    }

    public ArrayList<Integer> getEpicTasks(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            return epic.getTasksIds();
        }else{

            return null;
        }
    }

    public Task getTask(int taskId){
        return tasks.get(taskId);
    }

    public ArrayList<Task> getAllTasks(){
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getAllSubTasks(){
        return new ArrayList<>(subTasks.values());
    }
}
