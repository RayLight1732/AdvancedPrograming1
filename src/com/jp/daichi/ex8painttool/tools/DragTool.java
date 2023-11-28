package com.jp.daichi.ex8painttool.tools;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * クリックした際にインスタンス生成
 * ドラッグ中処理
 * ボタンリリースで終了
 */
public interface DragTool extends Tool {
    /**
     * 最初にクリックした点(ドラッグの開始点)の座標を返す
     * キャンバスの左上を(0,0)とする
     * @return 最初にクリックした点
     */
    Point getClickPoint();

    /**
     * マウスの座標が移動した際の動作を定義する
     * @param point 移動した先のキャンバス座標
     */
    void onMove(Point point);

    /**
     * マウスが離された時の動作を定義する
     * @return 描画されたもの
     */
    BufferedImage onRelease();

}
