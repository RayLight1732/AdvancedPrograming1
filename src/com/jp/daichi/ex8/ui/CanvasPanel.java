package com.jp.daichi.ex8.ui;

import com.jp.daichi.ex8.Canvas;
import com.jp.daichi.ex8.FinishHandler;
import com.jp.daichi.ex8.KeyManager;
import com.jp.daichi.ex8.UpDateHandler;
import com.jp.daichi.ex8.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class CanvasPanel extends JPanel {

    private Canvas canvas;
    private Tool tool = null;
    private ToolExecutor executor;
    private final List<MouseAdapter> mouseAdapters = new ArrayList<>();
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
        setBackground(Color.BLACK);
        this.canvas = canvas;
        KeyManager manager = new KeyManager();
        addKeyListener(manager);
        canvas.setThickness(10);
        canvas.setColor(new Color(100,0,0,100));
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocus();
                bypassMouseEvent(e,MouseAdapter::mousePressed);
                if (tool != null && executor == null) {
                    Point converted = new Point(convertX(e.getX()),convertY(e.getY()));
                    executor = tool.createExecutor(canvas,converted,manager,upDateHandler,finishHandler);
                    addListeners(executor);
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                bypassMouseEvent(e,(MouseAdapter::mouseClicked));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                bypassMouseEvent(e,(MouseAdapter::mouseReleased));
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                bypassMouseEvent(e,(MouseAdapter::mouseEntered));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                bypassMouseEvent(e,(MouseAdapter::mouseExited));
            }
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                MouseWheelEvent converted = convertMouseWheelEvent(e);
                for (MouseAdapter a:new ArrayList<>(mouseAdapters)) {
                    a.mouseWheelMoved(converted);
                }
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                bypassMouseEvent(e,(MouseAdapter::mouseDragged));
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                bypassMouseEvent(e,(MouseAdapter::mouseMoved));
            }
        };


        addMouseListener(adapter);
        addMouseMotionListener(adapter);
        addMouseWheelListener(adapter);

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

    private MouseEvent convertMouseEvent(MouseEvent e) {
        return new MouseEvent(e.getComponent(),e.getID(),e.getWhen(),e.getModifiersEx(),convertX(e.getX()),convertY(e.getY()),e.getClickCount(),e.isPopupTrigger(),e.getButton());
    }

    private MouseWheelEvent convertMouseWheelEvent(MouseWheelEvent e) {
        return new MouseWheelEvent(e.getComponent(),e.getID(),e.getWhen(),e.getModifiersEx(),convertX(e.getX()),convertY(e.getY()),e.getClickCount(),e.isPopupTrigger(),e.getScrollType(),e.getScrollAmount(),e.getWheelRotation());
    }

    /**
     * パネル上のx座標をキャンバス上のx座標に変換
     * @param x パネル上のx座標
     * @return キャンバス上のx座標
     */
    public int convertX(int x) {
        return (int)(x/scale);
    }

    /**
     * パネル上のy座標をキャンバス上のy座標に変換
     * @param y パネル上のy座標
     * @return キャンバス上のy座標
     */
    public int convertY(int y) {
        return (int)(y/scale);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g instanceof Graphics2D g2d) {
            AffineTransform transform = g2d.getTransform();
            transform.scale(scale,scale);
            g2d.setTransform(transform);
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
        mouseAdapters.add(executor.getMouseAdapter());
    }

    private void removeListeners(ToolExecutor executor) {
        mouseAdapters.remove(executor.getMouseAdapter());
    }

    private double scale = 1;

    public void setScale(double scale) {
        if (scale <= 0) {
            throw new IllegalArgumentException("scale must be grater than 0");
        } else {
            this.scale = scale;
        }
    }

    public double getScale() {
        return scale;
    }

    public int getScaledCanvasWidth() {
        return (int) (canvas.getWidth()*scale);
    }

    public int getScaledCanvasHeight() {
        return (int)(canvas.getHeight()*scale);
    }
    
    private void bypassMouseEvent(MouseEvent e, MouseEventExecutor proxy) {
        MouseEvent converted = convertMouseEvent(e);
        for (MouseAdapter a:new ArrayList<>(mouseAdapters)) {
            proxy.run(a,converted);
        }
    }
    
    private interface MouseEventExecutor {
        void run(MouseAdapter a,MouseEvent e);
    }
}
