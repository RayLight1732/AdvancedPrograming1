package com.jp.daichi.ex5;

import com.sun.javafx.geom.Vec2d;

public class Utils {

    public static final double playerBulletSpeed = 500;
    public static final double enemyBulletSpeed = 300;
    public static final double playerSpeed = 300;
    public static final double playerSpeedStep = 60;

    public static final double rotateSpeed = Math.toRadians(180);//90度をラジアンに変換
    public static double getLength(Vec2d vec) {
        return Math.sqrt(vec.x*vec.x+vec.y*vec.y);
    }

    public static void multiple(Vec2d vec,double length) {
        vec.x *= length;
        vec.y *= length;
    }

    public static boolean normalize(Vec2d vec) {
        double length = getLength(vec);
        if (length != 0) {
            multiple(vec,1/length);
            return true;
        } else {
            return false;
        }
    }

    public static Vec2d getDirection(GameEntity entity) {
        return getDirection(entity.getRotation());
    }

    public static Vec2d getDirection(double direction) {
        return new Vec2d(Math.cos(direction),Math.sin(direction));
    }

    public static double getRotation(Vec2d vec2d) {
        Vec2d copied = new Vec2d(vec2d);
        normalize(copied);
        double angle = Math.acos(copied.x);//角度取得
        return copied.y > 0 ? angle : Math.PI*2-angle;//-PI ~ PIまでの範囲に(ラジアンで)

    }

    public static double getSqDistance(double x1,double y1,double x2,double y2) {
        return (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
    }

    public static double getSqDistance(GameEntity e1,GameEntity e2) {
        return getSqDistance(e1.getX(),e1.getY(),e2.getX(),e2.getY());
    }
}
