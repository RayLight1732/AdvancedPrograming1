package com.jp.daichi.ex5shooting.base.bullet;

import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex5shooting.AGameEntity;
import com.jp.daichi.ex5shooting.Game;
import com.jp.daichi.ex5shooting.GameEntity;
import com.jp.daichi.ex5shooting.LivingEntity;
import com.jp.daichi.ex5shooting.particles.Explosion;

public abstract class Projectile extends AGameEntity {

    private GameEntity holder;
    private double damage;

    public Projectile(Game game,GameEntity holder, double size, RotationalObject displayEntity) {
        this(game,holder, size, displayEntity,0);
    }

    Projectile(Game game,GameEntity holder, double size, RotationalObject displayEntity,double damage) {
        super(game, size, displayEntity);
        this.holder = holder;
        this.damage = damage;
    }
    @Override
    public void doTick_(double deltaTime) {
        super.doTick_(deltaTime);
        if (getX() < -200 || getGame().getWidth()+200 < getX()
                || getY() < -200 || getGame().getHeight()+200 < getY()) {
            getGame().removeEntity(this);
        }
    }


    @Override
    public boolean doCollision(GameEntity entity) {
        return canAttack(entity);
    }

    @Override
    public void collideWith(GameEntity entity) {
        if (getHolder().canAttack(entity) && entity instanceof LivingEntity le) {
            le.attackedBy(getHolder(),getDamage());
            getGame().addParticle(new Explosion(getX(),getY(),getSize()));
            getGame().removeEntity(this);
        }
    }

    @Override
    public int getCollisionRulePriority() {
        return 100;
    }

    @Override
    public boolean canAttack(GameEntity entity) {
        return entity != holder && !(entity instanceof Projectile) && holder.canAttack(entity);//ホルダー、投射物以外かつ、ホルダーが攻撃できるもの
    }

    public GameEntity getHolder() {
        return holder;
    }

    public void setHolder(GameEntity holder) {
        this.holder = holder;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
