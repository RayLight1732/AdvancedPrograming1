package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import javax.swing.*;
import java.util.List;

/**
 * 更新用のスレッド
 */
public class UpdateThreadRunnable implements Runnable {

    private final PaintPanel panel;
    private final double gravity;
    private final double e;

    //invokelater用のrunnable
    private final Runnable updateSwing = new Runnable() {
        @Override
        public void run() {
            //再描画
            panel.validate();
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

            double margin = 400;
            objects.add(new WallObject(-margin,-margin, margin, panel.getHeight()+2* margin));
            objects.add(new WallObject(-margin,-margin, panel.getWidth()+2* margin, margin));
            objects.add(new WallObject(panel.getWidth(),-margin, margin, panel.getHeight()+2* margin));
            objects.add(new WallObject(-margin,panel.getHeight(), panel.getWidth()+2* margin, margin,e));//下

            /*
            //一度衝突判定したペアは二度目はやらないようなループ
            for (int i = 0; i < objects.size();i++) {
                AObject object1 = objects.get(i);//判定対象オブジェクト1
                for (int i2 = i+1; i2 < objects.size();i2++) {
                    AObject object2 = objects.get(i2);//判定対象オブジェクト2
                    if (object1.isCollide(object2)) {
                        object1.collideWith(object2,deltaTime);//衝突したことを通知
                        object2.collideWith(object1,deltaTime);//衝突したことを通知
                    }
                }
            }*/
            boolean collide = false;
            int loopCount = 0;
            do {
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
                        Vec2d hitVec = object1.isCollide(object2);
                        if (hitVec != null) {
                            collide = true;
                            object1.collideWith(object2,deltaTime,hitVec.x, hitVec.y);//衝突したことを通知
                            object2.collideWith(object1,deltaTime,hitVec.x,hitVec.y);//衝突したことを通知
                        }
                    }
                }
                loopCount++;
            } while (collide && loopCount < 100);
            if (loopCount >= 100) {
                System.out.println("over loop");
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
}
