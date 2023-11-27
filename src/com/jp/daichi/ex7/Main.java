package com.jp.daichi.ex7;

import com.jp.daichi.ex7.operator.Operators;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static final int maxDisplayScale = 20;
    public static final int maxCalculateScale = 40;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        Display display = new Display();
        frame.add(display);
        frame.setSize(400,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//×を押したらプログラムが終了するように
        frame.setVisible(true);
        display.requestFocus();
        frame.setMinimumSize(frame.getPreferredSize());
    }
}
