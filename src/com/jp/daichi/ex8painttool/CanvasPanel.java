package com.jp.daichi.ex8painttool;

import com.jp.daichi.ex8painttool.canvasobject.CanvasObject;
import com.jp.daichi.ex8painttool.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CanvasPanel extends JPanel {

    private Canvas canvas;
    private Tool tool = null;
    private ToolExecutor executor;
    private final UpDateHandler upDateHandler = e-> {
        canvas.setPreview(e.getPreviewCanvasObject());
        repaint();
    };
    private final FinishHandler finishHandler = e->{
        if (e.getFinalCanvasObject() != null) {
            canvas.getHistory().add("test", e.getFinalCanvasObject());
        }
        removeMouseAdapter(e.getMouseAdapter());
        executor = null;
        canvas.setPreview(null);
        repaint();
    };
    public CanvasPanel(int width,int height) {
        this.canvas = new SimpleCanvas(width,height,new SimpleHistory());
        canvas.setThickness(10);
        canvas.setColor(new Color(100,0,0,100));
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocus();
                if (tool != null && executor == null) {
                    executor = tool.createExecutor(canvas,e.getPoint(),upDateHandler,finishHandler);
                    addMouseAdapter(executor.getMouseAdapter());
                }
            }
        };
        addMouseAdapter(adapter);
        KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '1') {
                    setTool(new SimplePen());
                } else if (e.getKeyChar()=='2') {
                    setTool(new TemplateShapeTool(ShapeFactories.ELLIPSE.getFactory()));
                } else if (e.getKeyChar()=='3') {
                    setTool(new TemplateShapeTool(ShapeFactories.RECTANGLE.getFactory()));
                } else if (e.getKeyChar()=='4') {
                    setTool(new Picker());
                } else if (e.getKeyChar()=='5') {
                    setTool(new Eraser());
                }
            }
        };
        addKeyListener(keyListener);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (g instanceof Graphics2D g2d) {
             canvas.draw(g2d);
        }
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public Tool getTool() {
        return tool;
    }

    private void addMouseAdapter(MouseAdapter mouseAdapter) {
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
    }

    private void removeMouseAdapter(MouseAdapter mouseAdapter) {
        removeMouseListener(mouseAdapter);
        removeMouseMotionListener(mouseAdapter);
        removeMouseWheelListener(mouseAdapter);
    }
}
