import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {

    public HashMap<Integer, Task> tasks = new HashMap<>();
    public HashMap<Integer, Epic> epics = new HashMap<>();
    public HashMap<Integer, SubTask> subTasks = new HashMap<>();
    public int nextId = 1;
    Scanner scanner = new Scanner(System.in);

    public void addTask(Task task) {
        task.id = nextId;
        tasks.put(task.id, task);
        nextId++;
    }

    public void changeTask(int id){
        System.out.println("Обновить имя? 1-да 2-нет");
        int command = scanner.nextInt();
        if (command == 1){
            if (tasks.containsKey(id)) {
                Task n = tasks.get(id);
                System.out.println("Введите новое имя");
                String name = scanner.next();
                n.name = name;
                tasks.put(id, n);
            }else if (subTasks.containsKey(id)) {
                SubTask n = subTasks.get(id);
                System.out.println("Введите новое имя");
                String name = scanner.next();
                n.name = name;
                subTasks.put(id, n);
            }
        }
        System.out.println("Обновить описание? 1-да 2-нет");
        int command1 = scanner.nextInt();
        if (command1 == 1){
            if (tasks.containsKey(id)) {
                Task n = tasks.get(id);
                System.out.println("Введите новое описаиние");
                String description = scanner.next();
                n.description = description;
                tasks.put(id, n);
            }else if (subTasks.containsKey(id)) {
                SubTask n = subTasks.get(id);
                System.out.println("Введите новое описание");
                String description = scanner.next();
                n.description = description;
                subTasks.put(id, n);
            }
        }
    }

    public void changeEpic(int id){
        Epic n = epics.get(id);
        System.out.println("Обновить имя? 1-да 2-нет");
        int command = scanner.nextInt();
        if (command == 1){
            if (epics.containsKey(id)) {
                System.out.println("Введите новое имя");
                String name = scanner.next();
                n.name = name;
            }
        }
        System.out.println("Обновить описание? 1-да 2-нет");
        int command1 = scanner.nextInt();
        if (command1 == 1){
            if (epics.containsKey(id)) {
                System.out.println("Введите новое описаиние");
                String description = scanner.next();
                n.description = description;
            }
        }
        epics.put(id, n);
    }

    public void addSubTask(SubTask subTask) {
        subTask.id = nextId;
        subTasks.put(subTask.id, subTask);
        Epic epic = epics.get(subTask.epicId);
        epic.tasksIds.add(subTask.id);
        nextId++;
    }

    public void addEpic(Epic epic) {
        epic.id = nextId;
        epics.put(epic.id, epic);
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
                task.status = "In progress";
            }else{
                task.status = "Done";
            }
            tasks.replace(taskId, task);
        }else{
            SubTask subTask = subTasks.get(taskId);
            if (status == 1) {
                subTask.status = "In progress";
            }else{
                subTask.status = "Done";
            }
            subTasks.replace(taskId, subTask);
        }
    }

    public void checkEpicStatus(int taskId){
        if (subTasks.containsKey(taskId)) {
            int epicID = 0;
            for (SubTask x : subTasks.values()) {
                if (x.id == taskId) {
                    epicID = x.epicId;
                }
            }
            int calcSubTasks = 0;
            int calcDone = 0;
            int calcNew = 0;
            for (SubTask y : subTasks.values()) {
                calcSubTasks++;
                if (y.epicId == epicID && y.status.equals("Done")) {
                    calcDone++;
                }else if (y.epicId == epicID && y.status.equals("New")) {
                    calcNew++;
                }
            }
            if (calcDone == calcSubTasks) {
                Epic epic = epics.get(epicID);
                epic.status = "Done";
                epics.replace(epicID, epic);
            }else if (calcNew != calcSubTasks) {
                Epic epic = epics.get(epicID);
                epic.status = "In progress";
                epics.replace(epicID, epic);
            }
        }
    }

    public void printShortEpics(){
        for (Integer key : epics.keySet()) {
            Epic epic = epics.get(key);
            System.out.println("ID = " + key + " Название эпика - " + epic.name);
        }
    }

    public void printEpicTasks(int id) {
        Epic epic = epics.get(id);
        System.out.println("Название эпика - '" + epic.name + "'\nОписание эпика:\n'" + epic.description + "'\nСтатус эпика: " + epic.status);
        for (SubTask subTask : subTasks.values()) {
            if (subTask.epicId == id) {
                System.out.println(subTask.name);
                System.out.println("ID = " + subTask.id + "    Название эпика - '" + subTask.name
                        + "'\nОписание задачи:\n'" + subTask.description + "'\nСтатус задачи: " + subTask.status);
            }
        }
    }

    public void printTask(int taskId){
        if (tasks.containsKey(taskId)) {
            Task task = tasks.get(taskId);
            System.out.println("ID = " + taskId + "    Название задачи - '" + task.name + "'\nОписание задачи:\n'"
                    + task.description + "'\nСтатус задачи: " + task.status);
        } else if (subTasks.containsKey(taskId)) {
            SubTask subTask = subTasks.get(taskId);
            System.out.println("ID = " + taskId + "    Название задачи - '" + subTask.name + "'\nОписание задачи:\n'"
                    + subTask.description + "'\nСтатус задачи: " + subTask.status);
        }
    }

    public void printAllTasks(){
        for (int i = 1; i < nextId; i++){
            if(tasks.containsKey(i)){
                Task task = tasks.get(i);
                System.out.println("ID = " + i + "    Название задачи - '" + task.name + "'\nОписание задачи:\n'"
                        + task.description + "'\nСтатус задачи: " + task.status);
            } else if (subTasks.containsKey(i)) {
                SubTask subTask = subTasks.get(i);
                System.out.println("ID = " + i + "    Название задачи - '" + subTask.name + "'\nОписание задачи:\n'"
                        + subTask.description + "'\nСтатус задачи: " + subTask.status);
            }
        }
    }

    public void printShortTasks(){
        if (!tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                System.out.println("ID = " + task.id + "    Название задачи - '" + task.name + "'");
            }
        }
        if (!subTasks.isEmpty()){
            for (SubTask subTask : subTasks.values()) {
                System.out.println("ID = " + subTask.id + "    Название задачи - '" + subTask.name + "'");
            }
        }
    }
}
