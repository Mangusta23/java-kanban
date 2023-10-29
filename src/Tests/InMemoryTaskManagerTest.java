package Tests;

import Manager.InMemoryTaskManager;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    public InMemoryTaskManagerTest() {
        super();
        taskManager = new InMemoryTaskManager(); // Инициализация объекта taskManager
    }

}
