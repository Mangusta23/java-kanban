package Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import Model.Epic;
import Model.SubTask;
import Model.Task;

public class TaskManager {

    public HashMap<Integer, Task> tasks = new HashMap<>();
    public HashMap<Integer, Epic> epics = new HashMap<>();
    public HashMap<Integer, SubTask> subTasks = new HashMap<>();
    public int nextId = 1;
    Scanner scanner = new Scanner(System.in);

    public void addTask(Task task) {
        task.setId(nextId);
        tasks.put(task.getId(), task);
        nextId++;
    }

    public void changeTaskName(int id, String name) {
            if (tasks.containsKey(id)) {
                Task n = tasks.get(id);
                n.setName(name);
                tasks.put(id, n);
            } else if (subTasks.containsKey(id)) {
                SubTask n = subTasks.get(id);
                n.setName(name);
                subTasks.put(id, n);
            }
    }

    public void changeTaskDescription(int id, String description) {
            if (tasks.containsKey(id)) {
                Task n = tasks.get(id);
                n.setDescription(description);
                tasks.put(id, n);
            }else if (subTasks.containsKey(id)) {
                SubTask n = subTasks.get(id);
                n.setDescription(description);
                subTasks.put(id, n);
            }
    }

    public void changeEpicName(int id, String name) {
        Epic n = epics.get(id);
        if (epics.containsKey(id)) {
            n.setName(name);
        }
        epics.put(id, n);
    }

    public void changeEpicDescription(int id, String description) {
        Epic n = epics.get(id);
        if (epics.containsKey(id)) {
                n.setDescription(description);
            }
        epics.put(id, n);
    }

    public void addSubTask(SubTask subTask) {
        subTask.setId(nextId);
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        epic.getTasksIds().add(subTask.getId());
        nextId++;
    }

    public void addEpic(Epic epic) {
        epic.setId(nextId);
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

    public void changeTaskStatus(int taskId, int status){
        if (tasks.containsKey(taskId)) {
            Task task = tasks.get(taskId);
            if (status == 1) {
                task.setStatus("In progress");
            }else{
                task.setStatus("Done");
            }
            tasks.replace(taskId, task);
        }else{
            SubTask subTask = subTasks.get(taskId);
            if (status == 1) {
                subTask.setStatus("In progress");
            }else{
                subTask.setStatus("Done");
            }
            subTasks.replace(taskId, subTask);
        }
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
                calcSubTasks++;
                if (y.getEpicId() == epicID && y.getStatus().equals("Done")) {
                    calcDone++;
                }else if (y.getEpicId() == epicID && y.getStatus().equals("New")) {
                    calcNew++;
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

    public void printShortEpics(){
        for (Integer key : epics.keySet()) {
            Epic epic = epics.get(key);
            System.out.println("ID = " + key + " Название эпика - " + epic.getName());
        }
    }

    public Epic returnEpic(int id){
        Epic epic = epics.get(id);
        return epic;
    }

    public ArrayList<Integer> returnEpicTasks(int id) {
        Epic epic = epics.get(id);
        return epic.getTasksIds();
    }

    public Task returnTask(int taskId){
        return tasks.get(taskId);
    }

    public HashMap<Integer, Task> returnAllTasks(){
        return tasks;
    }

    public HashMap<Integer, SubTask> returnAllSubTasks(){
        return subTasks;
    }

    public void printShortTasks(){
        if (!tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                System.out.println("ID = " + task.getId() + "    Название задачи - '" + task.getName() + "'");
            }
        }
        if (!subTasks.isEmpty()){
            for (SubTask subTask : subTasks.values()) {
                System.out.println("ID = " + subTask.getId() + "    Название задачи - '" + subTask.getName() + "'");
            }
        }
    }
}
