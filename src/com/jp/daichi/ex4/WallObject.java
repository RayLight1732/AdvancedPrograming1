package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class WallObject extends AObject {

    private final double width;
    private final double height;

    public WallObject(double x, double y, double width, double height) {
        super(x,y,new Vec2d(0,0));
        this.width = width;
        this.height = height;
    }

    public WallObject(double x, double y, double width, double height,double restitutionCoefficient) {
        super(x,y,new Vec2d(0,0),restitutionCoefficient);
        this.width = width;
        this.height = height;
    }

    @Override
    public void setVector(Vec2d vector) {

    }

    @Override
    public void setVecX(double vecX) {

    }

    @Override
    public void setVecY(double vecY) {
    }

    @Override
    public void collideWith(AObject object, double deltaTime,double hitX,double hitY,boolean inner) {

    }

    @Override
    protected Area getArea(double x, double y) {
        return new Area(new Rectangle2D.Double(x,y,width,height));
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

}
