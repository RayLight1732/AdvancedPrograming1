package com.jp.daichi.ex8painttool.canvasobject;

import java.awt.*;
import java.awt.geom.Path2D;

public class EraserCanvasObject extends SimplePenCanvasObject {
    public EraserCanvasObject(Path2D path2D, Color color, int thickness) {
        super(path2D, color, thickness);
    }

    @Override
    public void draw(Graphics2D g) {
        Composite composite = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR,0f));
        g.setStroke(new BasicStroke(thickness,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        g.draw(path2D);
        g.setComposite(composite);
    }
}
