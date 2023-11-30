package com.jp.daichi.ex8painttool;

import com.jp.daichi.ex8painttool.tools.Tool;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 描画用の領域
 */
public interface Canvas {
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
     * 背景色を設定
     * @param color 新しい色
     */
    void setBackgroundColor(Color color);

    /**
     * 現在のキャンバスの状態を取得
     * @return 現在のキャンバスの状態
     */
    BufferedImage getBufferedImage();

    /**
     * 履歴を取得
     * @return 履歴
     */
    History getHistory();

    /**
     * idの場所までundo,redoする
     * @param id 履歴のid
     */
    void to(int id);

    /**
     * 何らかのキャンバスを更新しうるイベントが発生した際に呼ばれる
     * 例:MouseClickEvent,MouseMoveEvent,MouseReleaseEvent
     */
    void update();

    /**
     * 描画を行う
     * @param g グラフィックオブジェクト
     */
    void draw(Graphics2D g);

    /**
     * 描画用のツールを設定
     * @param tool ツール
     */
    void setTool(Tool tool);
}
