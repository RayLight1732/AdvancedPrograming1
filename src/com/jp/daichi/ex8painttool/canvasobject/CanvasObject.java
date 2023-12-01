package com.jp.daichi.ex8painttool.canvasobject;

import java.awt.*;
import java.io.Serializable;

/**
 * キャンバス上のオブジェクト
 */
public interface CanvasObject extends Serializable {
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
