package com.jp.daichi.ex5;

import com.jp.daichi.ex5.particles.Particle;
import com.jp.daichi.ex5.stage.Stage;
import com.jp.daichi.ex5.stage.StageFlow;

import java.awt.*;
import java.util.List;

public interface Game {
    /**
     * 登録されているゲームエンティティのリストのコピーを取得
     * @return 登録されているゲームエンティティのリストのコピー
     */
    List<GameEntity> getEntities();

    /**
     * ゲームエンティティをゲームから削除
     * @param entity 削除するエンティティ
     * @return 削除に成功したか
     */
    boolean removeEntity(GameEntity entity);

    /**
     * ゲームエンティティをゲームに登録
     * @param entity 登録するエンティティ
     */
    void addEntity(GameEntity entity);

    /**
     * ステートを取得
     * @return ステート
     */
    GameState getState();

    /**
     * ティック処理
     * @param deltaTime 前回ティックからどれだけ経過したか(秒)
     */
    void tick(double deltaTime);

    /**
     * 横幅を取得
     * @return 横幅
     */
    int getWidth();

    /**
     * 高さを取得
     * @return 高さ
     */
    int getHeight();

    /**
     * パーティクルを追加
     * @param particle 追加するパーティクル
     */
    void addParticle(Particle particle);

    /**
     * パーティクルのリストのコピーを取得
     * @return 登録されているパーティクルのリストのコピー
     */
    List<Particle> getParticles();

    void drawEntity(Graphics2D g);

    void drawParticle(Graphics2D g);

    void setStageFlow(StageFlow stageFlow);
}
