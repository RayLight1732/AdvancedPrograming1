package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.geom.Area;

/**
 * 色が連続的に変化する抽象オブジェクト
 */
public abstract class AColoredObject extends AObject implements Colored {

    private float h;//hsvのh 0~1が0~360に対応
    private Color color;
    private Color outLine;

    public AColoredObject(double x, double y, Vec2d vector) {
        this(x,y,vector,null);
    }

    public AColoredObject(double x, double y, Vec2d vector,Color color) {
        this(x,y,vector,color,null);
    }

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
    public void draw(Graphics g) {
        Color old = g.getColor();//現在使用している色を保存
        g.setColor(getColor());//描画色変更
        if (g instanceof Graphics2D) {
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            Area area1 = getArea(getX(),getY());
            ((Graphics2D)g).fill(area1);
            if (getOutLineColor() != null) {
                g.setColor(getOutLineColor());
                ((Graphics2D) g).draw(area1);
            }
        }
        g.setColor(old);//色をもとに戻す
    }

    @Override
    public Color getColor() {
        if (color == null) {
            return new Color(Color.HSBtoRGB(h, 1f, 1f));//hsbをrgbに変換
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
