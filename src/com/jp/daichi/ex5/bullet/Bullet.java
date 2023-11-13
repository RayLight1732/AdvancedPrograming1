package com.jp.daichi.ex5.bullet;

import com.jp.daichi.ex4.PathObject;
import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.*;
import com.jp.daichi.ex5.utils.Utils;
import java.awt.*;
import java.awt.geom.Path2D;

public class Bullet extends AGameEntity {

    private static RotationalObject getBulletShape(double length, double width, double x, double y, Vec2d vec) {
        RotationalObject rotationalObject;
        Path2D path = new Path2D.Double();
        path.moveTo(0,-width);
        path.lineTo(length,-width);
        path.lineTo(length,width);
        path.lineTo(0,width);
        path.closePath();
        rotationalObject = new PathObject(x,y,Utils.getRotation(vec),0,vec,path, Color.CYAN);
        //rotationalObject.setOutLineColor(Color.BLACK);
        return rotationalObject;
    }

    private final GameEntity holder;
    private final double damage;
    public Bullet(Game game, GameEntity holder, double size, double x, double y, Vec2d vec, double damage) {
        this(game,holder,size,1,x,y,vec,damage);
    }

    public Bullet(Game game, GameEntity holder, double length,double width, double x, double y, Vec2d vec, double damage) {
        super(game,length,getBulletShape(length,width,x,y,vec));
        this.holder = holder;
        this.damage = damage;
    }

    public Bullet(Game game,GameEntity holder,double damage,double size,RotationalObject shape) {
        super(game,size,shape);
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
        return entity != holder && !(entity instanceof Bullet) && holder.doCollision(entity);//ホルダー,弾以外とは衝突判定をし、ホルダーが衝突しないものとは衝突しない
    }

    @Override
    public int getCollisionRulePriority() {
        return 100;
    }

    @Override
    public void collideWith(GameEntity entity) {
        ((LivingEntity)entity).attackedBy(holder,getDamage());
        getGame().removeEntity(this);
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        if (getX() < -200 || getGame().getWidth()+200 < getX()
                || getY() < -200 || getGame().getHeight()+200 < getY()) {
            getGame().removeEntity(this);
        }
    }

    public void setColor(Color color) {
        displayEntity.setColor(color);
    }

    @Override
    public void setRotation(double rotation) {
        super.setRotation(rotation);
        double length = getVector().getLength();
        if (length > 0) {
            Vec2d vec = Utils.getDirectionVector(this);
            vec.multiple(length);
            setVector(vec);
        }
    }
}
