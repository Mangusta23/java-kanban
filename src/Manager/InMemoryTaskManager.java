package Manager;

import java.time.LocalDateTime;
import java.util.*;

import Model.*;

public class InMemoryTaskManager implements TaskManager{

    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, SubTask> subTasks = new HashMap<>();

    private int nextId = 1;
    protected final HistoryManager historyManager = Managers.getDefaultHistory();
    protected final TreeSet<Task> prioritizedTasks = new TreeSet<>(new TimeSortComparator());

    @Override
    public void addTask(Task task) {
        try {
            checkTasksSameStartTime(task);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return;
        }
        task.setId(nextId);
        tasks.put(task.getId(), task);
        prioritizedTasks.add(task);
        nextId++;
    }
    @Override
    public void updateTask(Task task) {
        try {
            checkTasksSameStartTime(task);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return;
        }
        final Task savedTask = tasks.get(task.getId());
        savedTask.setName(task.getName());
        savedTask.setDescription(task.getDescription());
        savedTask.setStatus(task.getStatus());
    }

    @Override
    public void updateSubTask(SubTask subtask) {
        try {
            checkTasksSameStartTime(subtask);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return;
        }
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
        try {
            checkTasksSameStartTime(subTask);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return;
        }
        subTask.setId(nextId);
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        epic.getTasksIds().add(subTask.getId());
        checkEpicStatus(subTask.getId());
        if (epic.getStartTime() == null){
            epic.setStartTime(subTask.getStartTime());
        }
        if (epic.getDuration() == null){
            epic.setDuration(subTask.getDuration());
        }else{
            epic.setDuration(epic.getDuration().plus(subTask.getDuration()));
        }
        prioritizedTasks.add(subTask);
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
        if (subTasks.containsKey(id)) {
            int tempEpicId = subTasks.get(id).getEpicId();
            Epic epic = epics.get(tempEpicId);
            epic.setDuration(epic.getDuration().minus(subTasks.get(id).getDuration()));
            subTasks.remove(id);
            historyManager.remove(id);
            checkEpicStatus(tempEpicId);
            checkEpicStartTime(tempEpicId);
        }
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteAllTasks() {
        subTasks.clear();
        epics.clear();
        tasks.clear();
        historyManager.removeAll();
        nextId = 1;
    }
    @Override
    public void deleteTasks() {
        for (int x : tasks.keySet()){
            historyManager.remove(x);
        }
        tasks.clear();
    }
    @Override
    public void deleteSubtasks() {
        for (int x : epics.keySet()){
            historyManager.remove(x);
        }
        for (Epic epic : epics.values()) {
            epic.cleanSubTaskIds();
            checkEpicStatus(epic.getId());
        }
        subTasks.clear();
    }
    @Override
    public void deleteEpics() {
        for (int x : epics.keySet()){
            historyManager.remove(x);
        }
        epics.clear();
        for (int x : subTasks.keySet()){
            historyManager.remove(x);
        }
        subTasks.clear();
    }

    @Override
    public void deleteEpic(int epicId) {
        Epic epic = epics.get(epicId);
        for (int x : epic.getTasksIds()){
            historyManager.remove(x);
        }
        historyManager.remove(epicId);
        epics.remove(epic.getId());
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

    public void checkEpicStartTime(int epicId) {
        Epic epic = epics.get(epicId);
        for (SubTask y : subTasks.values()){
            if (y.getEpicId() == epicId){
                if (epic.getStartTime() == null || epic.getStartTime().isAfter(y.getStartTime())) {
                    epic.setStartTime(y.getStartTime());
                }
            }
        }
    }

    public void checkTasksSameStartTime(Task task) {
        Optional<Task> check = getPrioritizedTasks().stream()
                .filter(t -> {
                    LocalDateTime startTime = t.getStartTime();
                    return startTime != null && startTime.equals(task.getStartTime());
                })
                .findFirst();

        if (check.isPresent()) {
            throw new IllegalStateException("Время начала задачи " + task.getName()
                    + " не должен совпадать c добавленной ранее" + check.get().getName()
                    + "задача не записана, пожалуйста создайте задачу заново");
        }
    }

    @Override
    public Epic getEpic(int id){
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public ArrayList<Integer> getEpicTasks(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            return epic.getTasksIds();
        }else{
            return null;
        }
    }

    @Override
    public Task getTask(int taskId){
        historyManager.add(tasks.get(taskId));
        return tasks.get(taskId);
    }
    @Override
    public SubTask getSubTask(int subTaskId){
        historyManager.add(subTasks.get(subTaskId));
        return subTasks.get(subTaskId);
    }

    @Override
    public ArrayList<Task> getAllTasks(){
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks(){
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {return new ArrayList<>(epics.values());}

    @Override
    public List<Task> getHistory(){
        return historyManager.getHistory();
    }

    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }
}
