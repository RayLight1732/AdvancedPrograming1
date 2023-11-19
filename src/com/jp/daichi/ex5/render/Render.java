package com.jp.daichi.ex5.render;

import com.jp.daichi.ex5.GameEntity;

import java.awt.*;

/**
 * 描画用
 * 同種のエンティティには同じインスタンスが使用される
 * @param <T>
 */
public interface Render<T extends GameEntity> {
    /**
     * 描画を行う
     *
     * @param g      グラフィックオブジェクト
     * @param entity エンティティ
     * @param step   0~1の値 次のtickまでどれぐらいかを表す
     */
    void render(Graphics2D g, T entity, double step);
}
