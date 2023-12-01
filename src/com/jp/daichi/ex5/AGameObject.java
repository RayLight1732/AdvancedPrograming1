package com.jp.daichi.ex5;

public abstract class AGameObject implements GameObject {
    protected boolean isVisible = true;
    protected double lastTickX;
    protected double lastTickY;
    protected double lastTickRotation;
    protected boolean lastTickExisted = false;
    protected double newX;
    protected double newY;
    protected double newRotation;

    private long lastTickTime;
    private double lastTickDelta;

    @Override
    public void tick(double deltaTime) {
        double lastTickX = getX();
        double lastTickY = getY();
        double lastTickRotation = getRotation();
        doTick(deltaTime);
        this.lastTickX = lastTickX;
        this.lastTickY = lastTickY;
        this.lastTickRotation = lastTickRotation;
        newX = getX();
        newY = getY();
        newRotation = getRotation();
        this.lastTickTime = System.currentTimeMillis();
        this.lastTickDelta = deltaTime;
        lastTickExisted = true;
    }

    protected abstract void doTick(double deltaTime);

    @Override
    public double getLastTickX() {
        return lastTickX;
    }

    @Override
    public double getNewX() {
        return newX;
    }

    @Override
    public double getNewY() {
        return newY;
    }

    @Override
    public double getLastTickY() {
        return lastTickY;
    }

    @Override
    public double getNewRotation() {
        return newRotation;
    }

    @Override
    public double getLastTickRotation() {
        return lastTickRotation;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public boolean lastTickExisted() {
        return lastTickExisted;
    }

    @Override
    public long lastTickMs() {
        return lastTickTime;
    }

    @Override
    public double lastTickDelta() {
        return lastTickDelta;
    }

}
