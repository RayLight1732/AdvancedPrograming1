package com.jp.daichi.ex8.ui;

import com.jp.daichi.ex8.Canvas;
import com.jp.daichi.ex8.FinishHandler;
import com.jp.daichi.ex8.KeyManager;
import com.jp.daichi.ex8.UpDateHandler;
import com.jp.daichi.ex8.tools.*;

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
        removeListeners(e);
        executor = null;
        canvas.setPreview(null);
        repaint();
    };

    public CanvasPanel(Canvas canvas) {
        this.canvas = canvas;
        KeyManager manager = new KeyManager();
        addKeyListener(manager);
        canvas.setThickness(10);
        canvas.setColor(new Color(100,0,0,100));
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocus();
                if (tool != null && executor == null) {
                    executor = tool.createExecutor(canvas,e.getPoint(),manager,upDateHandler,finishHandler);
                    addListeners(executor);
                }
            }
        };
        addMouseListener(adapter);
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

    /**
     * ツールを設定
     * @param tool 新しいツール
     */
    public void setTool(Tool tool) {
        this.tool = tool;
        if (executor != null) {
            finishHandler.onFinish(executor);
        }
    }

    /**
     * 現在設定されているツールを取得
     * @return 現在設定されているツール
     */
    public Tool getTool() {
        return tool;
    }

    /**
     * キャンバスを取得
     * @return キャンバス
     */
    public Canvas getCanvas() {
        return canvas;
    }

    private void addListeners(ToolExecutor executor) {
        MouseAdapter mouseAdapter = executor.getMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
    }

    private void removeListeners(ToolExecutor executor) {
        MouseAdapter mouseAdapter = executor.getMouseAdapter();
        removeMouseListener(mouseAdapter);
        removeMouseMotionListener(mouseAdapter);
        removeMouseWheelListener(mouseAdapter);
    }
}
