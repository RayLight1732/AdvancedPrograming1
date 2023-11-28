package com.jp.daichi.ex5shooting;

import com.jp.daichi.ex5shooting.particles.Particle;
import com.jp.daichi.ex5shooting.stage.StageFlow;

import java.util.List;

public interface Game {
    /**
     * 登録されているゲームエンティティのリストのコピーを取得
     * @return 登録されているゲームエンティティのリストのコピー
     */
    List<GameEntity> getEntities();

    /**
     * ゲームエンティティをゲームから削除するようマークする。
     * @param entity 削除するエンティティ
     */
    void removeEntity(GameEntity entity);

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


    void setStageFlow(StageFlow stageFlow);

    LivingEntity getPlayer();

    void killedBy(GameEntity victim,GameEntity attacker);

    int getScore();

    void setScore(int score);

}
