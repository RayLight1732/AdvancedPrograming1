package com.jp.daichi.ex8;

import com.jp.daichi.ex8.canvasobject.CanvasObject;
import com.jp.daichi.ex8.canvasobject.ClearObject;

import java.io.Serial;
import java.util.*;

public class SimpleHistory implements History {
    @Serial
    private static final long serialVersionUID = 3763579663798004361L;
    private ArrayList<HistoryStaff> list = new ArrayList<>();
    private int current = -1;
    @Override
    public int add(String name, CanvasObject obj) {
        int index = getIndex(current);
        Optional<Integer> optional= list.stream().map(HistoryStaff::id).max(Comparator.naturalOrder());
        int newId = optional.orElse(-1)+1;
        if (index == -1) {
            list.clear();
        } else {
            list = new ArrayList<>(list.subList(0,index+1));
        }
        list.add(new HistoryStaff(newId,name,obj));
        current = newId;
        return newId;
    }

    @Override
    public boolean to(int id) {
        if (id == -1) {
            current = -1;
            return true;
        } else {
            if (getIndex(id)!= -1) {//idが履歴中に存在
                current = id;
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public int getCurrentHistoryID() {
        return current;
    }

    @Override
    public List<HistoryStaff> painted() {
        List<HistoryStaff> result = new ArrayList<>();
        if (current != -1) {
            for (HistoryStaff historyStaff : list) {
                result.add(historyStaff);
                if (historyStaff.id() == current) {
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public List<HistoryStaff> getAll() {
        return new ArrayList<>(list);
    }

    @Override
    public int getIndex(int id) {
        for (int i = 0;i < list.size();i++) {
            if (list.get(i).id() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void clear() {
        if (!(list.get(current).canvasObject() instanceof ClearObject)) {
            add("clear", new ClearObject());
        }
    }

    @Override
    public void onDeserialized() {

    }
}
