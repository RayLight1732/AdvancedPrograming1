package com.jp.daichi.ex5;

import com.jp.daichi.ex4.PathObject;
import com.jp.daichi.ex4.RotationalObject;
import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.geom.Path2D;

public class Bullet extends AGameEntity {

    private static RotationalObject getBulletShape(double size,double x,double y,Vec2d vec) {
        RotationalObject rotationalObject;
        Path2D path = new Path2D.Double();
        path.moveTo(0,-1);
        path.lineTo(size,-1);
        path.lineTo(size,1);
        path.lineTo(0,1);
        path.closePath();
        rotationalObject = new PathObject(x,y,0,vec,path, Color.CYAN);
        rotationalObject.setRotation(Utils.getRotation(vec));
        rotationalObject.setOutLineColor(Color.BLACK);
        return rotationalObject;
    }

    private final GameEntity holder;
    private final double damage;
    public Bullet(Game game,GameEntity holder,double size, double x, double y, Vec2d vec, double damage) {
        super(game,size,getBulletShape(size,x,y,vec));
        this.holder = holder;
        this.damage = damage;
    }

    public GameEntity getHolder() {
        return holder;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public boolean doCollision(GameEntity entity) {
        return entity != holder && !(entity instanceof Bullet);//ホルダー,弾以外とは衝突判定
    }

    @Override
    public void collideWith(GameEntity entity) {
        if (entity != holder && entity instanceof LivingEntity) {
            ((LivingEntity)entity).attackedBy(holder,getDamage());
            game.removeEntity(this);
        }
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        if (getX() < -200 || game.getWidth()+200 < getX()
                || getY() < -200 || game.getHeight()+200 < getY()) {
            game.removeEntity(this);
        }
    }

    public void setColor(Color color) {
        displayEntity.setColor(color);
    }
}
