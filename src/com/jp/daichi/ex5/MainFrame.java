package com.jp.daichi.ex5;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.enemy.BeamTurretEnemy;
import com.jp.daichi.ex5.enemy.HomingExplosionTurret;
import com.jp.daichi.ex5.enemy.ThickBeamTurret;
import com.jp.daichi.ex5.enemy.TurretEnemy;
import com.jp.daichi.ex5.stage.*;
import com.jp.daichi.ex5.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
                float widthRatio = (float) frame.getContentPane().getWidth() / game.getWidth();
                float heightRatio = (float) frame.getContentPane().getHeight() / game.getHeight();
                float ratio = Math.min(widthRatio, heightRatio);
                float xOffset = (frame.getContentPane().getWidth() - game.getWidth() * ratio) / 2;
                float yOffset = (frame.getContentPane().getHeight() - game.getHeight() * ratio) / 2;
                display.setBounds(Math.round(xOffset), Math.round(yOffset), Math.round(game.getWidth() * ratio), Math.round(game.getHeight() * ratio));
                display.setRatio(ratio);
            }
        });
        frame.add(display);//パネル追加
        frame.getContentPane().setBackground(Color.BLACK);
        keyBind = new KeyBind(display);
        Player player = new Player(game, 300, 300, 30);
        game.setPlayer(player);
        //Stage stage = new FirstStage(player);
        List<SlideSpawnInfo> infoList = new ArrayList<>();

        SlideSpawnStage.TurretEnemyFactory factory1 = (_game, x, y, vec2d) -> new BeamTurretEnemy(_game, x, y, 30, 5, null, vec2d);
        infoList.add(new SlideSpawnInfo(factory1, Utils.playerSpeed * 0.3, Utils.playerSpeed * 0.5, 1.5));
        Stage stage1 = new SlideSpawnStage(player, infoList.get(0));
        SlideSpawnStage.TurretEnemyFactory factory2 = (_game, x, y, vec2d) -> new HomingExplosionTurret(_game, x, y, 40, 10, null, vec2d);
        infoList.add(new SlideSpawnInfo(factory2, Utils.playerSpeed * 0.1, Utils.playerSpeed * 0.2, 1.5));
        Stage stage2 = new SlideSpawnStage(player, infoList.get(1));
        SlideSpawnStage.TurretEnemyFactory factory3 = (_game, x, y, vec2d) -> new ThickBeamTurret(_game, x, y, 10, 50, vec2d);
        double thickBeamStartSpeed = Utils.playerSpeed * 0.1;
        double thickBeamEndSpeed = 0;
        double thickBeamEndSlideTime = 5;
        infoList.add(new SlideSpawnInfo(factory3, thickBeamStartSpeed, thickBeamEndSpeed, thickBeamEndSlideTime));
        List<Stage> stages = new ArrayList<>(Collections.nCopies(4, null));
        for (int i = 0; i < 4; i++) {
            List<SlideSpawnStage.TurretEnemyFactory> factories = new ArrayList<>(Collections.nCopies(4, null));
            factories.set(i, factory3);
            stages.set(i, new SlideSpawnStage(player, factories, thickBeamStartSpeed, thickBeamEndSpeed, thickBeamEndSlideTime));
        }
        game.setStageFlow(StageFlow.StageFlowFactory.createInstance().add(stage1).add(stage2).addAll(stages).add(new EndlessStage(player,infoList)).create());
        //game.setStageFlow(StageFlow.StageFlowFactory.createInstance().add(new EndlessStage(player,infoList)).create());

        game.addEntity(player);
        frame.setVisible(true);//表示
        Thread thread = new Thread(new UpdateThreadRunnable2(game, display));//更新用のスレッド 50*9.8
        thread.start();//スレッドスタート
        display.requestFocus();
    }
}