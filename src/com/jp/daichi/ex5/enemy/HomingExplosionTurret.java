package com.jp.daichi.ex5.enemy;

import com.jp.daichi.ex4.PathObject;
import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.LivingEntity;
import com.jp.daichi.ex5.bullet.Bullet;
import com.jp.daichi.ex5.bullet.HomingExplosion;
import com.jp.daichi.ex5.particles.Charging;
import com.jp.daichi.ex5.utils.PositionConverter;
import com.jp.daichi.ex5.utils.Utils;

import java.awt.*;
import java.awt.geom.Path2D;

public class HomingExplosionTurret extends TurretEnemy {

    private static final double originalCoolTime = 3;
    private static final double originalChargeTime = 2;
    private static final double bulletRadius = 10;
    private static final double bulletLife = 6;

    private double coolTime = originalCoolTime;
    private double chargeTime = originalChargeTime;
    private Charging charging;
    private final PositionConverter converter = (x,y)->{
        double r = getSize()*0.2+bulletRadius;
        return new Vec2d(x+r*Math.cos(getRotation()),y+r*Math.sin(getRotation()));
    };


    public HomingExplosionTurret(Game game,double x, double y,double size, double hp, LivingEntity target) {
        super(game,BeamTurretEnemy.createShape(x,y,size), size, hp,target);
    }

    public HomingExplosionTurret(Game game,double x, double y,double size, double hp, LivingEntity target,Vec2d direction) {
        super(game,BeamTurretEnemy.createShape(x,y,size), size, hp,target,direction);
    }
    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        if (coolTime > 0) {
            coolTime-=deltaTime;
        } else if (chargeTime > 0) {
            if (charging == null) {
                charging = new Charging(this,bulletRadius,originalChargeTime*0.8,converter, Color.ORANGE);
                game.addParticle(charging);
            }
            chargeTime-=deltaTime;
        } else {//発射
            if (getTarget() != null) {
                charging.end(true);
                charging = null;
                Vec2d pos = converter.convert(getX(), getY());
                Bullet bullet = new HomingExplosion(game, this, getTarget(), bulletRadius, pos.x, pos.y, getVector().multiple(3), 10, Utils.rotatetionSpeed * 0.8, bulletLife, Color.ORANGE);
                game.addEntity(bullet);
                coolTime = originalCoolTime;
                chargeTime = originalChargeTime;
            }
        }
    }


    @Override
    double getRotationLimit() {
        return Utils.rotatetionSpeed;
    }

    @Override
    public void onRemoved() {
        if (charging != null) {
            charging.end(true);
        }
    }
}
