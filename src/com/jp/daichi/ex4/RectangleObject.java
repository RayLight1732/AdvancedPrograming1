package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * 矩形を表すオブジェクト
 */
public class RectangleObject extends AObject {
    public final double width;
    public final double height;
    private Rectangle2D rectangle;

    public RectangleObject(double x, double y,double width,double height, Vec2d vector) {
        super(x, y, vector);
        this.width = width;
        this.height = height;
    }

    @Override
    protected Area getArea(double x, double y) {
        rectangle = new Rectangle2D.Double(x,y,width,height);
        return new Area(rectangle);
    }
}
