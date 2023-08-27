import Manager.TaskManager;
import Model.Epic;
import Model.SubTask;
import Model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();

        while(true){
            printMenu();
            int command = scanner.nextInt();
            switch(command){
                case 1:
                    Task task = new Task();
                    System.out.println("Введите название задачи");
                    task.setName(scanner.next());
                    System.out.println("Введите описание задачи");
                    task.setDescription(scanner.next());
                    task.setStatus("new");
                    taskManager.addTask(task);
                    break;
                case 2:
                    taskManager.printShortTasks();
                    System.out.println("Какую задачу хотите обновить?");
                    int taskId = scanner.nextInt();
                    if (!taskManager.subTasks.containsKey(taskId) && !taskManager.tasks.containsKey(taskId)){
                        System.out.println("Такой задачи не существует");
                        break;
                    }
                    System.out.println("1 - Обновить имя");
                    System.out.println("2 - Обновить описание");
                    System.out.println("3 - Обновить имя и описание");
                    int h = scanner.nextInt();
                    switch (h){
                        case 3:
                            System.out.println("Введите новое имя");
                            String name = scanner.next();
                            taskManager.changeTaskName(taskId, name);
                        case 2:
                            System.out.println("Введите новое описание");
                            String description = scanner.next();
                            taskManager.changeTaskDescription(taskId, description);
                            break;
                        case 1:
                            System.out.println("Введите новое имя");
                            String name1 = scanner.next();
                            taskManager.changeTaskName(taskId, name1);
                            break;
                        default:
                            System.out.println("Такой команды нет");
                            break;
                    }
                    break;
                case 3:
                    Epic epic = new Epic();
                    System.out.println("Введите название эпика");
                    epic.setName(scanner.next());
                    System.out.println("Введите описание эпика");
                    epic.setDescription(scanner.next());
                    epic.setStatus("new");
                    taskManager.addEpic(epic);
                    break;
                case 4:
                    taskManager.printShortEpics();
                    System.out.println("Какой эпик хотите обновить?");
                    taskId = scanner.nextInt();
                    if (!taskManager.subTasks.containsKey(taskId) && !taskManager.tasks.containsKey(taskId)){
                        System.out.println("Такого эпика не существует");
                        break;
                    }
                    System.out.println("1 - Обновить имя");
                    System.out.println("2 - Обновить описание");
                    System.out.println("3 - Обновить имя и описание");
                    h = scanner.nextInt();
                    switch (h){
                        case 3:
                            System.out.println("Введите новое имя");
                            String name = scanner.next();
                            taskManager.changeEpicName(taskId, name);
                        case 2:
                            System.out.println("Введите новое описание");
                            String description = scanner.next();
                            taskManager.changeEpicDescription(taskId, description);
                            break;
                        case 1:
                            System.out.println("Введите новое имя");
                            String name1 = scanner.next();
                            taskManager.changeEpicName(taskId, name1);
                            break;
                        default:
                            System.out.println("Такой команды нет");
                            break;
                    }
                case 5:
                    taskManager.printShortEpics();
                    System.out.println("Введите id эпика к которому будет принадлежать задача");
                    int id = scanner.nextInt();
                    if (!taskManager.epics.containsKey(id)){
                        System.out.println("Такого эпика не существует");
                        break;
                    }
                    SubTask subTask = new SubTask();
                    subTask.setEpicId(id);
                    System.out.println("Введите название задачи");
                    subTask.setName(scanner.next());
                    System.out.println("Введите описание задачи");
                    subTask.setDescription(scanner.next());
                    subTask.setStatus("new");
                    taskManager.addSubTask(subTask);
                    break;
                case 6:
                    taskManager.printShortTasks();
                    System.out.println("Введите id задачи");
                    taskId = scanner.nextInt();
                    if (!taskManager.subTasks.containsKey(taskId) && !taskManager.tasks.containsKey(taskId)){
                        System.out.println("Такой задачи не существует");
                        break;
                    }
                    System.out.println("Какой поставить статус задачи?\n1 - In progress\n2 - Done ");
                    int status = scanner.nextInt();
                    taskManager.changeTaskStatus(taskId, status);
                    taskManager.checkEpicStatus(taskId);
                    break;
                case 7:
                    taskManager.printShortTasks();
                    System.out.println("Введите id задачи");
                    taskId = scanner.nextInt();
                    if (!taskManager.subTasks.containsKey(taskId) && !taskManager.tasks.containsKey(taskId)){
                        System.out.println("Такой задачи не существует");
                        break;
                    }
                    taskManager.deleteTask(taskId);
                    break;
                case 8:
                    taskManager.deleteAllTasks();
                    break;
                case 9:
                    taskManager.printShortTasks();
                    System.out.println("Введите id задачи");
                    taskId = scanner.nextInt();
                    if (!taskManager.subTasks.containsKey(taskId) && !taskManager.tasks.containsKey(taskId)){
                        System.out.println("Такой задачи не существует");
                        break;
                    }
                    Task task1 = taskManager.returnTask(taskId);
                    break;
                case 10:
                    HashMap<Integer, Task> tasks = taskManager.returnAllTasks();
                    HashMap<Integer, SubTask> subTasks = taskManager.returnAllSubTasks();
                    break;
                case 11:
                    taskManager.printShortEpics();
                    System.out.println("Введите id эпика");
                    id = scanner.nextInt();
                    if (!taskManager.epics.containsKey(id)){
                        System.out.println("Такого эпика не существует");
                        break;
                    }
                    Epic epic1 = taskManager.returnEpic(id);
                    ArrayList<Integer> epicIds = taskManager.returnEpicTasks(id);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Такой команды еще нет");
                    break;
            }
        }
    }

    public static void printMenu(){
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Добавить задачу");
        System.out.println("2 - Обновить задачу");
        System.out.println("3 - Добавить эпик");
        System.out.println("4 - Обновить эпик");
        System.out.println("5 - Добавить задачу в эпик");
        System.out.println("6 - Изменить статус задачи");
        System.out.println("7 - Удалить задачу");
        System.out.println("8 - Удалить все задачи");
        System.out.println("9 - Показать задачу");
        System.out.println("10 - Показать все задачи");
        System.out.println("11 - Показать задачи в одном эпике");
        System.out.println("0 - Выход");
    }
}