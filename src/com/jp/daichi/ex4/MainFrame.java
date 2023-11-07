package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

/**
 * メインクラス
 */
public class MainFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();//メイン画面
        frame.setSize(600, 600);//サイズ指定
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//×を押したらプログラムが終了するように
        PaintPanel paintPanel = new PaintPanel();//描画用パネル
        frame.add(paintPanel);//追加

        JPanel sliderPanel = new JPanel();//スライダー配置用
        sliderPanel.setOpaque(false);//背景透過
        sliderPanel.setLayout(null);//レイアウトマネージャー削除
        JSlider slider = new JSlider(0,(int)(9.8*50*10),(int)(9.8*50));//重力用スライダー
        slider.setBounds(new Rectangle(0,0,200,25));//位置、幅設定
        sliderPanel.add(slider);//追加
        slider.setOpaque(false);//背景透過
        JSlider slider2 = new JSlider(0,100,90);//反発係数用スライダー
        slider2.setBounds(new Rectangle(0,25,200,25));//位置、幅設定
        slider2.setOpaque(false);//背景透過
        sliderPanel.add(slider2);//追加
        frame.setGlassPane(sliderPanel);//GlassPaneを設定

        MouseAdapter listener = new MouseAdapter() {//クリックしたら追加するように
            int i = 0;//何個出たか
            @Override
            public void mouseClicked(MouseEvent e) {
                AObject object;//生成するオブジェクト
                Vec2d speed = new Vec2d(Math.random() * 200 - 100, Math.random() * 200 - 100);//-50~50の範囲で速度を生成
                if (i == 0) {
                    //一つ目は円
                    object = new CircleObject(e.getX(), e.getY(), speed, 30);
                } else {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        //二つ目以降は順に頂点の数を増やしていく
                        object = new RPolygonObject(e.getX(), e.getY(), 40, i + 2, Math.toRadians(90 - 180.0 / (i + 2)), 0, speed);
                    } else {
                        //右クリックした際は中心からの距離がランダムな５角形を生成
                        object = new PathObject(e.getX(),e.getY(),0,speed,createPolygon(5,40,0));
                    }
                }
                i++;
                paintPanel.addObject(object);//追加
            }
        };
        paintPanel.addMouseListener(listener);//リスナー追加

        UpdateThreadRunnable runnable = new UpdateThreadRunnable(paintPanel, 50 * 9.8, 0.9);
        Thread thread = new Thread(runnable);//更新用のスレッド 50*9.8
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();//スレッドスタート

        slider.addChangeListener(e -> runnable.setGravity(slider.getValue()));
        slider2.addChangeListener(e -> runnable.setE(slider2.getValue()/100.0));


        frame.setVisible(true);//表示
        sliderPanel.setVisible(true);

    }

    private static Path2D createPolygon(int nPoints, double radius, double rotation) {
        Path2D path = new Path2D.Double();//パス
        double random = Math.random()*2*radius;//ランダム初期化
        path.moveTo(random*Math.cos(rotation),radius*Math.sin(rotation));//移動
        double deltaRotation = 2*Math.PI/nPoints;
        for (int i = 1;i < nPoints;i++) {
            random = Math.random()*2*radius;
            path.lineTo(random*Math.cos(rotation+deltaRotation*i),random*Math.sin(rotation+deltaRotation*i));
        }
        path.closePath();
        return path;
    }
}
