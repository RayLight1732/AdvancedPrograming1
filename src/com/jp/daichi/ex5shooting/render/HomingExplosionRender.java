package com.jp.daichi.ex5shooting.render;

import com.jp.daichi.ex5shooting.base.bullet.HomingExplosion;

import java.awt.*;

public class HomingExplosionRender implements Render<HomingExplosion> {

    @Override
    public void render(Graphics2D g, HomingExplosion entity, double step) {

    }

    @Override
    public void loadImages() {

    }

    @Override
    public boolean renderInParticlePhase(HomingExplosion entity) {
        return false;
    }
}
