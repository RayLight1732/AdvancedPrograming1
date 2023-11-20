package com.jp.daichi.ex5.render;

import com.jp.daichi.ex5.base.bullet.ThickBeam;
import com.jp.daichi.ex5.utils.ResourceManager;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class ThickBeamRender extends LinearCompletionRender<ThickBeam> {

    private static int width;
    private static int height;
    private static final double fps = 30;
    private static final int frame = 451;//総フレーム数
    @Override
    protected void doRendering(Graphics2D g, ThickBeam entity, double x, double y, double rotation, double step) {
        Image image = ResourceManager.getImage("beam.png");
        long current = System.currentTimeMillis();
        int h = (int)(((current-entity.getStartTime())/1000.0)*fps)%frame;
        h*=height;
        AffineTransform old = g.getTransform();
        AffineTransform transform = g.getTransform();
        transform.translate(x,y);
        transform.rotate(rotation);
        g.setTransform(transform);
        g.drawImage(image,(int)(-entity.getLength()*0.05),(int) -entity.getWidth()-100,(int)entity.getLength(),(int) entity.getWidth()+100,0,h,width,h+height,null);
        g.setTransform(old);
    }

    @Override
    public void loadImages() {
        ResourceManager.loadImage("beam.png",bf -> {
            width = bf.getWidth();
            height = bf.getHeight()/frame;
            return bf;
        });
    }

    @Override
    public boolean renderInParticlePhase(ThickBeam entity) {
        return true;
    }
}
