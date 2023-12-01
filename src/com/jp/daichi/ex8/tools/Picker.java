package com.jp.daichi.ex8.tools;

import com.jp.daichi.ex8.Canvas;
import com.jp.daichi.ex8.FinishHandler;
import com.jp.daichi.ex8.KeyManager;
import com.jp.daichi.ex8.UpDateHandler;
import com.jp.daichi.ex8.canvasobject.CanvasObject;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Picker implements Tool {
    @Override
    public ToolExecutor createExecutor(Canvas canvas, Point point, KeyManager manager, UpDateHandler upDateHandler, FinishHandler finishHandler) {
        return new PickerExecutor(canvas,finishHandler);
    }

    @Override
    public String getName() {
        return "カラーピッカー";
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
