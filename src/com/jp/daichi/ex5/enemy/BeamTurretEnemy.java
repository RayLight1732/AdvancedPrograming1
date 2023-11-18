package com.jp.daichi.ex5.enemy;

import com.jp.daichi.ex4.PathObject;
import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.bullet.Bullet;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.LivingEntity;
import com.jp.daichi.ex5.utils.Utils;

import java.awt.*;
import java.awt.geom.Path2D;

public class BeamTurretEnemy extends TurretEnemy {

    public static RotationalObject createShape(double x, double y, double size) {
        Path2D path = new Path2D.Double();
        path.moveTo(size*0.2,0);
        double rotation = Math.toRadians(120);
        path.lineTo(size*Math.cos(rotation),size*Math.sin(rotation));
        path.lineTo(-size*0.2,0);
        rotation += Math.toRadians(120);
        path.lineTo(size*Math.cos(rotation),size*Math.sin(rotation));
        path.closePath();
        return new PathObject(x,y,0,0,new Vec2d(),path,Color.BLUE);
    }

    private static final double BULLET_COOL_TIME = 0.2;
    private static final double BULLET_COOL_TIME2 = 3;
    private static final int MAX_BULLET_COUNT = 3;

    private double firstCoolTime = 3;

    private double bulletCoolTime;//一発ずつのクールタイム
    private double bulletCoolTime2;//３発まとめてのクールタイム
    private int bulletCount = 0;


    public BeamTurretEnemy(Game game, LivingEntity target, double x, double y, double size, double hp) {
        super(game,createShape(x,y,size), target, size, hp);
    }

    @Override
    public void doTick(double deltaTime) {
        super.doTick(deltaTime);
        if (firstCoolTime > 0) {
            firstCoolTime -= deltaTime;
            return;
        }
        if (bulletCount >= MAX_BULLET_COUNT) {
            if (bulletCoolTime2 <= 0) {
                bulletCoolTime = 0;
                bulletCount = 0;
            } else {
                bulletCoolTime2 -= deltaTime;
            }
        } else {
            if (bulletCoolTime <= 0 && getTarget() != null) {
                bulletCount++;
                //Vec2d vec = new Vec2d(getTarget().getX()-getX(),getTarget().getY()-getY());
                Vec2d vec = Utils.getDirectionVector(this);
                if (vec.normalize()) {//正規化成功したとき
                    vec.multiple(Utils.enemyBulletSpeed);
                    Bullet bullet = new Bullet(getGame(),this,30, getX(), getY(), vec,1);
                    bullet.setColor(Color.RED);
                    getGame().addEntity(bullet);
                    bulletCoolTime = BULLET_COOL_TIME;
                    if (bulletCount == MAX_BULLET_COUNT) {
                        bulletCoolTime2 = BULLET_COOL_TIME2;
                    }
                }
            } else {
                bulletCoolTime -= deltaTime;
            }
        }
    }

    @Override
    double getRotationLimit() {
        return Utils.rotatetionSpeed;
    }

    @Override
    public int getScore() {
        return 5;
    }
}
