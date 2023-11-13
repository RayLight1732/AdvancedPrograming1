package com.jp.daichi.ex5.stage;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.LivingEntity;
import com.jp.daichi.ex5.enemy.BeamTurretEnemy;
import com.jp.daichi.ex5.enemy.TurretEnemy;
import com.jp.daichi.ex5.utils.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FirstStage implements Stage {
    private static final int margin = 10;
    private final Set<TurretEnemy> entities = new HashSet<>();
    private boolean setTarget = false;
    private double time = 0;
    private final LivingEntity target;
    private boolean ended = false;
    private boolean started = false;

    public FirstStage(LivingEntity target) {
        this.target = target;
    }
    @Override
    public void startStage(Game game) {
        started = true;
        for (int i = 0;i < 4;i++) {
            double x,y;
            switch (i) {
                case 0 -> {
                    x = -margin;
                    y = -margin;
                }
                case 1 -> {
                    x = -margin;
                    y = game.getHeight()+margin;
                }
                case 2 -> {
                    x = game.getWidth()+margin;
                    y = game.getHeight()+margin;
                }
                default->{
                    x = game.getWidth()+margin;
                    y = -margin;
                }
            }
            Vec2d vec2d = new Vec2d(game.getWidth()/2.0-x, game.getHeight()/2.0-y);
            vec2d.normalize();
            vec2d.multiple(100);

            var entity = new BeamTurretEnemy(game,x,y,30,5,null,vec2d);
            game.addEntity(entity);
            entities.add(entity);
        }
    }

    @Override
    public void tick(double deltaTime) {
        entities.removeIf(LivingEntity::isDead);
        time +=deltaTime;
        if (entities.isEmpty()) {
            ended = true;
        }
        if (time > 3 && !setTarget) {
            setTarget = true;
            for (var entity:entities) {
                entity.setTarget(target);
                entity.setVector(new Vec2d(Utils.playerSpeed*0.5,0));
            }
        }
    }

    @Override
    public boolean isEnded() {
        return ended;
    }

    @Override
    public boolean started() {
        return started;
    }
}
