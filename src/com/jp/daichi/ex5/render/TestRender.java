package com.jp.daichi.ex5.render;

import com.jp.daichi.ex5.base.bullet.ThickBeam;

import java.awt.*;

public class TestRender implements Render<ThickBeam> {
    private double newR2;
    private double lastR2;

    protected void doRendering(Graphics2D g, ThickBeam entity, double x, double y, double rotation, double step,double newR,double lastR) {
        g.setColor(entity.getRotationalObject().getColor());
        g.fill(entity.getRotationalObject().getArea(x,y,rotation));
        /*
        if (newR > lastR) {
            if (rotation > newR || rotation < lastR) {
                System.out.println(step);
                System.out.println(rotation);
                System.out.println("error1");
            }
        } else {
            if (rotation < newR || rotation >lastR) {
                System.out.println(step);
                System.out.println(rotation);
                System.out.println("error2");
            }
        }

         */
        if (newR2 == lastR) {
            lastR2 = lastR;
            newR2 = newR;
            if (step > 0.5) {
                System.out.println(step+" error");
            }
        } else if (newR2 == newR) {

        } else {
            System.out.println("error");
            lastR2 = lastR;
            newR2 = newR;
        }
        //System.out.println(newR+","+lastR+","+step);
    }

    private double getRenderPos(double step,double now,double last) {
        return last+step*(now-last);
    }
    @Override
    public void render(Graphics2D g, ThickBeam entity, double step) {
        if (entity.lastTickExisted()) {
            double x = getRenderPos(step,entity.getNewX(),entity.getLastTickX());
            double y = getRenderPos(step,entity.getNewY(),entity.getLastTickY());
            double newR = entity.getNewRotation();
            double lastR = entity.getLastTickRotation();
            double rotation = getRenderPos(step,newR,lastR);
            doRendering(g, entity,x,y,rotation, step,newR,lastR);
        }
    }

    @Override
    public void loadImages() {

    }

    @Override
    public boolean renderInParticlePhase(ThickBeam entity) {
        return false;
    }
}
