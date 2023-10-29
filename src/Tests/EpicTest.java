package Tests;
import Manager.TaskManager;
import Manager.Managers;
import Model.Epic;
import Model.SubTask;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import Model.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EpicTest {

    @Test
    public void shouldReturnNewWhenEpicIsEmpty(){
        TaskManager taskManager = Managers.getDefault();
        Epic epic1 = new Epic("epicName1", "epicDes1", null,0);
        taskManager.addEpic(epic1);
        Assertions.assertEquals(TaskStatus.NEW, epic1.getStatus());
    }

    @Test
    public void shouldReturnNewWhenAllSubtasksNew(){
        TaskManager taskManager = Managers.getDefault();
        Epic epic1 = new Epic("epicName1", "epicDes1", null,0);
        taskManager.addEpic(epic1);
        SubTask subTask5 = new SubTask("subTaskName1", "subTaskDes", 1, LocalDateTime.of(2024, 10, 23, 15, 12), 0);
        taskManager.addSubTask(subTask5);
        SubTask subTask6 = new SubTask("subTaskName2", "subTaskDes", 1, LocalDateTime.of(2024, 10, 23, 15, 12), 0);
        taskManager.addSubTask(subTask6);
        assertEquals(TaskStatus.NEW, epic1.getStatus());
    }

    @Test
    public void shouldReturnDoneWhenAllSubtasksDone(){
        TaskManager taskManager = Managers.getDefault();
        Epic epic1 = new Epic("epicName1", "epicDes1", null,0);
        taskManager.addEpic(epic1);
        SubTask subTask5 = new SubTask("subTaskName1", "subTaskDes", 1, LocalDateTime.of(2024, 10, 23, 15, 12), 0);
        subTask5.setStatus(TaskStatus.DONE);
        taskManager.addSubTask(subTask5);
        SubTask subTask6 = new SubTask("subTaskName2", "subTaskDes", 1, LocalDateTime.of(2024, 10, 23, 15, 12), 0);
        subTask6.setStatus(TaskStatus.DONE);
        taskManager.addSubTask(subTask6);
        assertEquals(TaskStatus.DONE, epic1.getStatus());
    }

    @Test
    public void shouldReturnInProgressWhenSubtasksDoneAndNew(){
        TaskManager taskManager = Managers.getDefault();
        Epic epic1 = new Epic("epicName1", "epicDes1", null,0);
        taskManager.addEpic(epic1);
        SubTask subTask5 = new SubTask("subTaskName1", "subTaskDes", 1, LocalDateTime.of(2024, 10, 23, 15, 12), 0);
        taskManager.addSubTask(subTask5);
        subTask5.setStatus(TaskStatus.DONE);
        taskManager.updateSubTask(subTask5);
        SubTask subTask6 = new SubTask("subTaskName2", "subTaskDes", 1, LocalDateTime.of(2024, 10, 23, 15, 12), 0);
        taskManager.addSubTask(subTask6);
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatus());
    }

    @Test
    public void shouldReturnInProgressWhenAllSubtasksInProgress(){
        TaskManager taskManager = Managers.getDefault();
        Epic epic1 = new Epic("epicName1", "epicDes1", null,0);
        taskManager.addEpic(epic1);
        SubTask subTask5 = new SubTask("subTaskName1", "subTaskDes", 1, LocalDateTime.of(2024, 10, 23, 15, 12), 0);
        subTask5.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.addSubTask(subTask5);
        SubTask subTask6 = new SubTask("subTaskName2", "subTaskDes", 1, LocalDateTime.of(2024, 10, 23, 15, 12), 0);
        subTask6.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.addSubTask(subTask6);
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatus());
    }
}