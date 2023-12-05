package com.jp.daichi.ex8.tools;

import com.jp.daichi.ex8.Canvas;
import com.jp.daichi.ex8.KeyManager;
import com.jp.daichi.ex8.canvasobject.CanvasObject;
import com.jp.daichi.ex8.FinishHandler;
import com.jp.daichi.ex8.UpDateHandler;
import com.jp.daichi.ex8.canvasobject.TemplateShapeCanvasObject;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * あらかじめ設定された形を描画するツール
 */
public class TemplateShapeTool implements Tool {

    private final ShapeFactory factory;
    private final boolean fill;
    private final String name;

    /**
     * factoryの形を描画する新しいツールのインスタンスを作成
     *
     * @param factory 描画する形
     */
    public TemplateShapeTool(ShapeFactory factory,boolean fill,String name) {
        this.factory = factory;
        this.fill = fill;
        this.name = name;
    }

    @Override
    public ToolExecutor createExecutor(Canvas canvas, Point point, KeyManager manager, UpDateHandler upDateHandler, FinishHandler finishHandler) {
        return new TemplateShapeToolExecutor(factory, point,canvas.getColor(),canvas.getThickNess(),upDateHandler,finishHandler,fill);
    }

    @Override
    public String getName() {
        return name;
    }

    public static class TemplateShapeToolExecutor implements ToolExecutor {
        private boolean isEnded = false;
        private final Point startPoint;
        private final ShapeFactory factory;
        private final Color color;
        private final int thickness;
        private final UpDateHandler upDateHandler;
        private final FinishHandler finishHandler;
        private Shape shape;
        private boolean fill;
        private final MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                isEnded = true;
                shape = factory.create(startPoint.x, startPoint.y, e.getX(), e.getY());
                finishHandler.onFinish(TemplateShapeToolExecutor.this);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!isEnded) {
                    shape = factory.create(startPoint.x, startPoint.y, e.getX(), e.getY());
                    upDateHandler.onUpdate(TemplateShapeToolExecutor.this);
                }
            }
        };

        /**
         * 処理を実行するオブジェクト
         * @param factory Shapeを作成するファクトリー
         * @param point   始点
         * @param color 色
         */
        public TemplateShapeToolExecutor(ShapeFactory factory, Point point,Color color,int thickness,UpDateHandler upDateHandler,FinishHandler finishHandler,boolean fill) {
            this.factory = factory;
            this.startPoint = point;
            this.color = color;
            this.thickness = thickness;
            this.upDateHandler = upDateHandler;
            this.finishHandler = finishHandler;
            this.fill = fill;
        }

        @Override
        public CanvasObject getPreviewCanvasObject() {
            return new TemplateShapeCanvasObject(shape,color,thickness,true,fill);
        }

        @Override
        public CanvasObject getFinalCanvasObject() {
            return new TemplateShapeCanvasObject(shape,color,thickness,false,fill);
        }

        @Override
        public MouseAdapter getMouseAdapter() {
            return mouseAdapter;
        }

    }

}

