package com.jp.daichi.ex5.bullet;

import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.utils.Utils;

public abstract class HomingProjectile extends Projectile {

    private double maxSpeed;//最高速度
    private double rotateSpeed;//曲率半径
    private double acceleration;//加速度

    public HomingProjectile(Game game, GameEntity holder, double size, RotationalObject displayEntity) {
        super(game, holder, size, displayEntity);
    }

    public HomingProjectile(Game game, GameEntity holder, double size, RotationalObject displayEntity,double maxSpeed,double rotateSpeed,double acceleration) {
        super(game, holder, size, displayEntity);
        this.maxSpeed = maxSpeed;
        this.rotateSpeed = rotateSpeed;
        this.acceleration = acceleration;
    }

    public HomingProjectile(Game game, GameEntity holder, double size, RotationalObject displayEntity,double maxSpeed,double rotateSpeed,double acceleration,double damage) {
        super(game, holder, size, displayEntity,damage);
        this.maxSpeed = maxSpeed;
        this.rotateSpeed = rotateSpeed;
        this.acceleration = acceleration;
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        GameEntity target = getTarget();
        if (target != null) {
            /*
            //ma=F-kv
            Vec2d newSpeed = getVector();
            newSpeed.add(Utils.getDirectionVector(this).multiple(getAcceleration()*deltaTime));
            double newSpeedMagnitude = newSpeed.getLength();
            Vec2d normalized = new Vec2d(newSpeed);
            normalized.normalize();
            //自機の位置ベクトルA、自機の速度ベクトルをv、敵の位置ベクトルBとしたとき、
            //ABベクトルのvへの射影をPとする。
            //|v|:AP=x:PBとなるxを求める。そのxをPBベクトルにかけたものをQベクトルとする
            //x = |v|*PB/AP
            // Qをvに加えると、加えた後のベクトルは敵の方向を向く(向心加速度と呼ぶ)
            Vec2d toEnemy = new Vec2d(target.getX()-getX(),target.getY()-getY());//ABベクトル
            //射影の計算式はもとのベクトルをuとすると、dot(v,u)*v/|v|^2　だが、|v|=1とすると、dot(v,u)*v
            Vec2d projection = normalized.multiple(toEnemy.dot(normalized));//射影(APベクトル)
            double centripetalAccelMagnitude = newSpeedMagnitude*(new Vec2d(toEnemy).subtract(projection).getLength())/projection.getLength();//向心加速度の大きさ
            double maxCentripetalAccelMagnitude = getMaxSpeed()*getMaxSpeed()/curvatureRadius;//最大の加速度の大きさ
            centripetalAccelMagnitude = Math.min(centripetalAccelMagnitude,maxCentripetalAccelMagnitude);
            Vec2d centripetalAccel = toEnemy.subtract(projection);
            centripetalAccel.normalize();
            centripetalAccel.multiple(centripetalAccelMagnitude*deltaTime);

            newSpeed.add(centripetalAccel);

            newSpeed.subtract(new Vec2d(newSpeed).multiple(getAcceleration()/getMaxSpeed()*deltaTime));
            setVector(newSpeed);
            setRotation(Utils.getRotation(newSpeed));
             */
            /*
            Vec2d toTarget = new Vec2d(target.getX()-getX(),target.getY()-getY());
            Vec2d vn = getVector();
            vn.normalize();
            Vec2d centripetalAccel = toTarget.subtract(vn.copy().multiple(vn.dot(toTarget)));
            double centripetalAccelMagnitude = centripetalAccel.getLength();
            if (centripetalAccelMagnitude > 1) {
                centripetalAccel.multiple(1/centripetalAccelMagnitude);
            }
            double maxCentripetalAccelMagnitude = getMaxSpeed()*getMaxSpeed()/curvatureRadius;//最大の加速度の大きさ
            Vec2d force = centripetalAccel.multiple(maxCentripetalAccelMagnitude);
            force.add(vn.multiple(acceleration));
            force.subtract(getVector().multiple(getAcceleration()/getMaxSpeed()));//減衰率はma=F-kvよりk=F/v_max
            setVector(getVector().add(force.multiple(deltaTime)));
            setRotation(Utils.getRotation(getVector()));

             */
            setRotation(Utils.getRotation(new Vec2d(target.getX() - getX(), target.getY() - getY()), getRotation(), rotateSpeed* deltaTime));
            setVector(Utils.getDirectionVector(getRotation()).multiple(getVector().getLength()));
            double k = acceleration/maxSpeed;
            Vec2d a = Utils.getDirectionVector(this);//加速度
            a.normalize();//正規化して
            a.multiple(getAcceleration());//指定した加速度に伸ばす
            Vec2d newA = a.subtract(getVector().multiple(k));//新しい加速度はma-kvから導く
            setVector(getVector().add(newA.multiple(deltaTime)));
        }

    }

    public abstract GameEntity getTarget();

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getRotateSpeed() {
        return rotateSpeed;
    }

    public void setRotateSpeed(double rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
    }
}
