package com.jp.daichi.ex8.canvasobject;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;

public class EraserCanvasObject extends SimplePenCanvasObject {
    public EraserCanvasObject(Path2D path2D, int thickness) {
        super(path2D, new Color(0,0,0,0), thickness);
    }

    @Override
    public void draw(Graphics2D g) {
        Composite composite = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR,0f));
        super.draw(g);
        g.setComposite(composite);
    }
}
