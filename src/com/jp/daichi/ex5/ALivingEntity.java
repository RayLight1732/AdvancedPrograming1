package com.jp.daichi.ex5;

import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.particles.Explosion;

import java.awt.*;
import java.awt.geom.Area;

public abstract class ALivingEntity extends AGameEntity implements LivingEntity {

    private double maxHP;
    private double hp;
    protected boolean isDead = false;
    protected boolean impulse = false;

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

    @Override
    public void killedBy(GameEntity entity) {
        kill();
        game.killedBy(this,entity);
    }

    @Override
    public void collideWith(GameEntity entity) {
        if (entity instanceof ALivingEntity le) {
            Area area = this.getArea();
            area.intersect(entity.getArea());
            Rectangle rectangle = area.getBounds();
            this.pushedBy(entity,rectangle.getCenterX(),rectangle.getCenterY());
            le.pushedBy(this,rectangle.getCenterX(),rectangle.getCenterY());
        }
    }


    /**
     * 対象のエンティティに押される
     * @param entity 対象のエンティティ
     * @param collideX 衝突した場所のX座標
     * @param collideY 衝突した場所のY座標
     */
    protected void pushedBy(GameEntity entity,double collideX,double collideY) {
        Vec2d vec = new Vec2d(collideX-this.getX(),collideY-this.getY());
        setVector(getVector().add(vec.multiple(-3)));
        impulse = true;
    }

}
