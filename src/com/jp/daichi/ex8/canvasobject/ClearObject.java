package com.jp.daichi.ex8.canvasobject;

import java.awt.*;

public class ClearObject implements CanvasObject {
    private static final Color clear = new Color(0,0,0,0);
    @Override
    public void onDeserialized() {

    }

    @Override
    public void draw(Graphics2D g) {
        Composite composite = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR,0f));
        g.setColor(clear);
        g.fillRect(Integer.MIN_VALUE/2,Integer.MIN_VALUE/2,Integer.MAX_VALUE,Integer.MAX_VALUE);
        g.setComposite(composite);
    }

    @Override
    public void setColor(Color color) {

    }
}
