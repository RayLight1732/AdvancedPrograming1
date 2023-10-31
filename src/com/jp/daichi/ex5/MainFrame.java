package com.jp.daichi.ex5;

import javax.swing.*;

public class MainFrame{
    public static KeyBind keyBind;


    public static void main(String[] args) {
        JFrame frame = new JFrame();//メイン画面
        frame.setSize(600, 600);//サイズ指定
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//×を押したらプログラムが終了するように

        SimpleGame game = new SimpleGame();
        Display display = new Display(game);//描画用パネル
        game.setPanel(display);
        frame.add(display);//パネル追加
        keyBind = new KeyBind(display);
        Player player = new Player(game,display,300,300,50);
        Enemy enemy = new TurretEnemy(game,500,300,30,player);
        game.addEntity(player);
        game.addEntity(enemy);
        frame.setVisible(true);//表示
        Thread thread = new Thread(new UpdateThreadRunnable2(game,display));//更新用のスレッド 50*9.8
        thread.start();//スレッドスタート
        display.requestFocus();
    }
}