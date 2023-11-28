package com.jp.daichi.ex5shooting.render;

import com.jp.daichi.ex5shooting.particles.Charge;

import java.awt.*;

public class ChargeRender extends RoundParticleRender<Charge> {

    @Override
    protected void doRendering(Graphics2D g, Charge entity, double x, double y, double rotation,double radius, double step) {
        if (entity.getTime() >= entity.getExpansionTime()) {
            radius = entity.getOriginalRadius() + (Math.random() - 0.5) * entity.getOriginalRadius() * 0.05;//半径の1/20で振動
        }
        super.doRendering(g, entity, x, y, rotation, radius, step);

    }

    @Override
    public void loadImages() {

    }
}
