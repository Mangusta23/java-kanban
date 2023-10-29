package Tests;

import Manager.FileBackedTasksManager;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    File file = new File(".\\src\\Resources", "Tasks.csv");

    public FileBackedTasksManagerTest() {
        super();
        taskManager = new FileBackedTasksManager(file);
    }

    @Test
    void shouldSave() {
        taskManager.getTask(3);
        taskManager.getTask(4);
        taskManager.save();
        taskManager.fileReader();
        assertAll(
                () -> assertEquals(2, taskManager.getAllTasks().size()),
                () -> assertEquals(3, taskManager.getAllSubTasks().size()),
                () -> assertEquals(2, taskManager.getHistory().size()));
    }

    @Test
    void shouldSaveWithoutTasks() throws IOException {
        taskManager.deleteTasks();
        taskManager.save();
        taskManager.fileReader();

        assertAll(
                () -> assertEquals(0, taskManager.getAllTasks().size()),
                () -> assertEquals(3, taskManager.getAllSubTasks().size()),
                () -> assertEquals(0, taskManager.getHistory().size()));
    }

    @Test
    void shouldSaveWithoutSubtasks() {
        taskManager.deleteSubtasks();
        taskManager.save();
        taskManager.fileReader();

        assertAll(
                () -> assertEquals(2, taskManager.getAllTasks().size()),
                () -> assertEquals(0, taskManager.getAllSubTasks().size()),
                () -> assertEquals(0, taskManager.getHistory().size()));
    }
}
