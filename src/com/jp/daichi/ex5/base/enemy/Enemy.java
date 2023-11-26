package com.jp.daichi.ex5.base.enemy;

import com.jp.daichi.ex4.RPolygonObject;
import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.ALivingEntity;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.GameEntity;

import java.awt.*;

public abstract class Enemy extends ALivingEntity {
    private double damageCoolTime = 0;
    private final Color original = displayEntity.getColor();

    public Enemy(Game game, double size, double hp, RotationalObject displayEntity) {
        super(game,hp,size,displayEntity);
    }
    public Enemy(Game game, double x, double y, double size, double hp) {
        this(game, size, hp, new RPolygonObject(x,y,size,6,0,Math.PI,new Vec2d(),Color.BLUE));
    }

    @Override
    public int getCollisionRulePriority() {
        return 0;
    }

    @Override
    protected void doTick_(double deltaTime) {
        super.doTick_(deltaTime);
        if (damageCoolTime >= 0) {
            damageCoolTime = Math.max(0, damageCoolTime -deltaTime);
            if ((int)(damageCoolTime *10)%2!=0) {
                displayEntity.setColor(Color.RED);
            } else {
                displayEntity.setColor(original);
            }
        }
    }

    @Override
    public void attackedBy(GameEntity entity, double damage) {
        setHP(getHP()-damage);
        damageCoolTime = 1;
        if (getHP() <= 0) {
            killedBy(entity);
        }
    }

    @Override
    public boolean canAttack(GameEntity entity) {
        return !(entity instanceof Enemy);
    }

    /**
     * 倒した時のスコア
     * @return
     */
    public abstract int getScore();

}
