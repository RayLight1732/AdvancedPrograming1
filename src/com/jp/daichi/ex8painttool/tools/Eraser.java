package com.jp.daichi.ex8painttool.tools;

import com.jp.daichi.ex8painttool.Canvas;
import com.jp.daichi.ex8painttool.FinishHandler;
import com.jp.daichi.ex8painttool.UpDateHandler;
import com.jp.daichi.ex8painttool.canvasobject.CanvasObject;
import com.jp.daichi.ex8painttool.canvasobject.EraserCanvasObject;

import java.awt.*;

public class Eraser implements Tool {
    @Override
    public ToolExecutor createExecutor(Canvas canvas, Point point, UpDateHandler upDateHandler, FinishHandler finishHandler) {
        return new EraserExecutor(point,canvas.getThickNess(),upDateHandler,finishHandler);
    }

    public static class EraserExecutor extends SimplePen.SimplePenExecutor {

        public EraserExecutor(Point startPoint, int thickness, UpDateHandler upDateHandler, FinishHandler finishHandler) {
            super(startPoint, new Color(255,255,255,0),thickness, upDateHandler, finishHandler);
        }

        @Override
        protected CanvasObject createCanvasObject() {
            return new EraserCanvasObject(path2D,new Color(0,0,0,0),thickness);
        }
    }
}
