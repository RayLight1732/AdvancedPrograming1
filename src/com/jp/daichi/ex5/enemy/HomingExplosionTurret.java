package com.jp.daichi.ex5.enemy;

import com.jp.daichi.ex4.PathObject;
import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.LivingEntity;
import com.jp.daichi.ex5.bullet.Bullet;
import com.jp.daichi.ex5.bullet.HomingExplosion;
import com.jp.daichi.ex5.particles.Charging;
import com.jp.daichi.ex5.utils.PositionConverter;
import com.jp.daichi.ex5.utils.RotationConverter;
import com.jp.daichi.ex5.utils.Utils;

import java.awt.*;
import java.awt.geom.Path2D;

public class HomingExplosionTurret extends Enemy {

    private static RotationalObject createShape(double x,double y,double size) {
        Path2D path = new Path2D.Double();
        path.moveTo(size*0.2,0);
        double rotation = Math.toRadians(120);
        path.lineTo(size*Math.cos(rotation),size*Math.sin(rotation));
        path.lineTo(-size*0.2,0);
        rotation += Math.toRadians(120);
        path.lineTo(size*Math.cos(rotation),size*Math.sin(rotation));
        path.closePath();
        return new PathObject(x,y,0,0,new Vec2d(),path,Color.BLUE);
    }

    private static final double originalCoolTime = 3;
    private static final double originalChargeTime = 2;
    private static final double bulletRadius = 10;
    private static final double bulletLife = 7;

    private final LivingEntity target;
    private double coolTime = originalCoolTime;
    private double chargeTime = originalChargeTime;
    private Charging charging;
    private final PositionConverter converter = (x,y)->{
        double r = getSize()*0.2+bulletRadius;
        return new Vec2d(x+r*Math.cos(getRotation()),y+r*Math.sin(getRotation()));
    };

    public HomingExplosionTurret(Game game,double x, double y,double size, double hp, LivingEntity target) {
        super(game, size, hp, createShape(x,y,size));
        this.target = target;
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        setRotation(Utils.getRotation(new Vec2d(target.getX()-getX(),target.getY()-getY()),getRotation(),Utils.rotateSpeed*deltaTime));
        if (coolTime > 0) {
            coolTime-=deltaTime;
        } else if (chargeTime > 0) {
            if (charging == null) {
                charging = new Charging(this,bulletRadius,originalChargeTime*0.8,converter, Color.ORANGE);
                game.addParticle(charging);
            }
            chargeTime-=deltaTime;
        } else {//発射
            charging.end(true);
            charging = null;
            Vec2d pos = converter.convert(getX(),getY());
            Bullet bullet = new HomingExplosion(game,this,target,bulletRadius,pos.x,pos.y,getVector().multiple(3),10,Utils.rotateSpeed*0.8,bulletLife,Color.ORANGE);
            System.out.println(bullet.getX()+","+bullet.getY());
            game.addEntity(bullet);
            coolTime = originalCoolTime;
            chargeTime = originalChargeTime;
        }
    }

    @Override
    protected Enemy createNewEnemy(Game game, double x, double y, double size, double hp) {
        Enemy enemy = new HomingExplosionTurret(game,x,y,size,hp,target);
        enemy.setVector(getVector());
        return enemy;
    }

    @Override
    public void setRotation(double rotation) {
        super.setRotation(rotation);
        double length = getVector().getLength();
        if (length > 0) {
            Vec2d vec = Utils.getDirectionVector(this);
            vec.multiple(length);
            setVector(vec);
        }
    }

    @Override
    public void onRemove() {
        if (charging != null) {
            charging.end(true);
        }
    }
}
