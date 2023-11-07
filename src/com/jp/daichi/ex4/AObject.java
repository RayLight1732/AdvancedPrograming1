package com.jp.daichi.ex4;

import com.jp.daichi.ex5.Utils;
import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * すべての描画用オブジェクトの基底クラス
 */
public abstract class AObject {

    /**
     * 衝突時、衝突後のベクトルを取得
     * @param origin もととなるオブジェクト
     * @param collide 衝突したオブジェクト
     * @param hitX 衝突点のx座標
     * @param hitY 衝突点のy座標
     * @return 衝突後のベクトル
     */
    public static Vec2d getCollidedVector(AObject origin,AObject collide,double hitX,double hitY) {
        if (collide instanceof WallObject) {
            double hVx = hitX- origin.getX();
            double hVy = hitY - origin.getY();
            if (hVx*hVx+hVy*hVy < 0.001) {//速度が十分に小さいとき、停止
                return new Vec2d(0,0);
            }
            WallObject w = (WallObject) collide;
            double x1 = 0,y1 = 0;
            double x2 = 0,y2 = 0;
            double minSqDistance = Double.MAX_VALUE;
            int collideI = -1;
            for (int i = 0;i < 4;i++) {
                switch (i) {
                    case 0:
                        x1 = w.getX();
                        y1 = w.getY();
                        x2 = w.getX();
                        y2 = y1+w.getHeight();
                        break;
                    case 1:
                        x1 = w.getX();
                        y1 = w.getY()+w.getHeight();
                        x2 = x1+w.getWidth();
                        y2 = y1;
                        break;
                    case 2:
                        x1 = w.getX()+w.getWidth();
                        y1 = w.getY()+w.getHeight();
                        x2 = x1;
                        y2 = w.getY();
                        break;
                    case 3:
                        x1 = w.getX()+w.getWidth();
                        y1 = w.getY();
                        x2 = w.getX();
                        y2 = y1;
                        break;
                }
                //内積計算
                double dot1 = Utils.dot(hVx,hVy,x1-origin.getX(),y1-origin.getY());
                double dot2 = Utils.dot(hVx,hVy,x2-origin.getX(),y2-origin.getY());
                //どちらも内積0の時延長上
                if(dot1 == 0 && dot2 == 0) {
                    continue;//反射方向はこのループではない
                } else if (dot1 == 0) {
                    //片方内積ゼロのとき反射
                    double sqDistance = Utils.getSqDistance(origin.getX(),origin.getY(),x1,y1);
                    if (sqDistance < minSqDistance) {
                        minSqDistance = sqDistance;
                        collideI = i;
                    }
                } else if (dot2 == 0) {
                    double sqDistance = Utils.getSqDistance(origin.getX(),origin.getY(),x2,y2);
                    if (sqDistance < minSqDistance) {
                        minSqDistance = sqDistance;
                        collideI = i;
                    }
                } else if (dot1*dot2 < 0) {
                    //異符号のとき交わっている
                    /*
                    o+koV=t(x1,y1)+(1-t)(x2,y2)


                    |oVx x2-x1||k|_|x2-ox|
                    |oVy y2-y1||t|-|y2-oy|

                    |A|=oVx*(y2-y1)-oVy*(x2-x1)
                    |A|≠0のとき逆行列存在
                    |k|_        1    |y2-y1   -(x2-x1)||x2-ox|
                    |t|-  determinant|-oVy      oVx   ||y2-oy|
                     */
                    double determinant = hVx*(y2-y1)-hVy*(x2-x1);

                    double k = (y2-y1)*(x2-origin.getX())-(x2-x1)*(y2- origin.getY())/determinant;
                    double hitLineX = origin.getX()+k*hVx;
                    double hitLineY = origin.getY()+k*hVy;
                    double sqDistance = Utils.getSqDistance(origin.getX(),origin.getY(),hitLineX,hitLineY);
                    if (sqDistance < minSqDistance) {
                        minSqDistance = sqDistance;
                        collideI = i;
                    }
                }
            }
            Vec2d result;
            switch (collideI) {
                case 0:
                    result = new Vec2d(-Math.abs(origin.getVecX()),origin.getVecY());
                    break;
                case 1:
                    result = new Vec2d(origin.getVecX(),Math.abs(origin.getVecY()));
                    break;
                case 2:
                    result = new Vec2d(Math.abs(origin.getVecX()),origin.getVecY());
                    break;
                case 3:
                    result = new Vec2d(origin.getVecX(),-Math.abs(origin.getVecY()));
                    break;
                default:
                    result = origin.getVector();
            }

            Utils.multiple(result,origin.getRestitutionCoefficient()*collide.getRestitutionCoefficient());
            return result;
        } else {
            return collide.getVector();
        }
    }

    protected double x;//x座標
    protected double y;//y座標

    protected Vec2d vector;//ベクトル
    protected Vec2d preVec;//更新用

    public double preX;//更新用
    public double preY;//更新用

