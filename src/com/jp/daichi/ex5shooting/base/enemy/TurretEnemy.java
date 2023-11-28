package com.jp.daichi.ex5shooting.base.enemy;

import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5shooting.Game;
import com.jp.daichi.ex5shooting.LivingEntity;
import com.jp.daichi.ex5shooting.utils.Utils;

public abstract class TurretEnemy extends Enemy {

    private LivingEntity target;
    private double maxSpeed = 0;
    private double acceleration = Utils.playerAcceleration;
    public TurretEnemy(Game game, RotationalObject rotationalObject, LivingEntity target, double size, double hp) {
        super(game, size, hp,rotationalObject);
        this.target = target;
    }

    public LivingEntity getTarget() {
        return target;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Override
    protected void doTick_(double deltaTime) {
        //ma'=ma-kv
        //終端速度=maxSpeed
        //ma=kv
        //k=ma/maxSpeed
        super.doTick_(deltaTime);
        if (target != null) {
                setRotation(Utils.getRotation(new Vec2d(target.getX() - getX(), target.getY() - getY()), getRotation(), getRotationLimit() * deltaTime));
                double k = acceleration/maxSpeed;
                Vec2d a = Utils.getDirectionVector(this);//加速度
                a.normalize();//正規化して
                a.multiple(getAcceleration());//指定した加速度に伸ばす
                Vec2d newA = a.subtract(getVector().multiple(k));//新しい加速度はma-kvから導く
                setVector(getVector().add(newA.multiple(deltaTime)));
        }
    }

    abstract double getRotationLimit();

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }
}
