package com.jp.daichi.ex5.bullet;

import com.jp.daichi.ex4.CircleObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.particles.Charge;
import com.jp.daichi.ex5.particles.Explosion;
import com.jp.daichi.ex5.utils.Utils;

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
        this.charge = new Charge(this,size,0,color);
        game.addParticle(charge);
    }

    @Override
    public void doTick(double deltaTime) {
        super.doTick(deltaTime);
        life -= deltaTime;
        if (life < 0) {
            game.removeEntity(this);
            charge.end(true);
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
        charge.end(isVisible);
    }

    @Override
    public void onRemoved() {
        if (charge != null) {
            charge.end(true);
        }
    }

    @Override
    public void collideWith(GameEntity entity) {
        super.collideWith(entity);
        game.addParticle(new Explosion(getX(),getY(),size*1.5));
    }
}
