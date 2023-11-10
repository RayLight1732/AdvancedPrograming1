package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

/**
 * 正多角形を表すクラス
 */
public class RPolygonObject extends PathObject {

    public static Path2D createRPolygon(int nPoints,double radius) {
        Path2D path = new Path2D.Double();
        double rotation = 0;
        path.moveTo(radius*Math.cos(rotation),radius*Math.sin(rotation));
        double deltaRotation = 2*Math.PI/nPoints;
        for (int i = 1;i < nPoints;i++) {
            path.lineTo(radius*Math.cos(rotation+deltaRotation*i),radius*Math.sin(rotation+deltaRotation*i));
        }
        path.closePath();
        return path;
    }

    private final double radius;
    private final int nPoints;
    /**
     *
     * @param x 中心のx座標
     * @param y 中心のy座標
     * @param radius 半径(中心からある頂点までの距離)
     * @param nPoints 頂点の数
     * @param rotation 回転角(ラジアン)
     * @param rotationSpeed 回転速度(ラジアン)
     * @param vector 速度ベクトル
     */
    public RPolygonObject(double x, double y, double radius, int nPoints,double rotation,double rotationSpeed, Vec2d vector) {
        this(x, y, radius,nPoints,rotation,rotationSpeed,vector,null);
    }

    public RPolygonObject(double x, double y, double radius, int nPoints,double rotation,double rotationSpeed, Vec2d vector,Color color) {
        super(x, y,rotation ,rotationSpeed,vector,createRPolygon(nPoints, radius),color);
        this.radius = radius;
        this.nPoints = nPoints;
    }


    /**
     * 正多角形の半径(中心と頂点の距離)を返す
     * @return 正多角形の半径
     */
    public double getRadius() {
        return radius;
    }

    /**
     * 正多角形の頂点の数
     * @return 頂点の数
     */
    public int getNPoints() {
        return nPoints;
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
    }


}
