package com.jp.daichi.ex5.impl.bullet;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.base.bullet.Missile;
import com.jp.daichi.ex5.utils.Utils;

public class PlayerMissile extends Missile {
    public PlayerMissile(Game game, GameEntity holder,double x, double y, Vec2d vec) {
        super(game,holder,30,x,y,vec, Utils.playerBulletSpeed*1.5,Utils.rotatetionSpeed*1.5,500,2.5,10);
    }
}
