package Tests;
import Manager.TaskManager;
import Manager.Managers;
import Model.Epic;
import Model.SubTask;
import Model.Task;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    @BeforeEach
    public void BeforeEach() {
        Epic epic1 = new Epic("epicName1", "epicDes1",null,0);
        taskManager.addEpic(epic1);
        Epic epic2 = new Epic("epicName2", "epicDes2",null,0);
        taskManager.addEpic(epic2);
        Task task3 = new Task("taskName3", "taskDes3", LocalDateTime.of(2023, 10, 23, 15, 10), 0);
        taskManager.addTask(task3);
        Task task4 = new Task("taskName4", "taskDes4", LocalDateTime.of(2023, 10, 23, 15, 11), 0);
        taskManager.addTask(task4);
        SubTask subTask5 = new SubTask("subTaskName5", "subTaskDes", 1, LocalDateTime.of(2023, 10, 23, 15, 12), 0);
        taskManager.addSubTask(subTask5);
        SubTask subTask6 = new SubTask("subTaskName6", "subTaskDes", 1, LocalDateTime.of(2023, 10, 23, 15, 13), 0);
        taskManager.addSubTask(subTask6);
        SubTask subTask7 = new SubTask("subTaskName7", "subTaskDes", 1, LocalDateTime.of(2023, 10, 23, 15, 14), 0);
        taskManager.addSubTask(subTask7);
    }

    @Test
    void shouldGetAllTasks() {
        Collection<Task> tasks = taskManager.getAllTasks();
        assertEquals(2, tasks.size());
    }

    @Test
    void shouldGetAllSubTasks() {
        Collection<SubTask> subTasks = taskManager.getAllSubTasks();
        assertEquals(3, subTasks.size());
    }

    @Test
    void shouldDeleteAllSubTasks() {
        taskManager.deleteSubtasks();
        List<SubTask> subTasksList = taskManager.getAllSubTasks();
        assertTrue(subTasksList.isEmpty());
    }

    @Test
    void shouldDeleteAllTasks() {
        taskManager.deleteTasks();
        Collection<Task> tasks = taskManager.getAllTasks();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldDeleteTask() {
        taskManager.deleteTask(3);
        Collection<Task> tasks = taskManager.getAllTasks();
        assertEquals(1, tasks.size());
    }

    @Test
    void shouldGetEpic() {
        Epic epic = taskManager.getEpic(1);
        assertEquals("1, EPIC, epicName1, NEW, epicDes1, 2023.10.23 15:12, 0", epic.toString());
    }
    @Test
    void shouldGetTask() {
        assertEquals("3, TASK, taskName3, NEW, taskDes3, 2023.10.23 15:10, 0",
                taskManager.getTask(3).toString());
    }

    @Test
    void shouldAddSubTask() {
        SubTask subTask8 = new SubTask("subTaskName8", "subTaskDes", 1, LocalDateTime.of(2023, 11, 23, 15, 14), 0);

        taskManager.addSubTask(subTask8);
        assertEquals(4, taskManager.getAllSubTasks().size());
    }

    @Test
    void shouldAddTask() {
        Task task4 = new Task("taskName4", "taskDes4", LocalDateTime.of(2023, 10, 23, 16, 10), 0);

        taskManager.addTask(task4);
        assertEquals(3, taskManager.getAllTasks().size());
    }

    @Test
    void shouldUpdateTask() {
        Task task3 = new Task("taskName3", "taskDes3",  LocalDateTime.of(2024, 10, 23, 15, 10), 0);
        taskManager.addTask(task3);
        taskManager.updateTask(task3);
        Task updatedTask = taskManager.getTask(8);
        assertEquals(task3, updatedTask);
    }

    @Test
    void shouldUpdateSubTask() {
        SubTask subTask5 = new SubTask("subTaskName1", "subTaskDes1", 1,  LocalDateTime.of(2024, 10, 23, 15, 12), 0);
        taskManager.addSubTask(subTask5);
        subTask5.setName("NewName");
        taskManager.updateSubTask(subTask5);
        assertEquals("NewName", subTask5.getName());
    }

    @Test
    void shouldReturnEpicIdFromSubTask() {
        SubTask subTask5 = new SubTask("subTaskName5", "subTaskDes", 1, LocalDateTime.of(2024, 10, 23, 15, 12), 0);
        taskManager.addSubTask(subTask5);
        SubTask updatedTask = taskManager.getSubTask(8);
        assertEquals(1, updatedTask.getEpicId());
    }

    @Test
    void shouldReturnPrioritizedTasks() {
        SubTask subTask8 = new SubTask("subTaskName8", "subTaskDes", 1,  null, 0);
        taskManager.addSubTask(subTask8);

        SubTask subTask9 = new SubTask("subTaskName9", "subTaskDes", 1,  null, 0);
        taskManager.addSubTask(subTask9);

        SubTask subTask10 = new SubTask("subTaskName10", "subTaskDes", 1,  null, 0);
        taskManager.addSubTask(subTask10);

        System.out.println(taskManager.getPrioritizedTasks());
        assertEquals(8, taskManager.getPrioritizedTasks().size());

    }

    @Test
    void shouldGetEpicTasks() {
        taskManager.getEpicTasks(1);
        assertEquals(3, taskManager.getEpicTasks(1).size());
    }

    @Test
    void shouldPrintHistory() {
        List<Task> history = taskManager.getHistory();
        assertEquals(0, history.size());
    }
}
