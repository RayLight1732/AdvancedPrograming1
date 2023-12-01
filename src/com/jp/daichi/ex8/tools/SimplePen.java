package com.jp.daichi.ex8.tools;

import com.jp.daichi.ex8.Canvas;
import com.jp.daichi.ex8.KeyManager;
import com.jp.daichi.ex8.canvasobject.CanvasObject;
import com.jp.daichi.ex8.FinishHandler;
import com.jp.daichi.ex8.UpDateHandler;
import com.jp.daichi.ex8.canvasobject.SimplePenCanvasObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

/**
 * シンプルなペン
 */
public class SimplePen implements Tool {
    @Override
    public ToolExecutor createExecutor(Canvas canvas, Point point, KeyManager manager, UpDateHandler upDateHandler, FinishHandler finishHandler) {
        return new SimplePenExecutor(point, canvas.getColor(), canvas.getThickNess(),getMode(manager), upDateHandler, finishHandler);
    }

    @Override
    public String getName() {
        return "ペン";
    }

    protected Mode getMode(KeyManager manager) {
        if (manager.isKeyPressed(KeyEvent.VK_SHIFT)) {
            return Mode.LINE;
        } else if (manager.isKeyPressed(KeyEvent.VK_CONTROL)) {
            return Mode.PARALLEL;
        } else {
            return Mode.FREE_HAND;
        }
    }

    public enum Mode {
        FREE_HAND,PARALLEL,LINE
    }

    public static class SimplePenExecutor implements ToolExecutor {

        private final Point startPoint;
        private final Color color;
        protected Path2D path2D = new Path2D.Double();
        protected final int thickness;
        private boolean isEnded = false;
        private final UpDateHandler upDateHandler;
        private final FinishHandler finishHandler;
        private final Mode mode;
        private final MouseAdapter mouseAdapter = new MouseAdapter() {
            private int direction = 0;
            @Override
            public void mouseReleased(MouseEvent e) {
                //points.add(e.getPoint());
                addPoint(e.getPoint());
                isEnded = true;
                finishHandler.onFinish(SimplePenExecutor.this);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!isEnded) {
                    //points.add(e.getPoint());
                    addPoint(e.getPoint());
                    upDateHandler.onUpdate(SimplePenExecutor.this);
                }
            }

            private void addPoint(Point point) {
                switch (mode) {
                    case LINE -> {
                        path2D.reset();
                        path2D.moveTo(startPoint.x,startPoint.y);
                        path2D.lineTo(point.x,point.y);
                    }
                    case PARALLEL -> {
                        if (direction==0) {//x軸かy軸か定まっていない時
                            direction = Math.abs(point.x-startPoint.x)-Math.abs(point.y-startPoint.y);
                        } else if (direction>0) {//x軸
                            path2D.reset();
                            path2D.moveTo(startPoint.x,startPoint.y);
                            path2D.lineTo(point.x,startPoint.y);
                        } else {//y軸
                            path2D.reset();
                            path2D.moveTo(startPoint.x,startPoint.y);
                            path2D.lineTo(startPoint.x,point.y);
                        }
                    }
                    case FREE_HAND -> {
                        path2D.lineTo(point.x,point.y);
                    }
                }
            }

        };

        public SimplePenExecutor(Point startPoint, Color color,int thickness,Mode mode,UpDateHandler upDateHandler, FinishHandler finishHandler) {
            path2D.moveTo(startPoint.x,startPoint.y);
            this.startPoint = startPoint;
            this.color = color;
            this.thickness = thickness;
            this.mode = mode;
            this.upDateHandler = upDateHandler;
            this.finishHandler = finishHandler;
        }

        @Override
        public CanvasObject getPreviewCanvasObject() {
            return createCanvasObject();
        }

        @Override
        public CanvasObject getFinalCanvasObject() {
            return createCanvasObject();
        }

        @Override
        public MouseAdapter getMouseAdapter() {
            return mouseAdapter;
        }

        protected CanvasObject createCanvasObject() {
            return new SimplePenCanvasObject(path2D,color,thickness);
        }
    }
}
