package com.jp.daichi.ex5;

public interface LivingEntity extends GameEntity{
    double getHP();
    void setHP(double hp);

    void attackedBy(GameEntity entity, double damage);
    void kill();

    boolean isDead();
}
