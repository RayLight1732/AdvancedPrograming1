package com.jp.daichi.ex8painttool.canvasobject;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.List;


public class SimplePenCanvasObject implements CanvasObject {

    protected final Path2D path2D;
    protected Color color;
    protected final int thickness;
    public SimplePenCanvasObject(Path2D path2D, Color color,int thickness) {
        this.path2D = path2D;
        this.color = color;
        this.thickness = thickness;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(thickness,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        g.draw(path2D);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}