package com.jp.daichi.ex8.tools;

import com.jp.daichi.ex8.Canvas;
import com.jp.daichi.ex8.FinishHandler;
import com.jp.daichi.ex8.KeyManager;
import com.jp.daichi.ex8.UpDateHandler;
import com.jp.daichi.ex8.canvasobject.CanvasObject;
import com.jp.daichi.ex8.canvasobject.EraserCanvasObject;

import java.awt.*;

public class Eraser extends SimplePen {
    @Override
    public ToolExecutor createExecutor(Canvas canvas, Point point, KeyManager manager, UpDateHandler upDateHandler, FinishHandler finishHandler) {
        return new EraserExecutor(point,new Color(0,0,0,0),canvas.getThickNess(),getMode(manager),upDateHandler,finishHandler);
    }

    @Override
    public String getName() {
        return "消しゴム";
    }

    public static class EraserExecutor extends SimplePenExecutor {

        public EraserExecutor(Point startPoint, Color color, int thickness, Mode mode, UpDateHandler upDateHandler, FinishHandler finishHandler) {
            super(startPoint, color, thickness, mode, upDateHandler, finishHandler);
        }

        @Override
        protected CanvasObject createCanvasObject() {
            return new EraserCanvasObject(path2D,thickness);
        }
    }
}
