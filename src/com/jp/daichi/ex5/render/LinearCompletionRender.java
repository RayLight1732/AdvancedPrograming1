package com.jp.daichi.ex5.render;

import com.jp.daichi.ex5.GameEntity;

import java.awt.*;

public abstract class LinearCompletionRender<T extends GameEntity> implements Render<T> {
    @Override
    public final void render(Graphics2D g, T entity, double step) {
        if (entity.lastTickExisted()) {
            double x = getRenderPos(step,entity.getNewX(),entity.getLastTickX());
            double y = getRenderPos(step,entity.getNewY(),entity.getLastTickY());
            double rotation = getRenderPos(step,entity.getNewRotation(),entity.getLastTickRotation());
            doRendering(g, entity,x,y,rotation, step);
        }
    }

    private double getRenderPos(double step,double now,double last) {
        return last+step*(now-last);
    }
    protected abstract void doRendering(Graphics2D g,T entity,double x,double y,double rotation,double step);
}
