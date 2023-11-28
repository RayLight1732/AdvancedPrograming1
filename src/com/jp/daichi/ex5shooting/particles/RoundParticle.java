package com.jp.daichi.ex5shooting.particles;

import com.jp.daichi.ex5shooting.AGameObject;

import java.awt.*;

public abstract class RoundParticle extends AGameObject implements Particle {

    private double x;
    private double y;
    private double rotation;

    private double radius;
    private double lastTickR;
    private double newR;
    private boolean end;

    @Override
    public void tick(double deltaTime) {
        double lastTickR = getRadius();
        super.tick(deltaTime);
        this.newR = getRadius();
        this.lastTickR = lastTickR;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setX(double x, boolean teleported) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setY(double y, boolean teleported) {
        this.y = y;//TODO
    }

    @Override
    public double getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double getLastTickRadius() {
        return lastTickR;
    }

    public double getNewRadius() {
        return newR;
    }


    @Override
    public boolean isEnd() {
        return end;
    }

    @Override
    public void setEnd(boolean end) {
        this.end = end;
    }

    public abstract Color getColor();
}
