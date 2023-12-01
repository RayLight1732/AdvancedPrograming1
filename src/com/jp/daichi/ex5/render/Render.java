package com.jp.daichi.ex5.render;

import com.jp.daichi.ex5.GameObject;

import java.awt.*;

/**
 * 描画用
 * 同種のエンティティには同じインスタンスが使用される
 * @param <T>
 */
public interface Render<T extends GameObject> {
    /**
     * 描画を行う
     *
     * @param g      グラフィックオブジェクト
     * @param entity エンティティ
     * @param step   0~1の値 次のtickまでどれぐらいかを表す
     */
    void render(Graphics2D g, T entity, double step);

    void loadImages();

    /**
     * エンティティの描画を特別にパーティクルと同じフェーズに行うかどうか
     * @param entity 対象のエンティティ
     * @return パーティクルと同じフェーズに行うか
     */
    boolean renderInParticlePhase(T entity);
}
