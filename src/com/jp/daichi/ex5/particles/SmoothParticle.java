package com.jp.daichi.ex5.particles;

import java.awt.*;

public abstract class SmoothParticle implements Particle {

    private double lastTickDelta = -1;
    private double lastTickTime;
    private double previousX;
    private double previousY;
    private double nextX;
    private double nextY;
    private double x;
    private double y;

    @Override
    public void tick(double deltaTime) {
        if (lastTickDelta == -1) {
            previousX = getX();
            previousY = getY();
        } else {
            previousX = nextX;
            previousY = nextY;
        }
        lastTickDelta = deltaTime;
        lastTickTime = System.currentTimeMillis()/1000.0;
        nextX = getX();
        nextY = getY();
    }

    @Override
    public final void draw(Graphics2D g) {
        if (lastTickDelta == -1) {
            return;
        }
        double deltaTime = System.currentTimeMillis()/1000.0-lastTickTime;
        double step = Math.min(deltaTime/lastTickDelta,1);
        this.draw(g, previousX + (nextX - previousX) * step, previousY + (nextY - previousY) * step, step);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    protected void setLastTickDelta(double lastTickDelta) {
        this.lastTickDelta = lastTickDelta;
    }

    protected double getLastTickDelta() {
        return lastTickDelta;
    }
    protected abstract void draw(Graphics2D g, double x, double y, double step);
}
