package com.jp.daichi.ex5.render;

import com.jp.daichi.ex5.GameObject;
import com.jp.daichi.ex5.particles.Explosion;
import com.jp.daichi.ex5.particles.RoundParticle;

import java.awt.*;

public class RoundParticleRender<T extends RoundParticle> extends LinearCompletionRender<T> {
    @Override
    protected final void doRendering(Graphics2D g, T entity, double x, double y, double rotation, double step) {
        double radius = getRenderPos(step,entity.getNewRadius(),entity.getLastTickRadius());
        doRendering(g, entity, x, y, rotation,radius, step);
    }

    protected void doRendering(Graphics2D g,T entity,double x,double y,double rotation,double radius,double step) {
        g.setColor(entity.getColor());
        g.fillOval((int)(x-radius),(int)(y-radius),(int)(radius*2),(int)(radius*2));
    }

}
