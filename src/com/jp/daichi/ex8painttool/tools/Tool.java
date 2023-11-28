package com.jp.daichi.ex8painttool.tools;

import java.awt.*;

/**
 * 描画用ツール
 */
public interface Tool {
    /**
     * 現在の状態で描画する
     * @param g グラフィックオブジェクト
     */
    void drawPreview(Graphics2D g);

    /**
     * キャンバスに描画する
     * @param g グラフィックオブジェクト
     */
    void draw(Graphics2D g);

    /**
     * 処理が終わっているかどうか
     * @return 処理が終わっていたならtrue
     */
    boolean isEnd();
}
