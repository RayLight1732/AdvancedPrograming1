package com.jp.daichi.ex4;

import java.awt.*;
import java.awt.geom.Area;

/**
 * 色が連続的に変化する抽象オブジェクト
 */
public abstract class AColoredObject extends AObject implements Colored {

    private float h;//hsvのh 0~1が0~360に対応
    private Color color;
    private Color outLine;

    /**
     * x,y,ベクトルが指定された新しいインスタンスを返す
     * @param x x座標
     * @param y y座標
     * @param vector ベクトル
     */
    public AColoredObject(double x, double y, Vec2d vector) {
        this(x,y,vector,null);
    }

    /**
     * x,y,ベクトル,色が指定された新しいインスタンスを返す
     * @param x x座標
     * @param y y座標
     * @param vector ベクトル
     * @param color 色(nullの場合、連続的に変化)
     */
    public AColoredObject(double x, double y, Vec2d vector,Color color) {
        this(x,y,vector,color,null);
    }

    /**
     * x,y,ベクトル,色,枠線の色が指定された新しいインスタンスを返す
     * @param x x座標
     * @param y y座標
     * @param vec2d ベクトル
     * @param color 色(nullの場合、連続的に変化)
     * @param outLine 枠線の色(nullの場合、枠線は描画されない)
     */
    public AColoredObject(double x,double y, Vec2d vec2d,Color color,Color outLine) {
        super(x,y,vec2d);
        this.color = color;
        this.outLine = outLine;
    }
    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        h += 1/360.0*60*deltaTime;//一秒で60度変化
    }

    @Override
    protected void draw(Graphics2D g,double x,double y,double step) {
        //Color old = g.getColor();//現在使用している色を保存
        g.setColor(getColor());//描画色変更
        Area area1 = getArea(x,y);
        g.fill(area1);
        if (getOutLineColor() != null) {
            g.setColor(getOutLineColor());
            g.draw(area1);
        }
        //g.setColor(old);//色をもとに戻す
    }

    @Override
    public Color getColor() {
        if (color == null) {
            return new Color(Color.HSBtoRGB(h, 0.65f, 0.9f));//hsbをrgbに変換
        } else {
            return color;
        }
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getOutLineColor() {
        return outLine;
    }

    @Override
    public void setOutLineColor(Color color) {
        this.outLine = color;
    }
}
