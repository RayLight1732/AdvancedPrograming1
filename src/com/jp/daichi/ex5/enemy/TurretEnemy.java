package com.jp.daichi.ex5.enemy;

import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.LivingEntity;
import com.jp.daichi.ex5.utils.Utils;

public abstract class TurretEnemy extends Enemy {

    private LivingEntity target;

    public TurretEnemy(Game game, RotationalObject rotationalObject, double size, double hp, LivingEntity target) {
        this(game,rotationalObject,size,hp,target,new Vec2d());
    }

    public TurretEnemy(Game game, RotationalObject rotationalObject, double size, double hp, LivingEntity target, Vec2d direction) {
        super(game, size, hp,rotationalObject);
        this.target = target;
        setVector(direction);
        setRotation(Utils.getRotation(direction));
    }

    public LivingEntity getTarget() {
        return target;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        if (target != null) {
            setRotation(Utils.getRotation(new Vec2d(target.getX()-getX(),target.getY()-getY()),getRotation(),getRotationLimit()*deltaTime));
        }
    }

    @Override
    public void setRotation(double rotation) {
        super.setRotation(rotation);
        double length = getVector().getLength();
        if (length > 0) {
            Vec2d vec = Utils.getDirectionVector(this);
            vec.multiple(length);
            setVector(vec);
        }
    }

    abstract double getRotationLimit();

}
