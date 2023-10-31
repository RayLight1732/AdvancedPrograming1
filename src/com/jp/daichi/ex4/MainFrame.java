package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame{
    public static void main(String[] args) {
        JFrame frame = new JFrame();//メイン画面
        frame.setSize(600, 600);//サイズ指定
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//×を押したらプログラムが終了するように
        PaintPanel paintPanel = new PaintPanel();//描画用パネル
        frame.add(paintPanel);//パネル追加
        MouseAdapter listener = new MouseAdapter() {//クリックしたら追加するように
            int i = 0;//何個出たか

            @Override
            public void mouseClicked(MouseEvent e) {
                AObject object;//生成するオブジェクト
                Vec2d speed = new Vec2d(Math.random()*200-100,Math.random()*200-100);//-50~50の範囲で速度を生成
                if (i == 0) {
                    //一つ目は円
                    object = new CircleObject(e.getX(), e.getY(), speed, 30);
                } else {
                    //二つ目以降は順に頂点の数を増やしていく
                    object = new RPolygonObject(e.getX(), e.getY(), 40, i + 2, 0,30/360.0*2*Math.PI,speed);
                }
                i++;
                paintPanel.addObject(object);//追加
            }
        };
        paintPanel.addMouseListener(listener);//リスナー追加

        Thread thread = new Thread(new UpdateThreadRunnable(paintPanel,0));//更新用のスレッド 50*9.8
        thread.start();//スレッドスタート

        frame.setVisible(true);//表示
    }
}
