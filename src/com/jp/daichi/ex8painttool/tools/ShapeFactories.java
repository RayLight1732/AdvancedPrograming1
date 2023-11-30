package com.jp.daichi.ex8painttool.tools;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * @see ShapeFactory を集めたクラス
 */
public enum ShapeFactories {
    RECTANGLE(Rectangle::new),
    ELLIPSE(Ellipse2D.Double::new);

    private final ShapeFactory factory;
    ShapeFactories(SimpleShapeFactory.InnerShapeFactory factory) {
        this.factory = new SimpleShapeFactory(factory);
    }

    public ShapeFactory getFactory() {
        return factory;
    }
}