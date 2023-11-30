package com.jp.daichi.ex8painttool;

import com.jp.daichi.ex8painttool.tools.Tool;
import com.jp.daichi.ex8painttool.tools.ToolExecutor;

import java.awt.*;
import java.awt.Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Display extends Canvas {

    private com.jp.daichi.ex8painttool.Canvas canvas;
    private Tool tool;
    private ToolExecutor executor;

    public Display() {
        MouseAdapter mouseAdapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (tool != null) {
                    executor = tool.createExecutor(canvas,e.getPoint());
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (executor != null) {
                    executor.getMouseListener().
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }
        };
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public Tool getTool() {
        return tool;
    }
}
