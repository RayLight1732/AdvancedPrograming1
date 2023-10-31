package com.jp.daichi.ex4;

import java.awt.*;

/**
 * 色付きを判別
 */
public interface Colored {

    /**
     * 色を取得
     * @return 描画色
     */
    Color getColor();

    /**
     * 描画色を指定
     * @param color 描画色
     */
    void setColor(Color color);

    /**
     * 輪郭線の色を取得
     * @return 輪郭線の色
     */
    Color getOutLineColor();

    /**
     * 輪郭線の色を取得
     * @param color 輪郭線の色
     */
    void setOutLineColor(Color color);

}
