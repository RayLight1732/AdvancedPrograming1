package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

public class ResizableRPolygonObject extends RPolygonObject {

    private double minRadius;
    private double maxRadius;
    private double radius = getRadius();

    public ResizableRPolygonObject(double x, double y, double radius,double minRadius,double maxRadius, int nPoints,double rotation,double rotationSpeed, Vec2d vector) {
        this(x, y, radius,minRadius,maxRadius,nPoints,rotation,rotationSpeed,vector,null);
    }

    public ResizableRPolygonObject(double x, double y, double radius,double minRadius,double maxRadius, int nPoints, double rotation, double rotationSpeed, Vec2d vector, Color color) {
        super(x, y, radius, nPoints, rotation, rotationSpeed, vector, color);
        if (minRadius > maxRadius) {
            throw new IllegalArgumentException("maxRadius greater than minRadius");
        }
        this.maxRadius = maxRadius;
        this.minRadius = minRadius;
    }

    @Override
    protected Area getArea(double x, double y) {
        AffineTransform transform = AffineTransform.getTranslateInstance(x,y);//移動して
        transform.rotate(getRotation());//回転
        path = createRPolygon(getNPoints(),getRadius());
        path.transform(transform);
        return new Area(path);
    }

    private int direction = 1;

    @Override
    public void tick(double deltaTime) {
        radius += deltaTime*5*direction;
        if (radius > maxRadius) {
            direction = -1;
            radius = maxRadius;
        } else if (radius < minRadius) {
            direction = 1;
            radius = minRadius;
        }
        super.tick(deltaTime);
    }

    @Override
    public double getRadius() {
        return radius;
    }
}
