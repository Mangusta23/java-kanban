import Manager.TaskManager;
import Model.Epic;
import Model.SubTask;
import Model.Task;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task task1 = new Task();

        task1.setName("задача1");
        task1.setDescription("Описание_задачи_1");
        task1.setStatus("new");
        taskManager.addTask(task1);

        Task task2 = new Task();

        task2.setName("задача2");
        task2.setDescription("Описание_задачи_2");
        task2.setStatus("new");
        taskManager.addTask(task2);

        Epic epic1 = new Epic();
        epic1.setName("эпик1");
        epic1.setDescription("Описание_эпика_1");
        taskManager.addEpic(epic1);

        SubTask subTask1 = new SubTask();

        subTask1.setName("подзадача1");
        subTask1.setDescription("Описание_подзадачи_1");
        subTask1.setStatus("new");
        subTask1.setEpicId(epic1.getId());
        taskManager.addSubTask(subTask1);

        SubTask subTask2 = new SubTask();

        subTask2.setName("подзадача2");
        subTask2.setDescription("Описание_подзадачи_2");
        subTask2.setStatus("new");
        subTask2.setEpicId(epic1.getId());
        taskManager.addSubTask(subTask2);

        Epic epic2 =new Epic();

        epic2.setName("эпик2");
        epic2.setDescription("Описание_эпика_2");
        taskManager.addEpic(epic2);

        SubTask subTask3 = new SubTask();

        subTask3.setName("подзадача3");
        subTask3.setDescription("Описание_подзадачи_3");
        subTask3.setStatus("new");
        subTask3.setEpicId(epic2.getId());
        taskManager.addSubTask(subTask3);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getEpicTasks(epic1.getId()));
        System.out.println(epic1.getStatus());
        System.out.println(taskManager.getEpicTasks(epic2.getId()));
        System.out.println(epic2.getStatus());
        System.out.println(taskManager.getAllSubTasks() + "\n");

        subTask3.setStatus("Done");
        taskManager.updateSubTask(subTask3);
        subTask1.setStatus("Done");
        taskManager.updateSubTask(subTask1);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getEpicTasks(epic1.getId()));
        System.out.println(epic1.getStatus());
        System.out.println(taskManager.getEpicTasks(epic2.getId()));
        System.out.println(epic2.getStatus());
        System.out.println(taskManager.getAllSubTasks() + "\n");

        taskManager.deleteTask(subTask3.getId());
        taskManager.deleteEpic(epic2);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getEpicTasks(epic1.getId()));
        System.out.println(epic1.getStatus());
        System.out.println(taskManager.getEpicTasks(epic2.getId()));
        System.out.println(epic2.getStatus());
        System.out.println(taskManager.getAllSubTasks() + "\n");
    }
}