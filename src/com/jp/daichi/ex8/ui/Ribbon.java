package com.jp.daichi.ex8.ui;

import com.jp.daichi.ex8.tools.Tool;
import com.jp.daichi.ex8.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/*
ペン 消しゴム カラーピッカー　消しゴム      色選択
 */
public class Ribbon extends JPanel {
    private final ToolManager manager;
    public Ribbon(ToolManager manager) {
        this.manager = manager;
        setBackground(Color.GRAY);
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        manager.addListener(newTool -> repaint());
        add(createButton(Tools.PEN));
        add(createButton(Tools.ERASER));
        add(createButton(Tools.RECTANGLE));
        add(createButton(Tools.ELLIPSE));
    }

    private JButton createButton(Tool tool) {
        JButton button = new JButton(tool.getName());
        button.addActionListener(e -> manager.setTool(tool));
        return button;
    }
}
