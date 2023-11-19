package com.jp.daichi.ex5.bullet;

import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.utils.Utils;
import com.jp.daichi.ex5.enemy.Enemy;
import com.jp.daichi.ex5.particles.Explosion;

public class Missile extends HomingProjectile {

    private static final double range = 300;
    private double life;
    public Missile(Game game, GameEntity holder, double size, double x,double y,Vec2d vec, double maxSpeed, double curvatureRadius, double acceleration, double damage,double life) {
        super(game,holder,size,Bullet.getBulletShape(size,1,x,y,vec),maxSpeed,curvatureRadius,acceleration,damage);
        this.life = life;
    }

    @Override
    public void doTick(double deltaTime) {
        super.doTick(deltaTime);
        life -= deltaTime;
        if (life < 0) {
            game.removeEntity(this);
            game.addParticle(new Explosion(getX(),getY(),size));
        }
    }

    private GameEntity target = null;
    @Override
    public GameEntity getTarget() {
        if (target != null && !target.isVisible()) {
            target = null;
        }
        if (target == null) {
            Vec2d thisDirection = Utils.getDirectionVector(this).multiple(100);
            double centerX = getX()+thisDirection.x;
            double centerY = getY()+thisDirection.y;
            target = game.getEntities().stream()
                    .filter(it -> it instanceof Enemy)
                    .min((o1, o2) -> (int) (Utils.getSqDistance(centerX,centerY,o1.getX(),o1.getY()) - Utils.getSqDistance(centerX,centerY,o2.getX(),o2.getY()))).orElse(null);
        }
        return target;
    }

}
