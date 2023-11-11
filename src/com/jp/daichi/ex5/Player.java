package com.jp.daichi.ex5;

import com.jp.daichi.ex4.PathObject;
import com.jp.daichi.ex4.RotationalObject;
import com.sun.javafx.geom.Vec2d;

import javax.swing.*;
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
    private final JPanel panel;
    public Player(Game game,JPanel panel,double x, double y, double size){
        super(game,20,size,getPlayerShape(size,x,y,new Vec2d()));
        this.panel = panel;
        MainFrame.keyBind.addKeyBind(KeyEvent.VK_W);//キーを登録
        MainFrame.keyBind.addKeyBind(KeyEvent.VK_A);//キーを登録
        MainFrame.keyBind.addKeyBind(KeyEvent.VK_S);//キーを登録
        MainFrame.keyBind.addKeyBind(KeyEvent.VK_D);//キーを登録
        MainFrame.keyBind.addKeyBind(KeyEvent.VK_SPACE);//キーを登録
    }

    @Override
    public boolean doCollision(GameEntity entity) {
        return false;
    }

    @Override
    public int getCollisionPriority() {
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
            Vec2d direction = new Vec2d(rotateTargetX-getX(),rotateTargetY-getY());//マウスポインタへの方向ベクトル
            if (Utils.getLength(direction) > 0) {//長さが0以上の時
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
                    delta = Math.signum(delta)*Utils.rotateSpeed*deltaTime;//limitに制限
                }
                setRotation(getRotation()+delta);//角度更新
            }
        } else {
            if (MainFrame.keyBind.isPressed(KeyEvent.VK_A) && !MainFrame.keyBind.isPressed(KeyEvent.VK_D)) {//wキーが押されて、sキーが押されていないとき
                setRotation(getRotation() - deltaTime * Utils.rotateSpeed);
            } else if (!MainFrame.keyBind.isPressed(KeyEvent.VK_A) && MainFrame.keyBind.isPressed(KeyEvent.VK_D)) {//sキーが押されて、wキーが押されていないとき
                setRotation(getRotation() + deltaTime * Utils.rotateSpeed);
            }
        }

        Vec2d vec2d = getVector();
        double vecLength = Utils.getLength(vec2d);//ベクトルの長さ取得
        double deltaLength = Utils.playerSpeedStep*deltaTime;//一秒間にplayerSpeedStepだけ変化するとき、deltaTime秒で変化する量
        if (MainFrame.keyBind.isPressed(KeyEvent.VK_W) && !MainFrame.keyBind.isPressed(KeyEvent.VK_S)) {//wキーが押されて、sキーが押されていないとき
            vecLength = Math.min(vecLength+deltaLength,Utils.playerSpeed);//最大値設定
        } else if (!MainFrame.keyBind.isPressed(KeyEvent.VK_W) && MainFrame.keyBind.isPressed(KeyEvent.VK_S)) {//sキーが押されて、wキーが押されていないとき
            vecLength = Math.max(vecLength-deltaLength,0);//最小値0
        }

        Vec2d direction = Utils.getDirection(this);//方向ベクトル取得
        Utils.multiple(direction,vecLength);//長さ指定
        setVector(direction);//ベクトル設定

        if (MainFrame.keyBind.isPressed(KeyEvent.VK_SPACE) && canShootBullet()) {
            direction = Utils.getDirection(this);
            Vec2d shootPos = new Vec2d(getX()+direction.x*size,getY()+direction.y*size);
            Utils.multiple(direction,Utils.playerBulletSpeed);

            //Bullet bullet = new Bullet(game,this,30,shootPos.x,shootPos.y,direction,1);
            //Missile bullet = new Missile(game,this,30,5,shootPos.x,shootPos.y,direction,1);

            for (int i = 0;i < 4;i++) {
                double rotation = getRotation()+(1-2*(i%2))*Math.toRadians(10+(int) (i/2)*10);//(1-2*(i%2))　偶数であれば1,奇数であれば-1
                Vec2d missileDirection = Utils.getDirection(rotation);
                Utils.multiple(missileDirection,Utils.playerBulletSpeed);
                game.addEntity(new Missile(game,this,30,2.5,getX(),getY(),missileDirection,Utils.rotateSpeed*1.5,0.5));
            }

            //game.addEntity(bullet);
            bulletCoolTime = 1;
        }
    }

    @Override
    public void attackedBy(GameEntity entity, double damage) {
        //TODO
    }

    @Override
    public void kill() {
        super.kill();
        System.out.println("Killed");
    }

    public boolean canShootBullet() {
        return bulletCoolTime <= 0;
    }


}
