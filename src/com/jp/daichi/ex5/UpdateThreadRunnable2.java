package com.jp.daichi.ex5;

import javax.swing.*;
import java.util.List;

public class UpdateThreadRunnable2 implements Runnable {

    private final Game game;
    private final Display panel;

    //invokelater用のrunnable
    private final Runnable updateSwing = new Runnable() {
        @Override
        public void run() {
            //再描画
            panel.validate();
            panel.repaint();
        }
    };

    public UpdateThreadRunnable2(Game game,Display panel) {
        this.game = game;//処理対象のゲーム
        this.panel = panel;//描画用パネル
    }

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();//前回処理が行われた時間
        while (true) {
            long current = System.currentTimeMillis();//現在の時間
            double deltaTime = (current - lastTime) / 1000.0;//ミリ秒->秒に変換
            game.tick(deltaTime);//tick処理
            SwingUtilities.invokeLater(updateSwing);//更新
            try {
                Thread.sleep(Math.max(1000 / 60 - (current - lastTime), 0));//待機 0以下にはならないように
                lastTime = current;//時間更新

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
