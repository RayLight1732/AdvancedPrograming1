package com.jp.daichi.ex5;

import com.jp.daichi.ex4.RPolygonObject;
import com.sun.javafx.geom.Vec2d;

import java.awt.*;

public class Enemy extends ALivingEntity {
    private double damageCooltime = 0;
    public Enemy(Game game,double x, double y, double size) {
        super(game,5,size,new RPolygonObject(x,y,size,6,0,0,new Vec2d(),Color.BLUE));
    }

    @Override
    public boolean doCollision(GameEntity entity) {
        return false;
    }

    @Override
    public void collideWith(GameEntity entity) {
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        if (damageCooltime >= 0) {
            damageCooltime = Math.max(0,damageCooltime-deltaTime);
            if ((int)(damageCooltime*10)%2!=0) {
                displayEntity.setColor(Color.RED);
            } else {
                displayEntity.setColor(Color.BLUE);
            }
        }
    }

    @Override
    public void attackedBy(GameEntity entity, double damage) {
        setHP(getHP()-damage);
        damageCooltime = 1;
        if (hp <= 0) {
            kill();
        }
    }

    @Override
    public void kill() {
        super.kill();
        System.out.println("kill");
        game.removeEntity(this);
        double x = Math.random()*game.getWidth();
        double y = Math.random()*game.getHeight();
        game.addEntity(createNewEnemy(game,x,y,size));
    }

    protected Enemy createNewEnemy(Game game,double x,double y,double size) {
        return new Enemy(game,x,y,size);
    }
}
