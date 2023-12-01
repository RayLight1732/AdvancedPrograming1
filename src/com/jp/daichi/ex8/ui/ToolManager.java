package com.jp.daichi.ex8.ui;

import com.jp.daichi.ex8.tools.Tool;

import java.util.ArrayList;
import java.util.List;

public class ToolManager {
    private final List<ChangeListener> listeners = new ArrayList<>();
    private Tool tool;

    public void setTool(Tool tool) {
        this.tool = tool;
        for (ChangeListener listener:listeners) {
            listener.onChange(tool);
        }
    }

    public Tool getTool() {
        return tool;
    }

    public void addListener(ChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    public interface ChangeListener {
        void onChange(Tool newTool);
    }
}
