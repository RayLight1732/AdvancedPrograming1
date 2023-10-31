package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;

public abstract class RotationalObject extends AColoredObject {

    private double rotationSpeed;
    protected double rotation = 0;
    protected double preRotation = 0;

    public RotationalObject(double x, double y,double rotationSpeed, Vec2d vector) {
        this(x,y,rotationSpeed,vector,null);
    }

    public RotationalObject(double x, double y, double rotationSpeed, Vec2d vector, Color color) {
        super(x, y, vector,color);
        this.rotationSpeed = rotationSpeed;
    }

    @Override
    public void collisionTick(double deltaTime) {
        super.collisionTick(deltaTime);
        preRotation = getRotation()+getRotationSpeed() * deltaTime;//回転
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        setRotation(preRotation);
    }

    @Override
    public void collideWith(AObject object) {
        super.collideWith(object);
        preRotation = getRotation();
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }
}
