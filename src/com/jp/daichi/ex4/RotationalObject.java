package com.jp.daichi.ex4;

import java.awt.*;
import java.awt.geom.Area;

/**
 * 回転するオブジェクト
 */
public abstract class RotationalObject extends AColoredObject {

    private double rotationSpeed;
    protected double rotation;
    protected double preRotation = 0;

    private double previousRotation;
    private double nextRotation;
    public RotationalObject(double x, double y,double rotation,double rotationSpeed, Vec2d vector) {
        this(x,y,rotation,rotationSpeed,vector,null);
    }

    public RotationalObject(double x, double y, double rotation,double rotationSpeed, Vec2d vector, Color color) {
        super(x, y, vector,color);
        this.rotation = rotation;
        this.rotationSpeed = rotationSpeed;
        this.nextRotation = rotation;
        this.previousRotation = rotation;
    }

    @Override
    public void collisionTick(double deltaTime) {
        super.collisionTick(deltaTime);
        preRotation = getRotation()+getRotationSpeed() * deltaTime;//回転
    }

    @Override
    public void tick(double deltaTime) {
        if (teleported) {
            previousRotation = getRotation();
        } else {
            previousRotation = nextRotation;
        }
        super.tick(deltaTime);
        setRotation(preRotation);
        nextRotation = getRotation();
    }

    @Override
    public void collideWith(AObject object,double deltaTime,double hitX,double hitY,boolean inner) {
        super.collideWith(object,deltaTime,hitX,hitY,inner);
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

    @Override
    protected void draw(Graphics2D g,double x,double y,double step) {
        this.draw(g,x,y,previousRotation+(nextRotation-previousRotation)*step,step);
    }

    protected void draw(Graphics2D g,double x,double y,double rotation,double step) {
        //System.out.println(rotation);
        g.setColor(getColor());//描画色変更
        Area area1 = getArea(x,y,rotation);
        g.fill(area1);
        if (getOutLineColor() != null) {
            g.setColor(getOutLineColor());
            g.draw(area1);
        }
    }


    @Override
    public Area getArea(double x, double y) {
        return getArea(x,y,getRotation());
    }

    public abstract Area getArea(double x, double y, double rotation);
}
