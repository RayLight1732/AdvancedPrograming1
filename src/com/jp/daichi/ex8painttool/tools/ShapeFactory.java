package com.jp.daichi.ex8painttool.tools;

import java.awt.*;

/**
 * @see Shape のインスタンスを作成
 */
public interface ShapeFactory {
    /**
     * インスタンスを作成
     * @param x1 x座標1
     * @param y1 y座標1
     * @param x2 x座標2
     * @param y2 y座標2
     * @return 新しいインスタンス
     */
    Shape create(int x1,int y1,int x2,int y2);
}
