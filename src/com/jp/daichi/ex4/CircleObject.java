package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class CircleObject extends AObject {

    private final double radius;

    public CircleObject(double x, double y, Vec2d vector,double radius) {
        super(x, y, vector);
        this.radius = radius;
    }

    @Override
    protected Area getArea(double x, double y) {
        Ellipse2D ellipse2D = new Ellipse2D.Double(x-radius,y-radius,radius*2,radius*2);
        return new Area(ellipse2D);
    }

    public double getRadius() {
        return radius;
    }
}
