package com.jp.daichi.ex5shooting.render;

import com.jp.daichi.ex5shooting.GameObject;

import java.awt.*;

public abstract class LinearCompletionRender<T extends GameObject> implements Render<T> {
    @Override
    public final void render(Graphics2D g, T entity, double step) {
        if (entity.lastTickExisted()) {
            double x = getRenderPos(step,entity.getNewX(),entity.getLastTickX());
            double y = getRenderPos(step,entity.getNewY(),entity.getLastTickY());
            double rotation = getRenderPos(step,entity.getNewRotation(),entity.getLastTickRotation());
            doRendering(g, entity,x,y,rotation, step);
        }
    }

    @Override
    public void loadImages() {

    }

    @Override
    public boolean renderInParticlePhase(T entity) {
        return false;
    }

    protected double getRenderPos(double step, double now, double last) {
        return last+step*(now-last);
    }
    protected abstract void doRendering(Graphics2D g,T entity,double x,double y,double rotation,double step);
}
