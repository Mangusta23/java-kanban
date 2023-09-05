package Manager;

import java.util.ArrayList;
import java.util.HashMap;

import Model.Epic;
import Model.SubTask;
import Model.Task;
import Model.TaskStatus;

public class InMemoryTaskManager implements TaskManager{

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();

    private ArrayList<Task> savedTasks = new ArrayList<>();
    private int nextId = 1;

    @Override
    public void addTask(Task task) {
        task.setId(nextId);
        tasks.put(task.getId(), task);
        nextId++;
    }
    @Override
    public void updateTask(Task task) {
        final Task savedTask = tasks.get(task.getId());
        savedTask.setName(task.getName());
        savedTask.setDescription(task.getDescription());
        savedTask.setStatus(task.getStatus());
    }

    @Override
    public void updateSubTask(SubTask subtask) {
        final SubTask savedSubTask = subTasks.get(subtask.getId());
        savedSubTask.setName(subtask.getName());
        savedSubTask.setDescription(subtask.getDescription());
        savedSubTask.setStatus(subtask.getStatus());
        checkEpicStatus(subtask.getId());
    }

    @Override
    public void updateEpic(Epic epic) {
        final Epic savedEpic = epics.get(epic.getId());
        savedEpic.setName(epic.getName());
        savedEpic.setDescription(epic.getDescription());
    }

    @Override
    public void addSubTask(SubTask subTask) {
        subTask.setId(nextId);
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        epic.getTasksIds().add(subTask.getId());
        checkEpicStatus(subTask.getId());
        nextId++;
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(nextId);
        epic.setStatus(TaskStatus.NEW);
        epics.put(epic.getId(), epic);
        nextId++;
    }

    @Override
    public void deleteTask(int id) {
        if (subTasks.containsKey(id)) {subTasks.remove(id);}
        if (tasks.containsKey(id)) {tasks.remove(id);}
    }

    @Override
    public void deleteAllTasks() {
        subTasks.clear();
        epics.clear();
        tasks.clear();
    }

    @Override
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
                    if (y.getStatus().equals(TaskStatus.DONE)) {
                        calcDone++;
                    } else if (y.getStatus().equals(TaskStatus.NEW)) {
                        calcNew++;
                    }
                }
            }
            if (calcDone == calcSubTasks) {
                Epic epic = epics.get(epicID);
                epic.setStatus(TaskStatus.DONE);
                epics.replace(epicID, epic);
            }else if (calcNew != calcSubTasks) {
                Epic epic = epics.get(epicID);
                epic.setStatus(TaskStatus.IN_PROGRESS);
                epics.replace(epicID, epic);
            }

        }
    }

    public Epic getEpic(int id){
        Epic epic = epics.get(id);
        return epic;
    }

    @Override
    public ArrayList<Integer> getEpicTasks(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            for (int subTaskId : epic.getTasksIds()){
                savePastTask(subTasks.get(subTaskId));
            }
            return epic.getTasksIds();
        }else{
            return null;
        }
    }

    public Task getTask(int taskId){
        savePastTask(tasks.get(taskId));
        return tasks.get(taskId);
    }

    @Override
    public ArrayList<Task> getAllTasks(){
        for (Task task : tasks.values()) {
            savePastTask(task);
        }
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks(){
        for (Task task : subTasks.values()) {
            savePastTask(task);
        }
        return new ArrayList<>(subTasks.values());
    }

    public void savePastTask(Task task){
        if (savedTasks.size() < 10) {
            savedTasks.add(task);
        }else{
            savedTasks.remove(0);
            savedTasks.add(task);
        }
    }

    public ArrayList<Task> getSavedTasks(){
        return new ArrayList<>(savedTasks);
    }
}
