import Manager.TaskManager;
import Manager.Managers;
import Model.Epic;
import Model.SubTask;
import Model.Task;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();


        Epic epic1 = new Epic("Эпик1", "Описание_эпика_1");
        taskManager.addEpic(epic1);


        Epic epic2 = new Epic("Эпик2", "Описание_Эпика_2");
        taskManager.addEpic(epic2);


        Task task3 = new Task("Задача1", "Описание_задачи_1");
        taskManager.addTask(task3);

        Task task4 = new Task("Задача2", "Описание_задачи_2");
        taskManager.addTask(task4);


        SubTask subTask5 = new SubTask("Подзадача1", "Описание_подзадачи_1", 1);
        taskManager.addSubTask(subTask5);
        SubTask subTask6 = new SubTask("Подзадача2", "Описание_подзадачи_2", 1);
        taskManager.addSubTask(subTask6);
        SubTask subTask7 = new SubTask("Подзадача3", "Описание_подзадачи_3", 1);
        taskManager.addSubTask(subTask7);

        taskManager.getEpic(epic1.getId());
        taskManager.getEpic(epic2.getId());
        taskManager.getTask(task3.getId());
        taskManager.getTask(task4.getId());
        taskManager.getSubTask(subTask5.getId());
        taskManager.getSubTask(subTask6.getId());
        taskManager.getSubTask(subTask7.getId());
        System.out.println(taskManager.getHistory());
        taskManager.getSubTask(subTask7.getId());
        taskManager.getSubTask(subTask6.getId());
        taskManager.getSubTask(subTask5.getId());
        taskManager.getTask(task4.getId());
        taskManager.getTask(task3.getId());
        taskManager.getEpic(epic2.getId());
        taskManager.getEpic(epic1.getId());

        System.out.println(taskManager.getHistory());
        taskManager.deleteSubtasks();
        taskManager.deleteTasks();
        taskManager.deleteEpics();

    }
}