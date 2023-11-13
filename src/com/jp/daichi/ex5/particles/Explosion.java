package com.jp.daichi.ex5.particles;

import java.awt.*;

public class Explosion extends SmoothRoundParticle {
    private static final Color color = new Color(255,140,0);
    private static final double expansion = 0.1;
    private static final double contraction = 0.3;

    private double time;
    private final double maxRadius;
    private boolean end = false;

    public Explosion(double x,double y,double radius) {
        setX(x);
        setY(y);
        this.maxRadius = radius;
        setRadius(0);
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        time += deltaTime;
        if (time < expansion) {
            setRadius(maxRadius*time/expansion);
        } else if (time < expansion+contraction) {
            setRadius(maxRadius*(expansion+contraction-time)/contraction);
        } else {
            end = true;
        }
    }


    /*
    @Override
    protected void draw(Graphics2D g, double x, double y, double step) {
        if (time < expansion) {
            g.setColor(color);
            g.fillOval((int)(x-radius*time/expansion),(int)(y-radius*time/expansion),(int)(radius*time*2/expansion),(int)(radius*time*2/expansion));
        } else if (time < endTime) {
            float[] hsb = Color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),null);
            hsb[1] = (float) (hsb[1]+(1-hsb[1])/contraction*(time-expansion));
            g.setColor(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
            g. fillOval((int)(x-radius*(endTime-time)/contraction),(int)(y-radius*(endTime-time)/contraction),(int)(radius*(endTime-time)/contraction*2),(int)(radius*(endTime-time)/contraction*2));
        }
    }*/

    @Override
    protected void draw(Graphics2D g, double x, double y, double radius, double step) {
        g.setColor(color);
        g. fillOval((int)(x-radius),(int)(y-radius),(int)(radius*2),(int)(radius*2));

    }

    @Override
    public boolean isEndDrawing() {
        return end;
    }
}
