package com.jp.daichi.ex4;

import com.jp.daichi.ex5.Utils;
import com.sun.javafx.geom.Vec2d;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 更新用のスレッド
 */
public class UpdateThreadRunnable implements Runnable {

    private final int maxLoop = 100;//判定時、どれだけのループを行うか
    private final PaintPanel panel;
    private double gravity;
    private double e;

    //invokelater用のrunnable
    private final Runnable updateSwing = new Runnable() {
        @Override
        public void run() {
            //再描画
            panel.repaint();
        }
    };

    /**
     * 更新用のスレッド
     * @param panel 描画用のパネル
     * @param gravity 重力
     * @param e 反発係数
     */
    public UpdateThreadRunnable(PaintPanel panel,double gravity,double e) {
        this.panel = panel;
        this.gravity = gravity;
        this.e = e;
        //上下左右判定時、どれだけ余裕を持たせるか
    }

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();//前回処理が行われた時間
        while (true) {
            long current = System.currentTimeMillis();//現在の時間
            double deltaTime = (current-lastTime)/1000.0;//ミリ秒->秒に変換
            List<AObject> objects = panel.getObjects();//登録されているオブジェクトのリスト

            double margin = 400;//壁の余裕
            //壁追加
            objects.add(new WallObject(-margin,-margin, margin, panel.getHeight()+2* margin));
            objects.add(new WallObject(-margin,-margin, panel.getWidth()+2* margin, margin));
            objects.add(new WallObject(panel.getWidth(),-margin, margin, panel.getHeight()+2* margin));
            objects.add(new WallObject(-margin,panel.getHeight(), panel.getWidth()+2* margin, margin,e));//下

            boolean collide;//衝突したか
            int loopCount = 0;//ループした回数

            Set<AObject> hitObjectSet = new HashSet<>();//衝突が起こったオブジェクト収納用セット
            do {
                hitObjectSet.clear();
                collide = false;
                for (AObject object: objects) {
                    object.collisionTick(deltaTime);//衝突前処理を行う
                }
                for (int i = 0; i < objects.size();i++) {
                    AObject object1 = objects.get(i);//判定対象オブジェクト1
                     for (int i2 = i+1; i2 < objects.size();i2++) {
                        AObject object2 = objects.get(i2);//判定対象オブジェクト2
                        if (object1 instanceof WallObject && object2 instanceof WallObject) {
                            continue;
                        }
                        Vec2d hitVec = object1.isCollide(object2);//衝突したか
                        if (hitVec != null) {
                            collide = true;
                            object1.collideWith(object2,deltaTime,hitVec.x, hitVec.y);//衝突したことを通知
                            object2.collideWith(object1,deltaTime,hitVec.x,hitVec.y);//衝突したことを通知
                            loopCount++;
                            hitObjectSet.add(object1);
                            hitObjectSet.add(object2);
                        }
                    }
                }
            } while (collide && loopCount < maxLoop);
            if (loopCount >= maxLoop) {
                System.out.println("over loop");
                //最後まで残っていたらめり込んでいる可能性がある(もしくは衝突回数が多すぎる)どちらにせよ邪魔なので消す
                for (AObject aObject:hitObjectSet) {
                    if (!(aObject instanceof WallObject)) {
                        panel.removeObject(aObject);
                    }
                }
            }


            for (AObject object:objects) {//すべてのオブジェクトについて
                object.tick(deltaTime);//更新処理
                object.setVecY(object.getVecY()+gravity*deltaTime);
            }

            SwingUtilities.invokeLater(updateSwing);//更新
            lastTime = current;//時間更新


            try {
                //Thread.sleep(Math.max(1000/60-(current-lastTime),0));//待機 0以下にはならないように
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * 重力加速度を設定
     * @param gravity 重力加速度
     */
    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    /**
     * 重力加速度を取得
     * @return 重力加速度
     */
    public double getGravity() {
        return gravity;
    }

    /**
     * 床の反発係数を設定
     * @param e 床の反発係数
     */
    public void setE(double e) {
        this.e = e;
    }

    /**
     * 床の反発係数を取得
     * @return 床の反発係数
     */
    public double getE() {
        return e;
    }
}
