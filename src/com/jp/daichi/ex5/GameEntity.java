package com.jp.daichi.ex5;

import com.jp.daichi.ex4.AObject;
import com.jp.daichi.ex4.RotationalObject;
import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.geom.Area;

public interface GameEntity {

    /**
     * ほかのエンティティと衝突するか
     * @param entity 対象のエンティティ
     * @return 衝突するか
     */
    boolean doCollision(GameEntity entity);

    void collideWith(GameEntity entity);

    void collisionTick(double deltaTime);

    void tick(double deltaTime);

    double getX();

    void setX(double x);

    double getY();

    void setY(double y);

    void setVector(Vec2d vec);

    Vec2d getVector();

    double getRotation();

    void setRotation(double rotation);

    boolean isCollide(GameEntity entity);

    void draw(Graphics g);

    Area getArea();

    boolean isVisible();

    void setVisible(boolean isVisible);
}
