package com.jp.daichi.ex5;

import com.jp.daichi.ex4.Vec2d;
import java.awt.*;
import java.awt.geom.Area;

public interface GameEntity {

    /**
     * ほかのエンティティと衝突するか
     * @param entity 対象のエンティティ
     * @return 衝突するか
     */
    boolean doCollision(GameEntity entity);

    int getCollisionRulePriority();

    void collideWith(GameEntity entity);

    void collisionTick(double deltaTime);

    void tick(double deltaTime);

    double getX();

   // double getNewX();

    double getNewX();
    double getLastTickX();


    void setX(double x);

    void setX(double x,boolean teleported);

    double getY();


    double getNewY();
    double getLastTickY();

    void setY(double y);

    void setY(double y,boolean teleported);

    void setVector(Vec2d vec);
    Vec2d getVector();
    double getRotation();

    //double getNewRotation();

    double getLastTickRotation();

    double getNewRotation();

    void setRotation(double rotation);

    boolean isCollide(GameEntity entity);

    void draw(Graphics2D g);

    Area getArea();

    boolean isVisible();

    void setVisible(boolean isVisible);

    Game getGame();

    void onRemoved();

    /**
     * 対象のエンティティを攻撃できるか
     * @param entity 対象のエンティティ
     * @return このエンティティが対象のエンティティを攻撃できるならtrue
     */
    boolean canAttack(GameEntity entity);

    /**
     * 1tick経過したか
     * @return 1tickでも経過していたらtrue
     */
    boolean lastTickExisted();

}
