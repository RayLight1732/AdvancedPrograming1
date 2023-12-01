package com.jp.daichi.ex8painttool.tools;

import com.jp.daichi.ex8painttool.Canvas;
import com.jp.daichi.ex8painttool.FinishHandler;
import com.jp.daichi.ex8painttool.UpDateHandler;
import com.jp.daichi.ex8painttool.canvasobject.CanvasObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Picker implements Tool {
    @Override
    public ToolExecutor createExecutor(Canvas canvas, Point point, UpDateHandler upDateHandler, FinishHandler finishHandler) {
        return new PickerExecutor(canvas,finishHandler);
    }

    private static class PickerExecutor implements ToolExecutor {

        private final Canvas canvas;
        private final FinishHandler finishHandler;
        private final MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                canvas.setColor(new Color(canvas.getBufferedImage().getRGB(e.getX(),e.getY()),true));
                finishHandler.onFinish(PickerExecutor.this);
            }
        };

        private PickerExecutor(Canvas canvas,FinishHandler finishHandler) {
            this.canvas = canvas;
            this.finishHandler = finishHandler;
        }
        @Override
        public CanvasObject getPreviewCanvasObject() {
            return null;
        }

        @Override
        public CanvasObject getFinalCanvasObject() {
            return null;
        }

        @Override
        public MouseAdapter getMouseAdapter() {
            return mouseAdapter;
        }
    }
 }
