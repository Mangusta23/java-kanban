package Manager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import Model.*;
public class InMemoryHistoryManager implements HistoryManager {

    Node<Task> head = null;
    Node<Task> tail = null;
    Map<Integer, Node<Task>> historyMap = new HashMap<>();

    @Override
    public void add(Task task){
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, task, null);
        tail = newNode;
        if (oldTail == null){
            head = newNode;
        }else{
            oldTail.next = newNode;
        }
        if (historyMap.containsKey(task.getId())){
            remove(task.getId());
            historyMap.put(task.getId(), newNode);
        }else{
            historyMap.put(task.getId(), newNode);
        }
    }
    @Override
    public void remove(int taskId){
        if (historyMap.containsKey(taskId)){
            Node<Task> tempNode = historyMap.get(taskId);
            if(tempNode.prev != null) {
                tempNode.prev.next = tempNode.next;
            }else{
                tempNode.next.prev = null;
                head = tempNode.next;
            }
            if (tempNode.next != null) {
                tempNode.next.prev = tempNode.prev;
            }else{
                tempNode.prev.next = null;
                tail = tempNode.prev;
            }
            historyMap.remove(taskId);
        }
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> history = new ArrayList<>();
        Node<Task> tempNode = head;
        while(tempNode != null){
            history.add(tempNode.data);
            tempNode = tempNode.next;
        }
        return history;
    }
}
