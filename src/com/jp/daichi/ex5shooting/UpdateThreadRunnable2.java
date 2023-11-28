package com.jp.daichi.ex5shooting;

import javax.swing.*;

public class UpdateThreadRunnable2 implements Runnable {
    private static final double tickRate = 1/20.0;

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
            if (deltaTime > tickRate) {
                game.tick(deltaTime);//tick処理
                lastTime = current;//時間更新
            }
            SwingUtilities.invokeLater(updateSwing);//更新

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
