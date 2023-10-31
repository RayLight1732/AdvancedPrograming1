package com.jp.daichi.ex5;

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
}
