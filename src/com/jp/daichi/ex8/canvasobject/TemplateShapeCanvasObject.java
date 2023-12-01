package com.jp.daichi.ex8.canvasobject;

import java.awt.*;

import static java.awt.BasicStroke.CAP_SQUARE;
import static java.awt.BasicStroke.JOIN_MITER;

public class TemplateShapeCanvasObject implements MovableCanvasObject {
    private final Shape shape;
    private final int thickness;
    private final boolean isPreview;
    private Color color;
    private boolean isSelected = false;
    public TemplateShapeCanvasObject(Shape shape,Color color,int thickness,boolean isPreview) {
        this.shape = shape;
        this.color = color;
        this.thickness = thickness;
        this.isPreview = isPreview;
    }

    @Override
    public void draw(Graphics2D g) {
        if (isPreview) {
            g.setStroke(new BasicStroke(thickness, CAP_SQUARE, JOIN_MITER, 10.0f, new float[]{(float) thickness*2}, 0.0f));
        } else {
            g.setStroke(new BasicStroke(thickness));
        }
        g.setColor(color);
        g.draw(shape);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public boolean contains(Point point) {
        return shape.contains(point);
    }
}
