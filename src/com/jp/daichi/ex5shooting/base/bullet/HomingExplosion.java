package com.jp.daichi.ex5shooting.base.bullet;

import com.jp.daichi.ex4.CircleObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5shooting.Game;
import com.jp.daichi.ex5shooting.GameEntity;
import com.jp.daichi.ex5shooting.particles.Charge;
import com.jp.daichi.ex5shooting.particles.Charge2;
import com.jp.daichi.ex5shooting.particles.Explosion;
import com.jp.daichi.ex5shooting.utils.Utils;

import java.awt.*;

public class HomingExplosion extends HomingProjectile {

    private static CircleObject createShape(double x, double y, Vec2d vec2d, double size) {
        CircleObject result = new CircleObject(x,y,vec2d,size);
        result.setRotation(Utils.getRotation(vec2d));
        return result;
    }

    private final GameEntity target;
    private double life;
    private final Charge charge;

    public HomingExplosion(Game game, GameEntity holder,GameEntity target, double size, double x, double y, Vec2d vec2d, double damage,double rotateSpeed,double life,Color color) {
        super(game, holder, size, createShape(x,y,vec2d,size),vec2d.getLength(),rotateSpeed,100,damage);
        this.target = target;
        this.life = life;
        this.charge = new Charge2(this,size,0,color);
        game.addParticle(charge);
    }

    @Override
    public void doTick_(double deltaTime) {
        super.doTick_(deltaTime);
        life -= deltaTime;
        if (life < 0) {
            game.removeEntity(this);
            charge.setEnd(true);
            game.addParticle(new Explosion(getX(),getY(),size*1.5));
        }
    }

    @Override
    public GameEntity getTarget() {
        return target;
    }

    @Override
    public void setVisible(boolean isVisible) {
        super.setVisible(isVisible);
        charge.setEnd(isVisible);
    }

    @Override
    public void onRemoved() {
        if (charge != null) {
            charge.setEnd(true);
        }
    }

    @Override
    public void collideWith(GameEntity entity) {
        super.collideWith(entity);
        game.addParticle(new Explosion(getX(),getY(),size*1.5));
    }

    public Charge getCharge() {
        return charge;
    }
}
