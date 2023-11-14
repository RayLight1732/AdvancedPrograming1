package com.jp.daichi.ex5.particles;

import java.awt.*;

public abstract class SmoothRoundParticle extends SmoothParticle {
    private double radius;
    private double previousR;
    private double nextR;

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    protected void smooth(boolean first) {
        super.smooth(first);
        if (first) {
            previousR = radius;
        } else {
            previousR = nextR;
        }
        nextR = radius;
    }

    @Override
    final protected void draw(Graphics2D g, double x, double y, double step) {
        draw(g,x,y,previousR + (nextR - previousR) * step,step);
    }

    protected abstract void draw(Graphics2D g,double x,double y,double radius,double step);
}
