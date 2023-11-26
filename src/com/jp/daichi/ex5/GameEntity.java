package com.jp.daichi.ex5;

import com.jp.daichi.ex4.Vec2d;
import java.awt.*;
import java.awt.geom.Area;

public interface GameEntity extends GameObject {

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

    void setVector(Vec2d vec);
    Vec2d getVector();

    boolean isCollide(GameEntity entity);

    void draw(Graphics2D g);

    Area getArea();


    Game getGame();

    void onRemoved();

    /**
     * 対象のエンティティを攻撃できるか
     * @param entity 対象のエンティティ
     * @return このエンティティが対象のエンティティを攻撃できるならtrue
     */
    boolean canAttack(GameEntity entity);


}
