package com.jp.daichi.ex5.stage;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.LivingEntity;
import com.jp.daichi.ex5.enemy.TurretEnemy;
import com.jp.daichi.ex5.utils.Utils;

import java.util.*;

public class SlideSpawnStage implements Stage {

    private static final int margin = 10;
    private final List<TurretEnemyFactory> factories;
    private final List<TurretEnemy> enemies = new ArrayList<>();
    private final double startSpeed;
    private final double finalSpeed;
    private final double endSlideTime;
    private boolean setTarget = false;
    protected double time = 0;
    private final LivingEntity target;
    private boolean ended = false;
    private boolean started = false;

    public SlideSpawnStage(LivingEntity target,SlideSpawnInfo info) {
        this(target, Collections.nCopies(4,info.factory()),info.startSpeed(),info.finalSpeed(),info.endSlideTime());
    }

    public SlideSpawnStage(LivingEntity target, List<TurretEnemyFactory> factories,double startSpeed,double finalSpeed,double endSlideTime) {
        this.target = target;
        this.factories = factories;
        this.startSpeed = startSpeed;
        this.finalSpeed = finalSpeed;
        this.endSlideTime = endSlideTime;
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
            vec2d.multiple(startSpeed);
            if (factories.get(i)!= null) {
                TurretEnemy enemy = factories.get(i).create(game, x, y, vec2d);
                if (enemy != null) {
                    enemies.add(enemy);
                    game.addEntity(enemy);
                }
            }
        }
    }

    @Override
    public void tick(double deltaTime) {
        enemies.removeIf(TurretEnemy::isDead);
        time +=deltaTime;
        if (enemies.isEmpty()) {
            ended = true;
        }
        if (!ended) {
            if (time < endSlideTime) {
                for (var entity: enemies) {
                    Vec2d vec = entity.getVector();
                    vec.normalize();
                    vec.multiple(startSpeed+(finalSpeed-startSpeed)/endSlideTime*time);
                    entity.setVector(vec);
                }
            } else if (!setTarget) {
                setTarget = true;
                for (var entity: enemies) {
                    entity.setTarget(target);
                    entity.setVector(Utils.getDirectionVector(entity).multiple(finalSpeed));
                }
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

    public interface TurretEnemyFactory {
        TurretEnemy create(Game game,double x,double y,Vec2d vec2d);
    }

}