    protected List<AObject> lastCollide = new ArrayList<>();//前tickに衝突したオブジェクト
    protected List<AObject> collide = new ArrayList<>();//現在のtickに衝突したオブジェクト

    protected Area area;//メモ化用
    protected TickState tickState = TickState.None;//現在の処理段階
    protected double restitutionCoefficient;//反発係数

    public AObject(double x,double y,Vec2d vector) {
        this(x,y,vector,1);
    }

    public AObject(double x,double y,Vec2d vector,double restitutionCoefficient) {
        setX(x);
        setY(y);
        this.vector = vector;
        this.restitutionCoefficient = restitutionCoefficient;
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
     * 処理用の一時的なベクトルのコピーを返す
     * @return 処理用の一時的なベクトルのコピー
     */
    public Vec2d getPreVec() {
        return new Vec2d(preVec);
    }

    /**
     * 処理用の一時的なベクトルを設定する
     * @param preVec 処理用の一時的なベクトル
     */
    public void setPreVec(Vec2d preVec) {
        this.preVec = preVec;
    }

    /**
     * 描画用メソッド
     * @param g グラフィックオブジェクト
     */
    public void draw(Graphics g) {
        if (g instanceof Graphics2D) {
            //アンチエイリアシング有効化
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D)g).fill(getArea(getX(),getY()));//図形描画
        }
    }

    /**
     * 衝突判定前に実行される
     * @param deltaTime 前回からどれだけ経過したか
     */
    public void collisionTick(double deltaTime) {
        lastCollide = collide != null ? collide : new ArrayList<>();//lastCollideを更新
        collide = new ArrayList<>();//衝突したオブジェクト格納用list
        if (getTickState() == TickState.CollisionTick) {
            //二回目のcollisionTickの時 preVecを適用
            setVector(preVec);
        }
        //経過した時間分動かす
        preVec = getVector();
        preX = getX() + preVec.x * deltaTime;
        preY = getY() + preVec.y * deltaTime;

        area = getArea(preX,preY);
        tickState = TickState.CollisionTick;
    }

    /**
     * 更新用メソッド
     * @param deltaTime 前回からどれだけ経過したか(秒)
     */
    public void tick(double deltaTime) {
        tickState = TickState.ProcessTick;
        //マークした座標に更新
        x = preX;
        y = preY;
        setVector(preVec);
        area = getArea(getX(),getY());
    }

    /**
     * 衝突しているかどうかを判定するメソッド
     * @param object 対称のオブジェクト
     * @return このオブジェクトと対称のオブジェクトが衝突している場合、共通部分の位置ベクトル、そうでなければnull
     */
    public Vec2d isCollide(AObject object) {
        Area a1 = getArea();
        Area a2 = object.getArea();
        if (a1.getBounds2D().intersects(a2.getBounds2D())) {//負荷軽減のためにまずは大まかな矩形で判定
            a1.intersect(a2);//共通部分算出
            if (a1.isEmpty()) {
                //空ならnull
                return null;
            } else {
                Rectangle2D rec2d = a1.getBounds2D();
                return new Vec2d(rec2d.getCenterX(),rec2d.getCenterY());//中心座標返却
            }
        } else {
            return null;
        }
    }

    /**
     * 衝突した際に呼ばれるメソッド
     * @param object 対象のオブジェクト
     * @param deltaTime 前回からどれだけ経過したか(秒)
     * @param hitX 衝突時の共通部分の中心x座標
     * @param hitY 衝突時の共通部分の中心y座標
     */
    public void collideWith(AObject object,double deltaTime,double hitX,double hitY) {
        /*
        collide.add(object);//衝突したことを記録
        if (lastCollide.contains(object)) {
            //めり込んでいる可能性
            double vecX = getX()-object.getX();//離れる方向
            double vecY = getY()-object.getY();//離れる方向
            double length = Math.sqrt(vecX*vecX+vecY*vecY);//長さ
            vecX /= length;//正規化
            vecY /= length;//正規化
            vecX *= 100*deltaTime;//長さを秒間40に
            vecY *= 100*deltaTime;//長さを秒間40に
            preVec.x += vecX;//次の速度に加算
            preVec.y += vecY;//次の速度に加算
        } else {
            preVec = object.getVector();//衝突した際速度を入れ替え
            preX = getX();//衝突したら動かない
            preY = getY();//衝突したら動かない
        }*/

        preVec = getCollidedVector(this,object,hitX,hitY);
        preX = getX();//衝突したら動かない
        preY = getY();//衝突したら動かない

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
     * @return 更新後のエリア
     */
    protected abstract Area getArea(double x,double y);

    /**
     * 現在の処理状態を取得
     * @return 処理状態
     */
    public TickState getTickState() {
        return tickState;
    }

    /**
     * 反発係数を取得
     * 物体と物体の反発係数は質量が同じであるとして計算される
     * @return 壁との反発係数
     */
    public double getRestitutionCoefficient() {
        return restitutionCoefficient;
    }

}
