package com.jp.daichi.ex5.bullet;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.utils.Utils;
import com.jp.daichi.ex5.enemy.Enemy;
import com.jp.daichi.ex5.particles.Explosion;

public class Missile extends Bullet {

    private double life;
    private double rotateSpeed;
    private double range = 300;
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
        Vec2d direction = new Vec2d(target.getX()-getX(),target.getY()-getY());//マウスポインタへの方向ベクトル
        if (direction.getLength() > 0) {//長さが0以上の時
            double angle = Utils.getRotation(direction);
            double delta = angle - getRotation()%(Math.PI*2);//現在の角度との差
            delta %= 2*Math.PI;//0~2*PIに正規化
            //deltaの絶対値をなるべく小さくする
            if (delta > Math.PI) {//180以上であれば
                delta -= 2*Math.PI;//マイナスで表す
            } else if (delta < -Math.PI) {//-180以下であれば
                delta += 2*Math.PI;//プラスで表す
            }
            if (Math.abs(delta) > Utils.rotateSpeed*deltaTime) {//deltaがlimitよりも大きければ
                delta = Math.signum(delta)*rotateSpeed*deltaTime;//limitに制限
            }
            setRotation(getRotation()+delta);//角度更新
            double length = getVector().getLength();
            Vec2d vec = Utils.getDirectionVector(this);
            vec.multiple(length);
            setVector(vec);
        }
    }
}
