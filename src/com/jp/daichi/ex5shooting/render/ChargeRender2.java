package com.jp.daichi.ex5shooting.render;

import com.jp.daichi.ex5shooting.particles.Charge2;
import com.jp.daichi.ex5shooting.utils.ResourceManager;

import java.awt.*;

public class ChargeRender2 extends RoundParticleRender<Charge2> {
    private static int size;//一枚当たりの縦、横の長さ
    private static final double fps = 30;
    private static int frame;//総フレーム数
    @Override
    protected void doRendering(Graphics2D g, Charge2 entity, double x, double y, double rotation, double radius, double step) {
        Image image = ResourceManager.getImage("charge.png");
        long current = System.currentTimeMillis();
        int h = (int)(((current-entity.getStartTime())/1000.0*fps)%frame)*size;
        radius *=1.3;
        g.drawImage(image,(int)(x-radius),(int)(y-radius),(int)(x+radius),(int)( y+radius),0,h,size,h+size,null);

    }

    @Override
    public void loadImages() {
        ResourceManager.loadImage("charge.png",bf -> {
            size = bf.getWidth();
            frame = bf.getHeight()/bf.getWidth();
            return bf;
        });
    }

}
