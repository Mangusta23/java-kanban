import Manager.InMemoryTaskManager;
import Model.Epic;
import Model.SubTask;
import Model.Task;
import Model.TaskStatus;

public class Main {
    public static void main(String[] args) {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task task1 = new Task();

        task1.setName("задача1");
        task1.setDescription("Описание_задачи_1");
        task1.setStatus(TaskStatus.NEW);
        inMemoryTaskManager.addTask(task1);

        Task task2 = new Task();

        task2.setName("задача2");
        task2.setDescription("Описание_задачи_2");
        task2.setStatus(TaskStatus.NEW);
        inMemoryTaskManager.addTask(task2);

        Epic epic1 = new Epic();
        epic1.setName("эпик1");
        epic1.setDescription("Описание_эпика_1");
        inMemoryTaskManager.addEpic(epic1);

        SubTask subTask1 = new SubTask();

        subTask1.setName("подзадача1");
        subTask1.setDescription("Описание_подзадачи_1");
        subTask1.setStatus(TaskStatus.NEW);
        subTask1.setEpicId(epic1.getId());
        inMemoryTaskManager.addSubTask(subTask1);

        SubTask subTask2 = new SubTask();

        subTask2.setName("подзадача2");
        subTask2.setDescription("Описание_подзадачи_2");
        subTask2.setStatus(TaskStatus.NEW);
        subTask2.setEpicId(epic1.getId());
        inMemoryTaskManager.addSubTask(subTask2);

        Epic epic2 =new Epic();

        epic2.setName("эпик2");
        epic2.setDescription("Описание_эпика_2");
        inMemoryTaskManager.addEpic(epic2);

        SubTask subTask3 = new SubTask();

        subTask3.setName("подзадача3");
        subTask3.setDescription("Описание_подзадачи_3");
        subTask3.setStatus(TaskStatus.NEW);
        subTask3.setEpicId(epic2.getId());
        inMemoryTaskManager.addSubTask(subTask3);

        inMemoryTaskManager.getAllTasks();
        inMemoryTaskManager.getAllSubTasks();
        System.out.println(inMemoryTaskManager.getSavedTasks());
    }
}