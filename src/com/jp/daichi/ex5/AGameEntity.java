package com.jp.daichi.ex5;

import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.TickState;
import com.jp.daichi.ex4.Vec2d;

import java.awt.*;
import java.awt.geom.Area;

public abstract class AGameEntity implements GameEntity,OldRenderEntity {

    protected final Game game;//対象のゲーム
    protected double size;//サイズ
    protected RotationalObject displayEntity;//描画用オブジェクト
    protected boolean isVisible = true;
    protected double lastTickX;
    protected double lastTickY;
    protected double lastTickRotation;
    protected boolean lastTickExisted = false;
    protected double newX;
    protected double newY;
    protected double newRotation;

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
    public final void tick(double deltaTime) {
        lastTickExisted = true;
        double lastTickX = getX();
        double lastTickY = getY();
        double lastTickRotation = getRotation();
        displayEntity.setTickState(TickState.ProcessTick);
        displayEntity.tick(deltaTime);
        doTick(deltaTime);
        this.lastTickX = lastTickX;
        this.lastTickY = lastTickY;
        this.lastTickRotation = lastTickRotation;
        newX = getX();
        newY = getY();
        newRotation = getRotation();
    }


    protected abstract void doTick(double deltaTime);


    @Override
    public double getX() {
        return displayEntity.getX();
    }

    @Override
    public double getLastTickX() {
        return lastTickX;
    }

    @Override
    public double getNewX() {
        return newX;
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
    public double getNewY() {
        return newY;
    }

    @Override
    public double getLastTickY() {
        return lastTickY;
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
    public double getNewRotation() {
        return newRotation;
    }

    @Override
    public double getLastTickRotation() {
        return lastTickRotation;
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
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
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

    @Override
    public boolean lastTickExisted() {
        return lastTickExisted;
    }
}
