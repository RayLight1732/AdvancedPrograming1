package com.jp.daichi.ex5;

import com.jp.daichi.ex4.AObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 描画用のパネル
 */
public class Display extends JPanel {

    private final Game game;//描画するゲーム
    public Display(Game game) {
        this.game = game;
        setDoubleBuffered(true);//ダブルバッファリング有効化
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //すべてのオブジェクトについて
        for (GameEntity e1: game.getEntities()) {
            e1.draw(g);//描画処理実行
        }
    }
}
