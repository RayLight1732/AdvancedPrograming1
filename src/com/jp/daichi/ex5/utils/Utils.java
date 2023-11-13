package com.jp.daichi.ex5.utils;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.GameEntity;

public class Utils {

    public static final double tickParSecond = 1/20.0;
    public static final double playerBulletSpeed = 500;
    public static final double enemyBulletSpeed = 300;
    public static final double playerSpeed = 500;
    public static final double playerSpeedStep = 250;

    public static final double rotatetionSpeed = Math.toRadians(180);//180度をラジアンに変換

    public static Vec2d getDirectionVector(GameEntity entity) {
        return getDirectionVector(entity.getRotation());
    }

    public static Vec2d getDirectionVector(double direction) {
        return new Vec2d(Math.cos(direction),Math.sin(direction));
    }

    public static double getRotation(Vec2d vec2d) {
        Vec2d copied = new Vec2d(vec2d);
        copied.normalize();
        double angle = Math.acos(copied.x);//角度取得
        return copied.y > 0 ? angle : Math.PI*2-angle;//-PI ~ PIまでの範囲に(ラジアンで)

    }

    public static double getSqDistance(double x1,double y1,double x2,double y2) {
        return (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
    }

    public static double getSqDistance(GameEntity e1,GameEntity e2) {
        return getSqDistance(e1.getX(),e1.getY(),e2.getX(),e2.getY());
    }

    /**
     * 現在からの差がrotationLimitを超えない値で、targetVecが表す角度に最も近い角度を返す
     * @param targetVec 目標となるベクトル
     * @param nowRotation 現在の角度
     * @param rotationLimit 回転できる角度
     * @return targetVecが表す角度に最も近い角度
     */
    public static double getRotation(Vec2d targetVec,double nowRotation,double rotationLimit) {
        if (targetVec.getLength() > 0) {
            double angle = Utils.getRotation(targetVec);
            double delta = angle - nowRotation;//現在の角度との差
            delta %= 2 * Math.PI;//0~2*PIの範囲に
            //deltaの絶対値をなるべく小さくする
            if (delta > Math.PI) {//180以上であれば
                delta -= 2 * Math.PI;//マイナスで表す
            } else if (delta < -Math.PI) {//-180以下であれば
                delta += 2 * Math.PI;//プラスで表す
            }
            if (Math.abs(delta) > rotationLimit) {//deltaがlimitよりも大きければ
                delta = Math.signum(delta) * rotationLimit;//limitに制限
            }
            return delta + nowRotation;
        } else {
            return nowRotation;
        }
    }

    public static double dot(double x1,double y1,double x2,double y2) {
        return x1*y2 - y1*x2;
    }
}
