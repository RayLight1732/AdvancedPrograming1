package com.jp.daichi.ex5.base.enemy;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.LivingEntity;
import com.jp.daichi.ex5.base.bullet.HomingExplosion;
import com.jp.daichi.ex5.base.bullet.Projectile;
import com.jp.daichi.ex5.particles.Charge;
import com.jp.daichi.ex5.utils.PositionConverter;
import com.jp.daichi.ex5.utils.Utils;

import java.awt.*;

public class HomingExplosionTurret extends TurretEnemy {

    private static final double originalCoolTime = 3;
    private static final double originalChargeTime = 2;
    private final double bulletRadius;
    private static final double bulletLife = 6;

    private double coolTime = originalCoolTime;
    private double chargeTime = originalChargeTime;
    private Charge charge;
    private final PositionConverter converter;


    public HomingExplosionTurret(Game game, LivingEntity target, double x, double y, double size, double hp,double bulletRadius) {
        super(game,BeamTurretEnemy.createShape(x,y,size), target, size, hp);
        this.bulletRadius = 20;
        converter = (x_,y_)->{
            double r = getSize()*0.2+bulletRadius;
            return new Vec2d(x_+r*Math.cos(getRotation()),y_+r*Math.sin(getRotation()));
        };

    }

    @Override
    public void doTick_(double deltaTime) {
        super.doTick_(deltaTime);
        if (coolTime > 0) {
            coolTime-=deltaTime;
        } else if (chargeTime > 0) {
            if (charge == null) {
                charge = new Charge(this,bulletRadius,originalChargeTime*0.8,converter, Color.ORANGE);
                game.addParticle(charge);
            }
            chargeTime-=deltaTime;
        } else {//発射
            if (getTarget() != null) {
                charge.setEnd(true);
                charge = null;
                Vec2d pos = converter.convert(getX(), getY());
                Projectile bullet = new HomingExplosion(game, this, getTarget(), bulletRadius, pos.x, pos.y, getVector().multiple(3), 10, Utils.rotatetionSpeed * 0.8, bulletLife, Color.ORANGE);
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
        if (charge != null) {
            charge.setEnd(true);
        }
    }

    @Override
    public int getScore() {
        return 5;
    }
}
