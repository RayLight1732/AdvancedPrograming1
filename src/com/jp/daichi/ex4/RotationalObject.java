package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;

/**
 * 回転するオブジェクト
 */
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
    public void collideWith(AObject object,double deltaTime,double hitX,double hitY) {
        super.collideWith(object,deltaTime,hitX,hitY);

    }

    /**
     * 回転角を取得
     * @return 回転角(rad)
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * 回転角を設定
     * @param rotation 回転角(rad)
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    /**
     * 回転速度を取得
     * @return 回転速度(rad)
     */
    public double getRotationSpeed() {
        return rotationSpeed;
    }

    /**
     * 回転速度を設定
     * @param rotationSpeed 回転速度
     */
    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }
}
