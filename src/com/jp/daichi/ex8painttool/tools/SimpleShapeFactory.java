package com.jp.daichi.ex8painttool.tools;

import java.awt.*;

/**
 * ShapeFactoryの単純な実装
 */
public class SimpleShapeFactory implements ShapeFactory {

    private final InnerShapeFactory factory;

    public SimpleShapeFactory(InnerShapeFactory factory) {
        this.factory = factory;
    }

    @Override
    public Shape create(int x1, int y1, int x2, int y2) {
        int x = Math.min(x1,x2);
        int y = Math.min(y1,y2);
        int width = Math.abs(x1-x2);
        int height = Math.abs(y1-y2);
        return factory.create(x,y,width,height);
    }

    /**
     * @see ShapeFactory の別の表現
     */
    public interface InnerShapeFactory {
        /**
         * 新しいShapeのインスタンスを作成
         * @param x x座標
         * @param y y座標
         * @param width 幅
         * @param height 高さ
         * @return 新しいShapeのインスタンス
         */
        Shape create(int x,int y,int width,int height);
    }
}
