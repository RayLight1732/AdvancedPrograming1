package com.jp.daichi.ex8.ui;

import com.jp.daichi.ex8.Canvas;
import com.jp.daichi.ex8.ColorChangeListener;
import com.jp.daichi.ex8.HistoryStaff;
import com.jp.daichi.ex8.tools.Tool;
import com.jp.daichi.ex8.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.text.NumberFormat;
import java.util.List;

/*
ペン 消しゴム カラーピッカー　消しゴム      色選択
 */
public class Ribbon extends JPanel implements ColorChangeListener {
    private final ToolManager manager;
    private final ColorPicker picker;
    public Ribbon(MainDisplay display,Canvas canvas, ToolManager manager, JColorChooser colorChooser, ActionListener okListener) {
        this.manager = manager;
        setBackground(Color.GRAY);
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        manager.addListener(newTool -> repaint());
        add(createButton(Tools.PEN));
        add(createButton(Tools.ERASER));
        add(createButton(Tools.RECTANGLE));
        add(createButton(Tools.ELLIPSE));
        add(createButton(Tools.FILLED_RECTANGLE));
        add(createButton(Tools.FILLED_ELLIPSE));
        add(createButton(Tools.PICKER));
        JButton clearButton = new JButton("クリア");
        clearButton.addActionListener(e->{
            canvas.getHistory().clear();
            display.repaint();
        });
        NumberFormat format = NumberFormat.getIntegerInstance();
        JFormattedTextField textField = new JFormattedTextField(format);
        textField.setText(Integer.toString(canvas.getThickNess()));
        textField.setBorder(null);
        textField.setMaximumSize(new Dimension(10000,20));
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                    Ribbon.this.requestFocus();
                }
            }
        });
        textField.addFocusListener(new FocusListener() {
            @Override public void focusGained(FocusEvent e){}
            @Override
            public void focusLost(FocusEvent e) {
                try {
                canvas.setThickness(Integer.parseInt(textField.getText()));
                } catch (NumberFormatException ignore){}
            }
        });
        add(textField);
        add(clearButton);
        add(Box.createGlue());

        JDialog dialog = JColorChooser.createDialog(this,"color picker",true,colorChooser,okListener,null);

        this.picker = new ColorPicker(30);
        add(picker);
        picker.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialog.setVisible(true);
            }
        });
        add(Box.createHorizontalStrut(20));
    }

    private JButton createButton(Tool tool) {
        JButton button = new JButton(tool.getName());
        button.addActionListener(e -> manager.setTool(tool));
        return button;
    }
    @Override
    public void onChange(Color oldColor, Color newColor, boolean isBackGround) {
        if (!isBackGround) {
            picker.setForeground(newColor);
        }
    }

    private static class ColorPicker extends JButton {

        private final int size;
        private ColorPicker(int size) {
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorder(null);
            this.size = size;
        }
        private Shape getShape() {
            return new Ellipse2D.Double(0,0,getWidth()-1,getHeight()-1);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (g instanceof Graphics2D g2d) {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getForeground());
                Shape shape = getShape();
                g2d.fill(shape);
                if (isRolloverEnabled() && getModel().isRollover()) {
                    g2d.setColor(Color.BLACK);
                    g2d.draw(shape);
                }
            }
        }

        @Override
        public boolean contains(int x, int y) {
            return getShape().contains(x,y);
        }

        @Override
        public Dimension getMaximumSize() {
            return new Dimension(size,size);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(size,size);
        }
    }
}
