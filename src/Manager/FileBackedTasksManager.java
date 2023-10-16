package Manager;
import Model.Epic;
import Model.SubTask;
import Model.Task;
import Model.Types;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager{
    File file;

    public FileBackedTasksManager(File file) {
        super();
        this.file = file;
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> history = new LinkedList<>();
        String[] str = value.split(",");
        for (String s : str) {
            history.add(Integer.parseInt(s));
        }
        return history;
    }

    public static String historyToString(HistoryManager manager) {
        StringJoiner strj = new StringJoiner(",");
        for (Task task : manager.getHistory()) {
            strj.add(String.valueOf(task.getId()));
        }
        return strj.toString();
    }

    public static FileBackedTasksManager loadFromFile(File file){
        FileBackedTasksManager read = new FileBackedTasksManager(file);
        read.fileReader();
        return read;
    }
    public Task fromString(String string) {
        String[] split = string.split(", ");

        if (split.length == 6) {
            SubTask subTask = new SubTask(split[2], split[4], Integer.parseInt(split[5]));
            subTask.setType(Types.valueOf(split[1]));
            subTask.setStatus(split[3]);
            subTask.setId(Integer.parseInt(split[0]));
            return subTask;
        } else {
            Task task = new Task(split[2], split[4]);
            task.setType(Types.valueOf(split[1]));
            task.setStatus(split[3]);
            task.setId(Integer.parseInt(split[0]));
            return task;
        }

    }

    public void fileReader() throws RuntimeException {
        LinkedList<String> tasksFromFile = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                String lines = reader.readLine();
                tasksFromFile.add(lines);
            }
            tasksFromFile.removeFirst();
            for (int i = 0; i < tasksFromFile.size() - 2; i++) {
                Task task = fromString(tasksFromFile.get(i));
                switch (task.getType()) {
                    case SUBTASK:
                        subTasks.put(task.getId(), (SubTask) task);
                        break;
                    case EPIC:
                        epics.put(task.getId(), (Epic) task);
                        break;
                    case TASK:
                        tasks.put(task.getId(), task);
                        break;
                }
            }
            List<Integer> hfs = historyFromString(tasksFromFile.getLast());
            for (Integer hf : hfs) {
                if (tasks.containsKey(hf)) {
                    historyManager.add(tasks.get(hf));
                } else if (subTasks.containsKey(hf)) {
                    historyManager.add(subTasks.get(hf));
                } else if (epics.containsKey(hf)) {
                    historyManager.add(epics.get(hf));
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка, файл не найден");
        }
    }
    public void save() throws ManagerSaveException {

        try (BufferedWriter writter = new BufferedWriter(new FileWriter(file))) {

            writter.write("id,type,name,status,description,epic\n");

            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                Task value = entry.getValue();
                writter.write(value.toString() + "\n");
            }
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                Task value = entry.getValue();
                writter.write(value.toString() + "\n");
            }
            for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
                Task value = entry.getValue();
                writter.write(value.toString() + "\n");
            }

            writter.write("\n" + historyToString(historyManager));

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка, файла не существует");
        }
    }
    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    @Override
    public void deleteEpic(int epicId) {
        super.deleteEpic(epicId);
        save();
    }

    @Override
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public Epic getEpic(int epicIds) {
        Epic epic = epics.get(epicIds);
        historyManager.add(epic);
        save();
        return epic;
    }

    @Override
    public Task getTask(int taskId) {
        Task task = tasks.get(taskId);
        historyManager.add(task);
        save();
        return task;
    }

    @Override
    public SubTask getSubTask(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        historyManager.add(subTask);
        save();
        return subTask;
    }
}
