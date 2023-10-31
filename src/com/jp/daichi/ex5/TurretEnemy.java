package com.jp.daichi.ex5;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;

public class TurretEnemy extends Enemy {

    private static final double BULLET_COOL_TIME = 1;
    private static final double BULLET_COOL_TIME2 = 5;
    private static final int MAX_BULLET_COUNT = 3;

    private double firstCoolTime = 3;

    private double bulletCoolTime;//一発ずつのクールタイム
    private double bulletCoolTime2;//３発まとめてのクールタイム
    private int bulletCount = 0;
    private LivingEntity target;


    public TurretEnemy(Game game,double x, double y, double size, LivingEntity target) {
        super(game,x, y, size);
        this.target = target;
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
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
            if (bulletCoolTime <= 0) {
                bulletCount++;
                Vec2d vec = new Vec2d(target.getX()-getX(),target.getY()-getY());
                if (Utils.normalize(vec)) {//正規化成功したとき
                    Utils.multiple(vec,Utils.enemyBulletSpeed);
                    Bullet bullet = new Bullet(game,this,30, getX(), getY(), vec,1);
                    bullet.setColor(Color.RED);
                    game.addEntity(bullet);
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

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Override
    protected Enemy createNewEnemy(Game game, double x, double y, double size) {
        return new TurretEnemy(game,x,y,size,target);
    }
}
