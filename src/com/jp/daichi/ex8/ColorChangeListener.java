package com.jp.daichi.ex8;

import java.awt.*;

/**
 * 色が変わった際に呼ばれる
 */
public interface ColorChangeListener {
    /**
     * 色が変わったとき
     * @param oldColor 古い色
     * @param newColor 新しい色
     * @param isBackGround バックグラウンドカラーが変更されたか falseの場合は描画色が変更された
     */
    void onChange(Color oldColor,Color newColor,boolean isBackGround);
}
