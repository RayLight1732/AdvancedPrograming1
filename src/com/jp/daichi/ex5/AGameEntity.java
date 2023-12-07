package com.jp.daichi.ex5;

import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.TickState;
import com.jp.daichi.ex4.Vec2d;

import java.awt.*;
import java.awt.geom.Area;

/**
 * 抽象的なゲーム内のエンティティ
 */
public abstract class AGameEntity extends AGameObject implements GameEntity,OldRenderEntity {

    protected final Game game;//対象のゲーム
    protected double size;//サイズ
    protected RotationalObject displayEntity;//描画用オブジェクト

    /**
     * 新しいインスタンスを生成
     * @param game 所属するゲーム
     * @param size サイズ
     * @param displayEntity 表示用のエンティティ
     */
    public AGameEntity(Game game,double size,RotationalObject displayEntity) {
        this.game = game;
        this.size = size;
        this.displayEntity = displayEntity;
    }


    @Override
    public void collisionTick(double deltaTime) {
        displayEntity.setTickState(TickState.InnerCheckTick);
        displayEntity.setTickState(TickState.CollisionTick);
        displayEntity.collisionTick(deltaTime);
    }

    @Override
    protected final void doTick(double deltaTime) {
        displayEntity.setTickState(TickState.ProcessTick);
        displayEntity.tick(deltaTime);
        doTick_(deltaTime);
    }

    /**
     * ティック処理を行う
     * @param deltaTime 前回からの経過時間(秒)
     */
    protected void doTick_(double deltaTime){}


    @Override
    public double getX() {
        return displayEntity.getX();
    }


    @Override
    public void setX(double x) {
        displayEntity.setX(x);
    }

    @Override
    public void setX(double x, boolean teleported) {
        displayEntity.setX(x,teleported);
    }

    @Override
    public double getY() {
        return displayEntity.getY();
    }

    @Override
    public void setY(double y) {
        displayEntity.setY(y);
    }

    @Override
    public void setY(double y, boolean teleported) {
        displayEntity.setY(y,teleported);
    }

    @Override
    public void setVector(Vec2d vec) {
        displayEntity.setVector(vec);
    }

    @Override
    public Vec2d getVector() {
        return displayEntity.getVector();
    }

    @Override
    public double getRotation() {
        return displayEntity.getRotation();
    }

    @Override
    public void setRotation(double rotation) {
        displayEntity.setRotation(rotation);
    }

    @Override
    public void draw(Graphics2D g) {
        displayEntity.draw(g);
    }

    @Override
    public Area getArea() {
        return displayEntity.getArea();
    }

    @Override
    public boolean isCollide(GameEntity entity) {
        Area a1 = getArea();
        Area a2 = entity.getArea();
        if (a1.getBounds2D().intersects(a2.getBounds2D())) {//負荷軽減のためにまずは大まかな矩形で判定
            a1.intersect(a2);
            return !a1.isEmpty();//共通部分が空でなければ交わっている
        } else {
            return false;
        }
    }

    /**
     * このエンティティのサイズを取得
     * @return サイズ
     */
    public double getSize() {
        return this.size;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void onRemoved() {
    }

    @Override
    public boolean doCollision(GameEntity entity) {
        return true;
    }

    @Override
    public int getCollisionRulePriority() {
        return 0;
    }

    @Override
    public RotationalObject getRotationalObject() {
        return displayEntity;
    }

}
