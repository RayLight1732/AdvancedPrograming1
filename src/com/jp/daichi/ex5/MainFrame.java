package com.jp.daichi.ex5;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.enemy.BeamTurretEnemy;
import com.jp.daichi.ex5.enemy.HomingExplosionTurret;
import com.jp.daichi.ex5.enemy.ThickBeamTurret;
import com.jp.daichi.ex5.enemy.TurretEnemy;
import com.jp.daichi.ex5.stage.SlideSpawnStage;
import com.jp.daichi.ex5.stage.Stage;
import com.jp.daichi.ex5.stage.StageFlow;
import com.jp.daichi.ex5.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;

public class MainFrame{
    public static KeyBind keyBind;


    public static void main(String[] args) {
        JFrame frame = new JFrame();//メイン画面
        frame.setSize(600, 600);//サイズ指定
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//×を押したらプログラムが終了するように

        SimpleGame game = new SimpleGame();
        Display display = new Display(game);//描画用パネル
        frame.setLayout(null);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                float widthRatio = (float) frame.getContentPane().getWidth()/game.getWidth();
                float heightRatio = (float) frame.getContentPane().getHeight()/game.getHeight();
                float ratio = Math.min(widthRatio,heightRatio);
                float xOffset = (frame.getContentPane().getWidth()-game.getWidth()*ratio)/2;
                float yOffset = (frame.getContentPane().getHeight()-game.getHeight()*ratio)/2;
                display.setBounds(Math.round(xOffset),Math.round(yOffset),Math.round(game.getWidth()*ratio),Math.round(game.getHeight()*ratio));
                display.setRatio(ratio);
            }
        });
        frame.add(display);//パネル追加
        frame.getContentPane().setBackground(Color.BLACK);
        keyBind = new KeyBind(display);
        Player player = new Player(game,300,300,30);
        //Stage stage = new FirstStage(player);
        SlideSpawnStage.TurretEnemyFactory factory1 = (_game,x,y,vec2d)-> new BeamTurretEnemy(_game,x,y,30,5,null,vec2d);
        Stage stage1 = new SlideSpawnStage(player, Arrays.asList(factory1,factory1,factory1,factory1),Utils.playerSpeed*0.3,Utils.playerSpeed*0.5,1.5);
        SlideSpawnStage.TurretEnemyFactory factory2 = (_game,x,y,vec2d)-> new HomingExplosionTurret(_game,x,y,30,10,null,vec2d);
        Stage stage2 = new SlideSpawnStage(player, Arrays.asList(factory2,factory2,factory2,factory2),Utils.playerSpeed*0.3,Utils.playerSpeed*0.5,1.5);
        SlideSpawnStage.TurretEnemyFactory factory3 = (_game,x,y,vec2d)-> new ThickBeamTurret(_game,x,y,10,30);
        Stage stage3 = new SlideSpawnStage(player, Arrays.asList(factory3,factory3,factory3,factory3),Utils.playerSpeed*0.3,0,1.5);

        game.setStageFlow(StageFlow.StageFlowFactory.createInstance().add(stage1).add(stage2).add(stage3).create());
        game.addEntity(player);
        frame.setVisible(true);//表示
        Thread thread = new Thread(new UpdateThreadRunnable2(game,display));//更新用のスレッド 50*9.8
        thread.start();//スレッドスタート
        display.requestFocus();
    }
}