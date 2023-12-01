package com.jp.daichi.ex8painttool.tools;

import com.jp.daichi.ex8painttool.Canvas;
import com.jp.daichi.ex8painttool.canvasobject.CanvasObject;
import com.jp.daichi.ex8painttool.FinishHandler;
import com.jp.daichi.ex8painttool.UpDateHandler;
import com.jp.daichi.ex8painttool.canvasobject.SimplePenCanvasObject;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

/**
 * シンプルなペン
 */
public class SimplePen implements Tool {
    @Override
    public ToolExecutor createExecutor(Canvas canvas, Point point, UpDateHandler upDateHandler, FinishHandler finishHandler) {
        return new SimplePenExecutor(point,canvas.getColor(),canvas.getThickNess(),upDateHandler,finishHandler);
    }

    public static class SimplePenExecutor implements ToolExecutor {

        private final Color color;
        //private final List<Point> points = new ArrayList<>();
        protected final Path2D path2D;
        protected final int thickness;
        private boolean isEnded = false;
        private final UpDateHandler upDateHandler;
        private final FinishHandler finishHandler;
        private final MouseAdapter mouseAdapter = new MouseAdapter() {
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
                path2D.lineTo(point.x,point.y);
            }

        };

        public SimplePenExecutor(Point startPoint, Color color,int thickness,UpDateHandler upDateHandler, FinishHandler finishHandler) {
            this.color = color;
            this.thickness = thickness;
            this.path2D = new Path2D.Double();
            path2D.moveTo(startPoint.x,startPoint.y);
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
            return new SimplePenCanvasObject((Path2D) path2D.clone(),color,thickness);
        }
    }
}
