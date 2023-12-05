package com.jp.daichi.ex8;

import com.jp.daichi.ex8.ui.MainDisplay;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MainFrame extends JFrame {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setSize(600,600);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private MainFrame() {
        init(new SimpleCanvas(600,600,new SimpleHistory()));
    }

    private void clear() {
        getContentPane().removeAll();
    }

    private void init(Canvas canvas) {
        SpringLayout layout = new SpringLayout();
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);
        MainDisplay display = new MainDisplay(this,canvas);

        layout.putConstraint(SpringLayout.NORTH,display,0,SpringLayout.NORTH,contentPane);
        layout.putConstraint(SpringLayout.WEST,display,0,SpringLayout.WEST,contentPane);
        layout.putConstraint(SpringLayout.EAST,display,0,SpringLayout.EAST,contentPane);
        layout.putConstraint(SpringLayout.SOUTH,display,0,SpringLayout.SOUTH,contentPane);
        getContentPane().add(display);
    }

    /**
     * ファイルから履歴を復元して開く
     * @param file ファイル
     */
    public void setCanvas(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        Canvas canvas = (Canvas) inputStream.readObject();
        inputStream.close();
        canvas.onDeserialized();
        clear();
        init(canvas);
        setVisible(false);
        setVisible(true);
    }
}

