package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

public class ResizableRPolygonObject extends RPolygonObject {

    private double radius = getRadius();

    public ResizableRPolygonObject(double x, double y, double radius, int nPoints,double rotation,double rotationSpeed, Vec2d vector) {
        this(x, y, radius,nPoints,rotation,rotationSpeed,vector,null);
    }

    public ResizableRPolygonObject(double x, double y, double radius, int nPoints, double rotation, double rotationSpeed, Vec2d vector, Color color) {
        super(x, y, radius, nPoints, rotation, rotationSpeed, vector, color);
    }

    @Override
    protected Area getArea(double x, double y) {
        AffineTransform transform = AffineTransform.getTranslateInstance(x,y);//移動して
        transform.rotate(getRotation());//回転
        path = createRPolygon(getNPoints(),getRadius(),getRotation());
        path.transform(transform);
        return new Area(path);
    }

    private int direction = 1;

    @Override
    public void tick(double deltaTime) {
        radius += deltaTime*5*direction;
        if (radius > super.getRadius()*10) {
            direction = -1;
            radius = super.getRadius()*10;
        } else if (radius < super.getRadius()/2) {
            direction = 1;
            radius = super.getRadius()/2;
        }
        super.tick(deltaTime);
    }

    @Override
    public double getRadius() {
        return radius;
    }
}
