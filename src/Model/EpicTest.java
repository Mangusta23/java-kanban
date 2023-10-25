package Model;
import Manager.TaskManager;
import Manager.Managers;
import Model.Epic;
import Model.SubTask;
import Model.Task;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EpicTest {

    private static TaskManager taskManager;
    Epic epic1;


    @BeforeAll
    public static void BeforeAll(){
        taskManager = Managers.getDefault();
    }

    @Test
    public void shouldReturnNewWhenEpicIsEmpty(){
        Epic epic1 = new Epic("Эпик1", "Описание_эпика_1");
        taskManager.addEpic(epic1);
        assertEquals(TaskStatus.NEW, epic1.getStatus());
    }

    @Test
    public void shouldReturnNewWhenAllSubtasksNew(){
        Epic epic1 = new Epic("Эпик1", "Описание_эпика_1");
        taskManager.addEpic(epic1);
        assertEquals(epic1.getStatus(), TaskStatus.NEW);
        SubTask subTask5 = new SubTask("Подзадача1", "Описание_подзадачи_1", 1);
        taskManager.addSubTask(subTask5);
        SubTask subTask6 = new SubTask("Подзадача2", "Описание_подзадачи_2", 1);
        taskManager.addSubTask(subTask6);
        assertEquals(TaskStatus.NEW, epic1.getStatus());
    }

    @Test
    public void shouldReturnDoneWhenAllSubtasksDone(){
        Epic epic1 = new Epic("Эпик1", "Описание_эпика_1");
        taskManager.addEpic(epic1);
        SubTask subTask5 = new SubTask("Подзадача1", "Описание_подзадачи_1", 1);
        subTask5.setStatus(TaskStatus.DONE);
        taskManager.addSubTask(subTask5);
        SubTask subTask6 = new SubTask("Подзадача2", "Описание_подзадачи_2", 1);
        subTask6.setStatus(TaskStatus.DONE);
        taskManager.addSubTask(subTask6);
        assertEquals(TaskStatus.DONE, epic1.getStatus());
    }

    @Test
    public void shouldReturnInProgressWhenSubtasksDoneAndNew(){
        Epic epic1 = new Epic("Эпик1", "Описание_эпика_1");
        taskManager.addEpic(epic1);
        SubTask subTask5 = new SubTask("Подзадача1", "Описание_подзадачи_1", 1);
        taskManager.addSubTask(subTask5);
        subTask5.setStatus(TaskStatus.DONE);
        taskManager.updateSubTask(subTask5);
        SubTask subTask6 = new SubTask("Подзадача2", "Описание_подзадачи_2", 1);
        taskManager.addSubTask(subTask6);
        System.out.println(epic1.getStatus());
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatus());
    }

    @Test
    public void shouldReturnInProgressWhenAllSubtasksInProgress(){
        Epic epic1 = new Epic("Эпик1", "Описание_эпика_1");
        taskManager.addEpic(epic1);
        assertEquals(epic1.getStatus(), TaskStatus.NEW);
        SubTask subTask5 = new SubTask("Подзадача1", "Описание_подзадачи_1", 1);
        subTask5.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.addSubTask(subTask5);
        SubTask subTask6 = new SubTask("Подзадача2", "Описание_подзадачи_2", 1);
        subTask6.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.addSubTask(subTask6);
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatus());
    }
}