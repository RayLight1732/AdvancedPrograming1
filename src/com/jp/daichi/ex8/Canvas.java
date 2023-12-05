package com.jp.daichi.ex8;

import com.jp.daichi.ex8.canvasobject.CanvasObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * 描画用の領域
 */
public interface Canvas extends Serializable2 {
    /**
     * キャンバスの幅を取得
     * @return キャンバスの幅
     */
    int getWidth();

    /**
     * キャンバスの幅を設定
     * @param width 新しい幅
     */
    void setWidth(int width);

    /**
     * キャンバスの高さを取得
     * @return キャンバスの高さ
     */
    int getHeight();

    /**
     * キャンバスの高さを設定
     * @param height 新しい幅
     */
    void setHeight(int height);

    /**
     * キャンバスのサイズを取得
     * @return キャンバスのサイズ
     */
    Dimension getSize();

    /**
     * キャンバスのサイズを設定
     * @param dimension 新しいサイズ
     */
    void setSize(Dimension dimension);

    /**
     * 背景色を取得
     * @return 背景色
     */
    Color getBackgroundColor();

    /**
     * 描画色を取得
     * @return 描画色
     */
    Color getColor();

    /**
     * 描画色を設定
     * @param color 描画色
     */
    void setColor(Color color);

    /**
     * 背景色を設定
     * @param color 新しい色
     */
    void setBackgroundColor(Color color);

    /**
     * 履歴を取得
     * @return 履歴
     */
    History getHistory();

    /**
     * 描画を行う
     * @param g グラフィックオブジェクト
     */
    void draw(Graphics2D g);

    /**
     * 最後に描画された際のBufferedImageを取得する
     * @return 最後に描画された際のBufferedImage
     */
    BufferedImage getBufferedImage();

    /**
     * 線の太さを取得する
     * @return 線の太さ
     */
    int getThickNess();

    /**
     * 線の太さを設定する
     * @param thickness 線の太さ
     */
    void setThickness(int thickness);

    /**
     * プレビューを設定(null可能)
     * @param canvasObject プレビュー
     */
    void setPreview(CanvasObject canvasObject);

    void addColorChangeListener(ColorChangeListener listener);

    void removeColorChangeListener(ColorChangeListener listener);

}
