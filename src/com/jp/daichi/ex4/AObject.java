package com.jp.daichi.ex4;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

public abstract class AObject {

    protected double x;//x座標
    protected double y;//y座標

    protected Vec2d vector;//ベクトル
    protected Vec2d preVec;//更新用

    public double preX;//更新用
    public double preY;//更新用

    protected List<AObject> lastCollide = new ArrayList<>();//前tickに衝突したオブジェクト
    protected List<AObject> collide = new ArrayList<>();//現在のtickに衝突したオブジェクト

    protected Area area;

    public AObject(double x,double y,Vec2d vector) {
        setX(x);
        setY(y);
        this.vector = vector;
    }

    /**
     * x座標を取得
     * @return x座標
     */
    public double getX() {
        return x;
    }

    /**
     * x座標を設定
     * @param x 新たなx座標
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * y座標を取得
     * @return y座標
     */
    public double getY() {
        return y;
    }

    /**
     * y座標を設定
     * @param y 新たなy座標
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * 速度ベクトルのコピーを取得
     * @return 速度ベクトル
     */
    public Vec2d getVector() {
        return new Vec2d(vector);
    }

    /**
     * ベクトルを設定
     * @param vector ベクトル
     */
    public void setVector(Vec2d vector) {
        this.vector = vector;
    }

    /**
     * 速度ベクトルのx成分を設定
     * @param vecX x成分
     */
    public void setVecX(double vecX) {
        this.vector.x = vecX;
    }

    /**
     * 速度ベクトルのx成分を取得
     * @return x成分
     */
    public double getVecX() {
        return this.vector.x;
    }

    /**
     * 速度ベクトルのy成分を設定
     * @param vecY y成分
     */
    public void setVecY(double vecY) {
        this.vector.y = vecY;
    }

    /**
     * 速度ベクトルのy成分を取得
     * @return y成分
     */
    public double getVecY() {
        return this.vector.y;
    }

    /**
     * 描画用メソッド
     * @param g グラフィックオブジェクト
     */
    public void draw(Graphics g) {
        if (g instanceof Graphics2D) {
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D)g).fill(getArea(getX(),getY()));
        }
    }

    /**
     * 衝突判定前に実行される
     * @param deltaTime 前回からどれだけ経過したか
     */
    public void collisionTick(double deltaTime) {
        lastCollide = collide != null ? collide : new ArrayList<>();//lastCollideを更新
        collide = new ArrayList<>();//衝突したオブジェクト格納用list
        //経過した時間分動かす
        preX = getX()+ vector.x*deltaTime;
        preY = getY()+ vector.y*deltaTime;
        preVec = getVector();
        area = getArea(preX,preY);
    }

    /**
     * 更新用メソッド
     * @param deltaTime 前回からどれだけ経過したか
     */
    public void tick(double deltaTime) {
        //マークした座標に更新
        x = preX;
        y = preY;
        vector = preVec;
        area = getArea(getX(),getY());
    }

    /**
     * 衝突しているかどうかを判定するメソッド
     * @param object 対称のオブジェクト
     * @return このオブジェクトと対称のオブジェクトが衝突している場合true
     */
    public boolean isCollide(AObject object) {
        Area a1 = getArea();
        Area a2 = object.getArea();
        if (a1.getBounds2D().intersects(a2.getBounds2D())) {//負荷軽減のためにまずは大まかな矩形で判定
            a1.intersect(a2);
            return !a1.isEmpty();//共通部分が空でなければ交わっている
        } else {
            return false;
        }
    }

    /**
     * 衝突した際に呼ばれるメソッド
     * @param object 対象のオブジェクト
     */
    public void collideWith(AObject object) {
        collide.add(object);//衝突したことを記録
        if (lastCollide.contains(object)) {
            //めり込んでいる可能性
            double vecX = getX()-object.getX();//離れる方向
            double vecY = getY()-object.getY();//離れる方向
            double length = Math.sqrt(vecX*vecX+vecY*vecY);//長さ
            vecX /= length;//正規化
            vecY /= length;//正規化
            vecX *= 20;//長さを20に
            vecY *= 20;//長さを20に
            preVec.x += vecX;//次の速度に加算
            preVec.y += vecY;//次の速度に加算
        } else {
            preVec = object.getVector();//衝突した際速度を入れ替え
            preX = getX();//衝突したら動かない
            preY = getY();//衝突したら動かない
        }

    }

    /**
     * このオブジェクトが表すエリアのコピーを返す
     * @return エリアのコピー
     */
    public Area getArea() {
        if (area == null) {
            area = getArea(getX(),getY());
        }
        return (Area) area.clone();
    }

    /**
     * 代表座標がx,yの時のエリアに更新
     * @param x 代表座標のx
     * @param y 代表座標のy
     */
    protected abstract Area getArea(double x,double y);
}
