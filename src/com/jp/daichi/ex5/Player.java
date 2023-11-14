package com.jp.daichi.ex5;

import com.jp.daichi.ex4.PathObject;
import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.bullet.Missile;
import com.jp.daichi.ex5.utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Path2D;

public class Player extends ALivingEntity {

    private static RotationalObject getPlayerShape(double size,double x,double y,Vec2d vec) {
        Path2D path = new Path2D.Double();
        path.moveTo(size,0);
        path.lineTo(Math.cos(Math.toRadians(140))*size,Math.sin(Math.toRadians(140))*size);
        path.lineTo(0,0);
        path.lineTo(Math.cos(Math.toRadians(-140))*size,Math.sin(Math.toRadians(-140))*size);
        path.closePath();
        return new PathObject(x,y,0,0,new Vec2d(),path,Color.RED);
    }

    private double bulletCoolTime = 0;//弾発射のクールタイム
    public Player(Game game,double x, double y, double size){
        super(game,20,size,getPlayerShape(size,x,y,new Vec2d()));
        MainFrame.keyBind.addKeyBind(KeyEvent.VK_W);//キーを登録
        MainFrame.keyBind.addKeyBind(KeyEvent.VK_A);//キーを登録
        MainFrame.keyBind.addKeyBind(KeyEvent.VK_S);//キーを登録
        MainFrame.keyBind.addKeyBind(KeyEvent.VK_D);//キーを登録
        MainFrame.keyBind.addKeyBind(KeyEvent.VK_SPACE);//キーを登録
    }

    @Override
    public boolean doCollision(GameEntity entity) {
        return true;
    }

    @Override
    public int getCollisionRulePriority() {
        return 0;
    }

    @Override
    public void collideWith(GameEntity entity) {

    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        if (!canShootBullet()) {//クールタイムが終わっていないなら
            bulletCoolTime -=deltaTime;//クールタイム減少
        }

        boolean forceRotate = false;//強制的に回転させるか
        double rotateTargetX = getX();
        double rotateTargetY = getY();
        if (getX() < 0 ) {
            rotateTargetX = 0;
            forceRotate = true;
        } else if (game.getWidth() < getX()) {
            rotateTargetX = game.getWidth();
            forceRotate = true;
        }
        if (getY() < 0 ) {
            rotateTargetY = 0;
            forceRotate = true;
        } else if (game.getHeight() < getY()) {
            rotateTargetY = game.getHeight();
            forceRotate = true;
        }
        if (forceRotate) {
            Vec2d direction = new Vec2d(rotateTargetX-getX(),rotateTargetY-getY());//向かうべきベクトル
            if (direction.getLength() > 0) {//長さが0以上の時
                setRotation(Utils.getRotation(direction,getRotation(),Utils.rotatetionSpeed *deltaTime));//角度更新
            }
        } else {
            if (MainFrame.keyBind.isPressed(KeyEvent.VK_A) && !MainFrame.keyBind.isPressed(KeyEvent.VK_D)) {//wキーが押されて、sキーが押されていないとき
                setRotation(getRotation() - deltaTime * Utils.rotatetionSpeed);
            } else if (!MainFrame.keyBind.isPressed(KeyEvent.VK_A) && MainFrame.keyBind.isPressed(KeyEvent.VK_D)) {//sキーが押されて、wキーが押されていないとき
                setRotation(getRotation() + deltaTime * Utils.rotatetionSpeed);
            }
        }

        Vec2d vec2d = getVector();
        double vecLength = vec2d.getLength();//ベクトルの長さ取得
        double deltaLength = Utils.playerSpeedStep*deltaTime;//一秒間にplayerSpeedStepだけ変化するとき、deltaTime秒で変化する量
        if (MainFrame.keyBind.isPressed(KeyEvent.VK_W) && !MainFrame.keyBind.isPressed(KeyEvent.VK_S)) {//wキーが押されて、sキーが押されていないとき
            vecLength = Math.min(vecLength+deltaLength,Utils.playerSpeed);//最大値設定
        } else if (!MainFrame.keyBind.isPressed(KeyEvent.VK_W) && MainFrame.keyBind.isPressed(KeyEvent.VK_S)) {//sキーが押されて、wキーが押されていないとき
            vecLength = Math.max(vecLength-deltaLength,0);//最小値0
        }

        Vec2d direction = Utils.getDirectionVector(this);//方向ベクトル取得
        direction.multiple(vecLength);//長さ指定
        setVector(direction);//ベクトル設定

        if (MainFrame.keyBind.isPressed(KeyEvent.VK_SPACE) && canShootBullet()) {
            direction = Utils.getDirectionVector(this);
            Vec2d shootPos = new Vec2d(getX()+direction.x*size,getY()+direction.y*size);
            direction.multiple(Utils.playerBulletSpeed);

            //Bullet bullet = new Bullet(game,this,30,shootPos.x,shootPos.y,direction,1);
            //Missile bullet = new Missile(game,this,30,5,shootPos.x,shootPos.y,direction,1);

            for (int i = 0;i < 4;i++) {
                double rotation = getRotation()+(1-2*(i%2))*Math.toRadians(10+(int) (i/2)*10);//(1-2*(i%2))　偶数であれば1,奇数であれば-1
                Vec2d missileDirection = Utils.getDirectionVector(rotation);
                missileDirection.multiple(Utils.playerBulletSpeed);
                game.addEntity(new Missile(game,this,30,2.5,getX(),getY(),missileDirection,Utils.rotatetionSpeed *1.5,5));
            }

            //game.addEntity(bullet);
            bulletCoolTime = 1;
        }
    }

    @Override
    public void attackedBy(GameEntity entity, double damage) {
        setHP(getHP()-damage);
        if (getHP() <= 0) {
            killedBy(entity);
        }
    }


    public boolean canShootBullet() {
        return bulletCoolTime <= 0;
    }


}
