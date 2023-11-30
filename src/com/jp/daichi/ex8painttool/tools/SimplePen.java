package com.jp.daichi.ex8painttool.tools;

import com.jp.daichi.ex8painttool.Canvas;
import com.jp.daichi.ex8painttool.CanvasObject;
import com.jp.daichi.ex8painttool.FinishHandler;
import com.jp.daichi.ex8painttool.UpDateHandler;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;

/**
 * シンプルなペン
 */
public class SimplePen implements Tool {
    @Override
    public ToolExecutor createExecutor(Canvas canvas, Point point, UpDateHandler upDateHandler, FinishHandler finishHandler) {
        return new SimplePenExecutor(point,canvas.getColor(),upDateHandler,finishHandler);
    }

    private static class SimplePenExecutor implements ToolExecutor {

        private final Color color;
        private final Path2D path2D;
        private boolean isEnded = false;
        private final UpDateHandler upDateHandler;
        private final FinishHandler finishHandler;
        private final MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                lineTo(e.getPoint());
                isEnded = true;
                finishHandler.onFinish(SimplePenExecutor.this);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!isEnded) {
                    lineTo(e.getPoint());
                    upDateHandler.onUpdate(SimplePenExecutor.this);
                }
            }

            private void lineTo(Point point) {
                path2D.lineTo(point.x,point.y);
            }
        };

        private SimplePenExecutor(Point startPoint, Color color,UpDateHandler upDateHandler, FinishHandler finishHandler) {
            this.color = color;
            this.path2D = new Path2D.Double();
            path2D.moveTo(startPoint.x,startPoint.y);
            this.upDateHandler = upDateHandler;
            this.finishHandler = finishHandler;
        }

        @Override
        public CanvasObject getPreviewCanvasObject() {
            return new SimplePenObject((Path2D) path2D.clone(),color,null);
        }

        @Override
        public CanvasObject getFinalCanvasObject() {
            return new SimplePenObject(path2D,color,null);//TODO implement mouse listener
        }

        @Override
        public MouseAdapter getMouseListener() {
            return mouseAdapter;
        }
    }

    private static class SimplePenObject implements CanvasObject {

        private final Path2D path;
        private Color color;
        private final MouseListener mouseListener;

        private SimplePenObject(Path2D path,Color color,MouseListener mouseListener) {
            this.path = path;
            this.color = color;
            this.mouseListener = mouseListener;
        }

        @Override
        public void draw(Graphics2D g) {
            g.setColor(color);
            g.draw(path);
        }

        @Override
        public MouseListener mouseListener() {
            return mouseListener;
        }

        @Override
        public void setColor(Color color) {
            this.color = color;
        }
    }
}
