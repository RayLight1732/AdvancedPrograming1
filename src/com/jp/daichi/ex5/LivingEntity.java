package com.jp.daichi.ex5;

public interface LivingEntity extends GameEntity{
    double getHP();
    void setHP(double hp);

    double getMaxHP();

    double setMaxHP(double maxHP);

    void attackedBy(GameEntity entity, double damage);
    void kill();

    void killedBy(GameEntity entity);

    boolean isDead();
}
