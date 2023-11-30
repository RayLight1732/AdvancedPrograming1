package com.jp.daichi.ex8painttool;

import java.awt.*;
import java.awt.event.MouseListener;

/**
 * キャンバス上のオブジェクト
 */
public interface CanvasObject {
    /**
     * 描画を行う
     * @param g グラフィックオブジェクト
     */
    void draw(Graphics2D g);

    /**
     * マウスリスナーを取得する
     * 返されるマウスリスナーは常に同じインスタンスである必要がある
     * @return マウスリスナー
     */
    MouseListener mouseListener();

    /**
     * 色を設定
     * @param color 新しい色
     */
    void setColor(Color color);
}
