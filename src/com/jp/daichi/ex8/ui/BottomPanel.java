package com.jp.daichi.ex8.ui;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class BottomPanel extends JPanel {
    private final JSlider slider;
    private final ValueConverter converter;

    public BottomPanel(int min,int max,int value,ValueConverter converter) {
        this.converter = converter;
        setBackground(new Color(220,220,220));
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        this.slider = new JSlider(min,max,value);
        slider.setOpaque(false);
        add(slider);
        layout.putConstraint(SpringLayout.NORTH,slider,0,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.WEST,slider,-200,SpringLayout.EAST,slider);
        layout.putConstraint(SpringLayout.EAST,slider,0,SpringLayout.EAST,this);
        layout.putConstraint(SpringLayout.SOUTH,slider,0,SpringLayout.SOUTH,this);

        JLabel percent = new JLabel();
        add(percent);
        percent.setText(getPercentLabelText(slider));
        slider.addChangeListener(e -> percent.setText(getPercentLabelText((JSlider)(e.getSource()))));
        layout.putConstraint(SpringLayout.NORTH,percent,0,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.SOUTH,percent,0,SpringLayout.SOUTH,this);
        layout.putConstraint(SpringLayout.EAST,percent,0,SpringLayout.WEST,slider);
        layout.putConstraint(SpringLayout.WEST,percent,-50,SpringLayout.EAST,percent);
    }

    private String getPercentLabelText(JSlider slider) {
        double converted = converter.convert(slider.getValue());
        return (int)(converted*100)+"%";
    }

    public JSlider getSlider() {
        return slider;
    }

    /**
     * スライダーの値をキャンバスのスケールに変換する
     */
    public interface ValueConverter {
        /**
         * 変換を行う
         * @param value スライダーの値
         * @return キャンバスのスケールの値
         */
        double convert(int value);
    }
}
