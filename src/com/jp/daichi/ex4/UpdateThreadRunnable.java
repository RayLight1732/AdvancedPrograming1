package com.jp.daichi.ex4;

import com.jp.daichi.ex5.Utils;
import com.sun.javafx.geom.Vec2d;

import javax.swing.*;
import java.util.*;

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
            double deltaTime = (current - lastTime) / 1000.0;//ミリ秒->秒に変換
            List<AObject> objects = panel.getObjects();//登録されているオブジェクトのリスト

            double margin = 400;//壁の余裕
            //壁追加
            objects.add(new WallObject(-margin, -margin, margin, panel.getHeight() + 2 * margin));
            objects.add(new WallObject(-margin, -margin, panel.getWidth() + 2 * margin, margin));
            objects.add(new WallObject(panel.getWidth(), -margin, margin, panel.getHeight() + 2 * margin));
            objects.add(new WallObject(-margin, panel.getHeight(), panel.getWidth() + 2 * margin, margin, e));//下

            objects.forEach(o -> {
                o.setTickState(TickState.InnerCheckTick);
                o.collisionTick(deltaTime);
            });
            Set<AObject> inner = new HashSet<>();
            doubleLoop(objects, ((o1, o2) -> {
                if (o1 instanceof WallObject && o2 instanceof WallObject) {
                    return;
                }
                Vec2d hitVec = o1.isCollide(o2);
                if (hitVec != null) {
                    inner.add(o1);
                    inner.add(o2);
                    o1.collideWith(o2, deltaTime, hitVec.x, hitVec.y, true);//衝突したことを通知
                    o2.collideWith(o1, deltaTime, hitVec.x, hitVec.y, true);//衝突したことを通知
                }
            }));

            objects.forEach(o -> o.setTickState(TickState.CollisionTick));

            boolean collide;//衝突したか
            int loopCount = 0;//ループした回数

            do {
                collide = false;
                objects.forEach(o -> o.collisionTick(deltaTime));//衝突前処理
                for (int i = 0; i < objects.size(); i++) {
                    AObject object1 = objects.get(i);//判定対象オブジェクト1
                    for (int i2 = i + 1; i2 < objects.size(); i2++) {
                        AObject object2 = objects.get(i2);//判定対象オブジェクト2
                        if (inner.contains(object1) && inner.contains(object2)) {
                            continue;
                        }
                        if (object1 instanceof WallObject && object2 instanceof WallObject) {
                            continue;
                        }
                        Vec2d hitVec = object1.isCollide(object2);//衝突したか
                        if (hitVec != null) {
                            collide = true;
                            loopCount++;
                            object1.collideWith(object2, deltaTime, hitVec.x, hitVec.y, false);//衝突したことを通知
                            object2.collideWith(object1, deltaTime, hitVec.x, hitVec.y, false);//衝突したことを通知
                        }
                    }
                }

            } while (collide && loopCount < maxLoop);
            if (loopCount >= maxLoop) {
                System.out.println("over loop");
            }

            objects.forEach(o->{
                o.setTickState(TickState.ProcessTick);
                o.tick(deltaTime);
                o.setVecY(o.getVecY()+gravity*deltaTime);
            });

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

    public void doubleLoop(List<AObject> objects,TwoObjectRunnable runnable) {
        for (int i = 0; i < objects.size();i++) {
            AObject object1 = objects.get(i);//判定対象オブジェクト1
            for (int i2 = i + 1; i2 < objects.size(); i2++) {
                AObject object2 = objects.get(i2);//判定対象オブジェクト2
                runnable.run(object1,object2);
            }
        }
    }

    public interface TwoObjectRunnable {
        void run(AObject o1,AObject o2);
    }
}
