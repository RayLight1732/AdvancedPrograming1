package com.jp.daichi.ex5;

import com.jp.daichi.ex5.base.bullet.HomingExplosion;
import com.jp.daichi.ex5.base.bullet.ThickBeam;
import com.jp.daichi.ex5.base.enemy.*;
import com.jp.daichi.ex5.particles.Charge;
import com.jp.daichi.ex5.particles.Charge2;
import com.jp.daichi.ex5.particles.RoundParticle;
import com.jp.daichi.ex5.render.*;
import com.jp.daichi.ex5.stage.*;
import com.jp.daichi.ex5.utils.ResourceManager;
import com.jp.daichi.ex5.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainFrame {
    public static KeyBind keyBind;
    public static String backgroundImage = "background.png";


    public static void main(String[] args) {
        JFrame frame = new JFrame();//メイン画面
        frame.setSize(600, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);//サイズ指定

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//×を押したらプログラムが終了するように

        SimpleGame game = new SimpleGame();//ゲームのインスタンス生成
        Display display = new Display(game);//描画用パネル
        registerRender();
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
        frame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                display.requestFocus();
            }
        });
        frame.add(display);//パネル追加
        frame.getContentPane().setBackground(Color.BLACK);
        keyBind = new KeyBind(display);
        Player player = new Player(game, game.getWidth() / 2.0, game.getHeight() / 2.0, 30);
        game.setPlayer(player);
        //*
        List<SlideSpawnInfo> infoList = new ArrayList<>();

        SlideSpawnStage.TurretEnemyFactory factory1 = (_game, x, y) -> new BeamTurretEnemy(_game, null, x, y, 30, 5);
        infoList.add(new SlideSpawnInfo(factory1, Utils.playerSpeed * 0.3, Utils.playerSpeed * 0.5, 1.5));
        Stage stage1 = new SlideSpawnStage(player, infoList.get(0));
        SlideSpawnStage.TurretEnemyFactory factory2 = (_game, x, y) -> new HomingExplosionTurret(_game, null, x, y, 40, 10, 20);
        infoList.add(new SlideSpawnInfo(factory2, Utils.playerSpeed * 0.1, Utils.playerSpeed * 0.2, 1.5));
        Stage stage2 = new SlideSpawnStage(player, infoList.get(1));
        SlideSpawnStage.TurretEnemyFactory factory3 = (_game, x, y) -> new ThickBeamTurret(_game, null, x, y, 50, 10);
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
        game.setStageFlow(StageFlow.StageFlowFactory.createInstance().add(stage1).add(stage2).addAll(stages).add(new EndlessStage(player, infoList)).create());
        //*/
        /*
        game.setStageFlow(StageFlow.StageFlowFactory.createInstance().add(new EndlessStage(player,Collections.singletonList(
                new SlideSpawnInfo((_game,x,y,vec2d)->{
                    NoAITurret turret = new NoAITurret(_game,x,y,20,10,player);
                    turret.setMaxSpeed(Utils.playerSpeed * 0.5);
                    turret.setRotation(Utils.getRotation(vec2d));
                    return turret;
                    },Utils.playerSpeed * 0.3, Utils.playerSpeed * 0.5, 1.5)))).create());
        */
        //game.setStageFlow(StageFlow.StageFlowFactory.createInstance().add(new SlideSpawnStage(player, Arrays.asList((_game,x,y)-> new ThickBeamTurret(_game,null, x, y, 50, 10),null,null,null),0,0,1)).create());

        game.addEntity(player);
        frame.setVisible(true);//表示
        loadImage(game);
        Thread thread = new Thread(new UpdateThreadRunnable2(game, display));//更新用のスレッド 50*9.8
        thread.start();//スレッドスタート
        display.requestFocus();
    }

    private static void registerRender() {
        RenderManager.registerRender(AGameEntity.class, OldDefaultRender::new);
        RenderManager.registerRender(ThickBeam.class, TestRender::new);
        RenderManager.registerRender(RoundParticle.class, RoundParticleRender::new);
        RenderManager.registerRender(Charge.class, ChargeRender::new);
        RenderManager.registerRender(HomingExplosion.class, HomingExplosionRender::new);
        RenderManager.registerRender(Charge2.class,ChargeRender2::new);
        RenderManager.registerRender(ThickBeam.class,ThickBeamRender::new);
    }

    private static void loadImage(Game game) {
        ResourceManager.loadImage(backgroundImage, bi -> {
            double scale = Math.max((double) game.getWidth() / bi.getWidth(), (double) game.getHeight() / bi.getHeight());
            return bi.getScaledInstance((int) (bi.getWidth() * scale) + 10, (int) (bi.getHeight() * scale) + 10, Image.SCALE_AREA_AVERAGING);
        });
    }

}