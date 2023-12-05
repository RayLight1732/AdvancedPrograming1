package com.jp.daichi.ex8.canvasobject;

import com.jp.daichi.ex8.Serializable2;

import java.awt.*;
import java.io.Serializable;

/**
 * キャンバス上のオブジェクト
 */
public interface CanvasObject extends Serializable2 {
    /**
     * 描画を行う
     * @param g グラフィックオブジェクト
     */
    void draw(Graphics2D g);

    /**
     * 色を設定
     * @param color 新しい色
     */
    void setColor(Color color);
}
