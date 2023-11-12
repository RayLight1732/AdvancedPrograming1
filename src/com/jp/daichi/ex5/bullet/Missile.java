package com.jp.daichi.ex5.bullet;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.utils.Utils;
import com.jp.daichi.ex5.enemy.Enemy;
import com.jp.daichi.ex5.particles.Explosion;

public class Missile extends Bullet {

    private static final double range = 300;
    private double life;
    private final double rotateSpeed;
    public Missile(Game game, GameEntity holder, double size, double life, double x, double y, Vec2d vec, double rotateSpeed, double damage) {
        super(game, holder, size, x, y, vec, damage);
        this.rotateSpeed = rotateSpeed;
        this.life = life;
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        life -= deltaTime;
        if (life < 0) {
            game.removeEntity(this);
            game.addParticle(new Explosion(getX(),getY(),size));
            return;
        }
        Vec2d thisDirection = Utils.getDirectionVector(this);//このエンティティの方向ベクトル
        thisDirection.multiple(range);
        double centerX = getX()+thisDirection.x;
        double centerY = getY()+thisDirection.y;
        GameEntity target = game.getEntities().stream().filter(it -> it instanceof Enemy).min((o1, o2) -> (int) (Utils.getSqDistance(centerX,centerY,o1.getX(),o1.getY()) - Utils.getSqDistance(centerX,centerY,o2.getX(),o2.getY()))).orElse(null);//このエンティティとの距離でソート
        if (target == null || Utils.getSqDistance(centerX,centerY,target.getX(),target.getY()) > range*range) {
            return;
        }
        Vec2d direction = new Vec2d(target.getX()-getX(),target.getY()-getY());
        if (direction.getLength() > 0) {//長さが0以上の時
            setRotation(Utils.getRotation(direction,getRotation(),rotateSpeed*deltaTime));
        }
    }
}
