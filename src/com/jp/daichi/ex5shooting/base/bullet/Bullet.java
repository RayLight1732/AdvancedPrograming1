package com.jp.daichi.ex5shooting.base.bullet;

import com.jp.daichi.ex4.PathObject;
import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5shooting.*;
import com.jp.daichi.ex5shooting.utils.Utils;
import java.awt.*;
import java.awt.geom.Path2D;

/**
 * 弾を表す
 */
public class Bullet extends Projectile {

    /**
     * 弾の形を取得
     * @param length 長さ
     * @param width 幅
     * @param x x座標
     * @param y y座標
     * @param vec 速度
     * @param setCenterToBottom 弾の後ろ側を基準位置にするか
     * @return 弾
     */
    public static RotationalObject getBulletShape(double length, double width, double x, double y, Vec2d vec,boolean setCenterToBottom) {
        if (setCenterToBottom) {
            length *= -1;
        }
        RotationalObject rotationalObject;
        Path2D path = new Path2D.Double();
        path.moveTo(0,-width);
        path.lineTo(-length,-width);
        path.lineTo(-length,width);
        path.lineTo(0,width);
        path.closePath();
        rotationalObject = new PathObject(x,y,Utils.getRotation(vec),0,vec,path, Color.CYAN);
        //rotationalObject.setOutLineColor(Color.BLACK);
        return rotationalObject;
    }

    public static RotationalObject getBulletShape(double length, double width, double x, double y, Vec2d vec){
        return getBulletShape(length, width, x, y, vec,false);
    }

    public Bullet(Game game, GameEntity holder, double size, double x, double y, Vec2d vec, double damage) {
        this(game,holder,size,1,x,y,vec,damage);
    }

    public Bullet(Game game, GameEntity holder, double length,double width, double x, double y, Vec2d vec, double damage) {
        super(game,holder,length,getBulletShape(length,width,x,y,vec),damage);
    }

    public Bullet(Game game,GameEntity holder,double damage,double size,RotationalObject shape) {
        super(game,holder,size,shape,damage);
    }

    @Override
    public boolean doCollision(GameEntity entity) {
        return canAttack(entity);
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
