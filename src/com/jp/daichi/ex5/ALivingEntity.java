package com.jp.daichi.ex5;

import com.jp.daichi.ex4.RotationalObject;

public abstract class ALivingEntity extends AGameEntity implements LivingEntity {
    protected double hp;
    protected boolean isDead = false;

    public ALivingEntity(Game game,double hp,double size,RotationalObject displayEntity) {
        super(game,size,displayEntity);
        this.hp = hp;
    }

    @Override
    public double getHP() {
        return hp;
    }

    @Override
    public void setHP(double hp) {
        this.hp = hp;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public void kill() {
        isDead = true;
        setVisible(false);
    }
}
