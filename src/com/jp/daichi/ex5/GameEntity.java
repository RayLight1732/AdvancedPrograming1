package com.jp.daichi.ex5;

import com.jp.daichi.ex4.Vec2d;
import java.awt.*;
import java.awt.geom.Area;

/**
 * ゲーム内のエンティティ
 */
public interface GameEntity extends GameObject {

    /**
     * ほかのエンティティと衝突するか
     * @param entity 対象のエンティティ
     * @return 衝突するか
     */
    boolean doCollision(GameEntity entity);

    /**
     * 衝突に関するルールの優先度を取得
     * 数字が大きいほうが優先度が高い
     * @return 衝突に関するルールの優先度
     */
    int getCollisionRulePriority();

    /**
     * ほかのエンティティと衝突したとき
     * @param entity 衝突した相手
     */
    void collideWith(GameEntity entity);

    /**
     * 衝突前処理を行う
     * @param deltaTime 前回からの経過時間
     */
    void collisionTick(double deltaTime);

    /**
     * ティック処理を行う
     * @param deltaTime 前回からの経過時間
     */
    void tick(double deltaTime);

    /**
     * ベクトルを設定
     * @param vec ベクトル
     */
    void setVector(Vec2d vec);

    /**
     * ベクトルを取得
     * @return ベクトル
     */
    Vec2d getVector();

    /**
     * 対象のエンティティと衝突しているか
     * @param entity 対象のエンティティ
     * @return 衝突しているならtrue
     */
    boolean isCollide(GameEntity entity);

    /**
     * 描画を行う
     * @param g グラフィックオブジェクト
     */
    @Deprecated
    void draw(Graphics2D g);

    /**
     * 当たり判定があるエリアを取得
     * @return エリア
     */
    Area getArea();


    Game getGame();

    void onRemoved();

    /**
     * 対象のエンティティを攻撃できるか
     * @param entity 対象のエンティティ
     * @return このエンティティが対象のエンティティを攻撃できるならtrue
     */
    boolean canAttack(GameEntity entity);


}
