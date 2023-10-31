package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import javax.swing.*;
import java.util.List;

public class UpdateThreadRunnable implements Runnable {

    private final PaintPanel panel;
    private double gravity;

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
     */
    public UpdateThreadRunnable(PaintPanel panel,double gravity) {
        this.panel = panel;
        this.gravity = gravity;
    }

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();//前回処理が行われた時間
        while (true) {
            long current = System.currentTimeMillis();//現在の時間
            double deltaTime = (current-lastTime)/1000.0;//ミリ秒->秒に変換
            List<AObject> objects = panel.getObjects();//登録されているオブジェクトのリスト
            for (AObject object: objects) {
                object.collisionTick(deltaTime);//衝突前処理を行う
            }
            //一度衝突判定したペアは二度目はやらないようなループ
            for (int i = 0; i < objects.size();i++) {
                AObject object1 = objects.get(i);//判定対象オブジェクト1
                for (int i2 = i+1; i2 < objects.size();i2++) {
                    AObject object2 = objects.get(i2);//判定対象オブジェクト2
                    if (object1.isCollide(object2)) {
                        object1.collideWith(object2);//衝突したことを通知
                        object2.collideWith(object1);//衝突したことを通知
                    }
                }
            }

            //上下左右判定時、どれだけ余裕を持たせるか
            double margin = 400;
            //左端
            RectangleObject left = new RectangleObject(-margin,-margin, margin, panel.getHeight()+2* margin, new Vec2d(0,0));
            left.collisionTick(0);//衝突前処理
            //上端
            RectangleObject up = new RectangleObject(-margin,-margin, panel.getWidth()+2* margin, margin,new Vec2d(0,0));
            up.collisionTick(0);//衝突前処理
            //右端
            RectangleObject right = new RectangleObject(panel.getWidth(),-margin, margin, panel.getHeight()+2* margin,new Vec2d(0,0));
            right.collisionTick(0);//衝突前処理
            //下端
            RectangleObject down = new RectangleObject(-margin,panel.getHeight(), panel.getWidth()+2* margin, margin,new Vec2d(0,0));
            down.collisionTick(0);//衝突前処理

            for (AObject object:objects) {//すべてのオブジェクトについて
                object.tick(deltaTime);//更新処理
                object.setVecY(object.getVecY()+gravity*deltaTime);
                //x座標が枠からはみ出た場合
                if (left.isCollide(object)) {//左端を超えていた時
                    object.setVecX(Math.abs(object.getVecX()));//強制的に内側を向くように
                } else if (right.isCollide(object)) {//右端を超えていた時
                    object.setVecX(-Math.abs(object.getVecX()));//強制的に内側を向くように
                }
                if (up.isCollide(object)) {//上端を超えていた時
                    object.setVecY(Math.abs(object.getVecY()));//強制的に下側を向くように
                } else if (down.isCollide(object)) {//下端を超えていた時
                    object.setVecY(-Math.abs(object.getVecY())*0.9);//強制的に上側を向くように
                }
                //※符号反転だけだとウィンドウサイズが変わったときや、速度が非常に小さいときに対応できないため
            }

            SwingUtilities.invokeLater(updateSwing);//更新
            try {
                Thread.sleep(Math.max(1000/60-(current-lastTime),0));//待機 0以下にはならないように
                lastTime = current;//時間更新
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
