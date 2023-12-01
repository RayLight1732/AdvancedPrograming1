package com.jp.daichi.ex8.canvasobject;

import java.awt.*;

/**
 * 移動、回転が可能なキャンバスオブジェクト
 */
public interface MovableCanvasObject extends CanvasObject {
    /**
     * このオブジェクトが選択されているかどうか
     * @return 選択されているかどうか
     */
    boolean isSelected();

    /**
     * このオブジェクトが選択されているかどうかを設定
     *
     * @param isSelected 選択されているかどう
     */
    void setSelected(boolean isSelected);

    /**
     * 点が内部にあるか
     * @param point 点
     * @return 点が内部にあるならtrue
     */
    boolean contains(Point point);
}
