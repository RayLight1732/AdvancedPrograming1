package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

public class PathObject extends RotationalObject {

    private final Path2D original;
    private Path2D path;

    public PathObject(double x, double y,double rotationSpeed, Vec2d vector, Path2D path) {
        this(x, y, rotationSpeed,vector,path,null);
    }

    public PathObject(double x, double y, double rotationSpeed, Vec2d vector, Path2D path, Color color) {
        super(x, y, rotationSpeed,vector,color);
        this.original = (Path2D) path.clone();//オリジナルのパスをコピーして保存
    }


    @Override
    protected Area getArea(double x, double y) {
        AffineTransform transform = AffineTransform.getTranslateInstance(x,y);//移動して
        transform.rotate(getRotation());//回転
        path = (Path2D) original.clone();
        path.transform(transform);
        return new Area(path);
    }

    /**
     * このオブジェクトの形を表すパスのコピーを返す
     * @return パスのコピー
     */
    public Path2D getPath() {
        return (Path2D) path.clone();
    }

}
