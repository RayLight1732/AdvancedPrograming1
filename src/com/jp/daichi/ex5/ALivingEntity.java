package com.jp.daichi.ex5;

import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex5.particles.Explosion;

public abstract class ALivingEntity extends AGameEntity implements LivingEntity {

    private double maxHP;
    private double hp;
    protected boolean isDead = false;

    public ALivingEntity(Game game,double hp,double size,RotationalObject displayEntity) {
        super(game,size,displayEntity);
        this.maxHP = hp;
        this.hp = hp;
    }

    @Override
    public double getHP() {
        return hp;
    }

    @Override
    public void setHP(double hp) {
        this.hp = Math.min(hp,maxHP);
    }

    @Override
    public double setMaxHP(double maxHP) {
        return this.maxHP = maxHP;
    }

    @Override
    public double getMaxHP() {
        return maxHP;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public void kill() {
        isDead = true;
        setVisible(false);
        Game game = getGame();
        game.removeEntity(this);
        game.addParticle(new Explosion(getX(),getY(),size));
    }
}
