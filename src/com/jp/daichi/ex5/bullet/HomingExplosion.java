package com.jp.daichi.ex5.bullet;

import com.jp.daichi.ex4.CircleObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.particles.Charging;
import com.jp.daichi.ex5.particles.Explosion;
import com.jp.daichi.ex5.utils.Utils;

import java.awt.*;

public class HomingExplosion extends Bullet {

    private static CircleObject createShape(double x, double y, Vec2d vec2d, double size) {
        CircleObject result = new CircleObject(x,y,vec2d,size);
        result.setRotation(Utils.getRotation(vec2d));
        return result;
    }

    private final double rotateSpeed;
    private final GameEntity target;
    private double life;
    private final Charging charging;

    public HomingExplosion(Game game, GameEntity holder,GameEntity target, double size, double x, double y, Vec2d vec2d, double damage,double rotateSpeed,double life,Color color) {
        super(game, holder, damage,size, createShape(x,y,vec2d,size));
        this.target = target;
        this.rotateSpeed = rotateSpeed;
        this.life = life;
        this.charging = new Charging(this,size,0,color);
        game.addParticle(charging);
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        life -= deltaTime;
        if (life < 0) {
            game.removeEntity(this);
            charging.end(true);
            game.addParticle(new Explosion(getX(),getY(),size*1.5));
        } else {
            Vec2d direction = new Vec2d(target.getX()-getX(),target.getY()-getY());
            if (direction.getLength() > 0) {//長さが0以上の時
                setRotation(Utils.getRotation(direction,getRotation(),rotateSpeed*deltaTime));
            }
        }
    }

    @Override
    public void setVisible(boolean isVisible) {
        super.setVisible(isVisible);
        charging.end(isVisible);
    }

    @Override
    public void onRemoved() {
        if (charging != null) {
            charging.end(true);
        }
    }

    @Override
    public void collideWith(GameEntity entity) {
        super.collideWith(entity);
        game.addParticle(new Explosion(getX(),getY(),size*1.5));
    }
}
