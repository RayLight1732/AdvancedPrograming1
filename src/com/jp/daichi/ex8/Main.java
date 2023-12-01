package com.jp.daichi.ex8;

import com.jp.daichi.ex8.ui.CanvasPanel;
import com.jp.daichi.ex8.ui.MainDisplay;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        SpringLayout layout = new SpringLayout();
        frame.setLayout(layout);
        /*
        CanvasPanel canvasPanel = new CanvasPanel(new SimpleCanvas(600,600,new SimpleHistory()));
        */
        MainDisplay display = new MainDisplay(frame);
        Container contentPane = frame.getContentPane();
        layout.putConstraint(SpringLayout.NORTH,display,0,SpringLayout.NORTH,contentPane);
        layout.putConstraint(SpringLayout.WEST,display,0,SpringLayout.WEST,contentPane);
        layout.putConstraint(SpringLayout.EAST,display,0,SpringLayout.EAST,contentPane);
        layout.putConstraint(SpringLayout.SOUTH,display,0,SpringLayout.SOUTH,contentPane);
        frame.add(display);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
