package com.jp.daichi.ex8painttool;

import com.jp.daichi.ex8painttool.canvasobject.CanvasObject;

import java.util.*;

public class SimpleHistory implements History {
    private List<HistoryStaff> list = new ArrayList<>();
    private int current = -1;
    @Override
    public int add(String name, CanvasObject obj) {
        int index = getIndex(current);
        Optional<Integer> optional= list.stream().map(HistoryStaff::id).max(Comparator.naturalOrder());
        int newId = optional.orElse(-1)+1;
        if (index == -1) {
            list.clear();
        } else {
            list = list.subList(0,index+1);
        }
        list.add(new HistoryStaff(newId,name,obj));
        current = newId;
        return newId;
    }

    @Override
    public boolean to(int id) {
        for (HistoryStaff historyStaff:list) {
            if (historyStaff.id() == id) {
                current = id;
                return true;
            }
        }
        return false;
    }

    @Override
    public int geCurrentHistoryID() {
        return current;
    }

    @Override
    public List<CanvasObject> getObjects() {
        List<CanvasObject> result = new ArrayList<>();
        if (current != -1) {
            for (HistoryStaff historyStaff : list) {
                result.add(historyStaff.canvasObject());
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

    private int getIndex(int id) {
        for (int i = 0;i < list.size();i++) {
            if (list.get(i).id() == id) {
                return i;
            }
        }
        return -1;
    }

}
