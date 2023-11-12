package com.jp.daichi.ex5.particles;

import java.awt.*;

public class Explosion implements Particle {
    private static final Color color = new Color(255,140,0);
    private static final double expansion = 0.1;
    private static final double contraction = 0.3;
    private static final double endTime = expansion+contraction;

    private double time;
    private final double x;
    private final double y;
    private final double radius;
    private boolean end = false;

    public Explosion(double x,double y,double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void tick(double deltaTime) {
        time += deltaTime;
        if (time >= endTime) {
            end = true;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (time < expansion) {
            g.setColor(color);
            g.fillOval((int)(x-radius*time/expansion),(int)(y-radius*time/expansion),(int)(radius*time*2/expansion),(int)(radius*time*2/expansion));
        } else if (time < endTime) {
            float[] hsb = Color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),null);
            hsb[1] = (float) (hsb[1]+(1-hsb[1])/contraction*(time-expansion));
            g.setColor(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
            g. fillOval((int)(x-radius*(endTime-time)/contraction),(int)(y-radius*(endTime-time)/contraction),(int)(radius*(endTime-time)/contraction*2),(int)(radius*(endTime-time)/contraction*2));
        }
    }

    @Override
    public boolean isEndDrawing() {
        return end;
    }
}
