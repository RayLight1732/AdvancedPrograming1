package com.jp.daichi.ex5.base.enemy;

import com.jp.daichi.ex4.RPolygonObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.LivingEntity;
import com.jp.daichi.ex5.utils.Utils;

import java.awt.*;

public class NoAITurret extends TurretEnemy {

    public NoAITurret(Game game, double x,double y,double size, double hp, LivingEntity target) {
        super(game, new RPolygonObject(x,y,size,3,0,0,new Vec2d(), Color.RED), target, size, hp);
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    double getRotationLimit() {
        return Utils.rotatetionSpeed;
    }
}
