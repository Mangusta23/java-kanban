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
                    task.name = scanner.next();
                    System.out.println("Введите описание задачи");
                    task.description = scanner.next();
                    task.status = "new";
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
                    taskManager.changeTask(taskId);
                    break;
                case 3:
                    Epic epic = new Epic();
                    System.out.println("Введите название эпика");
                    epic.name = scanner.next();
                    System.out.println("Введите описание эпика");
                    epic.description = scanner.next();
                    epic.status = "new";
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
                    taskManager.changeEpic(taskId);
                case 5:
                    taskManager.printShortEpics();
                    System.out.println("Введите id эпика к которому будет принадлежать задача");
                    int id = scanner.nextInt();
                    if (!taskManager.epics.containsKey(id)){
                        System.out.println("Такого эпика не существует");
                        break;
                    }
                    SubTask subTask = new SubTask();
                    subTask.epicId = id;
                    System.out.println("Введите название задачи");
                    subTask.name = scanner.next();
                    System.out.println("Введите описание задачи");
                    subTask.description = scanner.next();
                    subTask.status = "new";
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
                    taskManager.printTask(taskId);
                    break;
                case 10:
                    taskManager.printAllTasks();
                    break;
                case 11:
                    taskManager.printShortEpics();
                    System.out.println("Введите id эпика");
                    id = scanner.nextInt();
                    if (!taskManager.epics.containsKey(id)){
                        System.out.println("Такого эпика не существует");
                        break;
                    }
                    taskManager.printEpicTasks(id);
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