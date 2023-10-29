package Manager;
import Exceptions.ManagerSaveException;
import Model.Epic;
import Model.SubTask;
import Model.Task;
import Model.Types;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTasksManager(File file) {
        super();
        this.file = file;
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> history = new LinkedList<>();
        String[] str = value.split(", ");
        for (String s : str) {
            history.add(Integer.parseInt(s));
        }
        return history;
    }

    public static String historyToString(HistoryManager manager) {
        StringJoiner strj = new StringJoiner(", ");
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm");

        Task task;

        if (Types.valueOf(split[1]).equals(Types.SUBTASK)) {
            task = new SubTask(split[2],
                    split[4],
                    Integer.parseInt(split[5]),
                    LocalDateTime.parse(split[6], formatter),
                    Integer.parseInt(split[7]));
        } else if (Types.valueOf(split[1]).equals(Types.TASK)) {
            task = new Task(split[2],
                    split[4],
                    (split[5].equals("null") ? null : (LocalDateTime.parse(split[5], formatter))),
                    Integer.parseInt(split[6]));
        } else {
            task = new Epic(split[2],
                    split[4],
                    (split[5].equals("null") ? null : (LocalDateTime.parse(split[5], formatter))),
                    Integer.parseInt(split[6]));
        }
        task.setType(Types.valueOf(split[1]));
        task.setStatus(split[3]);
        task.setId(Integer.parseInt(split[0]));
        return task;
    }

    public void fileReader() {
        LinkedList<String> tasksFromFile = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                String lines = reader.readLine();
                tasksFromFile.add(lines);
            }
            if (tasksFromFile.size() != 2) {
                tasksFromFile.removeFirst();
                if (tasksFromFile.size() > 2) {
                    for (int i = 0; i < tasksFromFile.size() - 2; i++) {
                        Task task = fromString(tasksFromFile.get(i));
                        tasksToMap(task);
                    }
                }
                if (!tasksFromFile.getLast().equals("")) {
                    List<Integer> history = historyFromString(tasksFromFile.getLast());//возврат истории просмотров
                    for (Integer historyId : history) {
                        historyBack(historyId);
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка, файла не существует");
        }

    }
    public void save() throws ManagerSaveException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("id,type,name,status,description,epic\n");
            saveTasks(writer, epics);
            saveTasks(writer, tasks);
            saveTasks(writer, subTasks);
            writer.write("\n" + historyToString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка, файла не существует");
        }

    }

    private void saveTasks(BufferedWriter writer, Map<Integer, ? extends Task> taskMap) throws IOException {
        for (Task task : taskMap.values()) {
            writer.write(task.toString() + "\n");
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

    public void tasksToMap(Task task) {
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

    public void historyBack(int id) {
        if (tasks.containsKey(id)) {
            historyManager.add(tasks.get(id));
        } else if (subTasks.containsKey(id)) {
            historyManager.add(subTasks.get(id));
        } else if (epics.containsKey(id)) {
            historyManager.add(epics.get(id));
        }
    }
}
