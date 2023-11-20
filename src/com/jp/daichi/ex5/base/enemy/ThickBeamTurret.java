package com.jp.daichi.ex5.base.enemy;

import com.jp.daichi.ex4.PathObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.LivingEntity;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.base.bullet.ThickBeam;
import com.jp.daichi.ex5.particles.Charge;
import com.jp.daichi.ex5.utils.PositionConverter;
import com.jp.daichi.ex5.utils.RotationConverter;
import com.jp.daichi.ex5.utils.Utils;

import java.awt.*;
import java.awt.geom.Path2D;

public class ThickBeamTurret extends TurretEnemy {

    private static double getOutLineThick(double size) {
        if (size < 20) {
            return size*0.1;
        } else {
            return size*0.3;
        }
    }

    private static PathObject createShape(double x,double y,double size) {
        Path2D path = new Path2D.Double();
        double deltaRotation = 2*Math.PI/8;
        double rotation = deltaRotation/2;//八角形の正面を開口部とするため、開始位置をずらす
        path.moveTo(size*Math.cos(rotation),size*Math.sin(rotation));
        for (int i = 1;i < 8;i++) {
            rotation+=deltaRotation;
            path.lineTo(size*Math.cos(rotation),size*Math.sin(rotation));
        }
        size = (size-getOutLineThick(size));
        path.lineTo(size*Math.cos(rotation),size*Math.sin(rotation));
        for (int i = 1;i < 8;i++) {
            rotation-=deltaRotation;
            path.lineTo(size*Math.cos(rotation),size*Math.sin(rotation));
        }
        path.closePath();
        return new PathObject(x,y,0,0,new Vec2d(),path,Color.CYAN);
    }

    private static final Color beamColor = Color.ORANGE;
    private static final double rotationLimit = Math.toRadians(15);
    private static final double damage = 0.1;

    private double time = 0;
    private boolean spawnChargeParticle = false;
    private Charge charge;

    private ThickBeam bullet;


    /**
     * 太いビームを放つ敵
     *
     * @param game   ゲーム
     * @param target ターゲット(自動で追尾する)
     * @param x      x座標
     * @param y      y座標
     * @param size   大きさ
     * @param hp     hp
     */
    public ThickBeamTurret(Game game, LivingEntity target, double x, double y, double size, double hp) {
        super(game, createShape(x,y,size), target, size, hp);
    }

    @Override
    public void doTick_(double deltaTime) {
        GameEntity target = getTarget();
        if (target != null) {
            setRotation(Utils.getRotation(new Vec2d(getTarget().getX() - getX(), getTarget().getY() - getY()), getRotation(), rotationLimit * deltaTime));
        }
        time += deltaTime;
        if (!spawnChargeParticle) {
            charge = new Charge(this,size-getOutLineThick(size),2, beamColor);
            getGame().addParticle(charge);
            spawnChargeParticle = true;
        }
        if (time > 4 && bullet == null) {
            bullet = new ThickBeam(game,this,Math.sqrt(game.getHeight()*game.getHeight()+game.getWidth()*game.getWidth()),getOutLineThick(size), PositionConverter.getEmptyInstance(), RotationConverter.getEmptyInstance(),damage);
            bullet.setColor(beamColor);
            game.addEntity(bullet);
        }
    }

    @Override
    public void onRemoved() {
        if (charge != null) {
            charge.setEnd(true);
        }
        if (bullet != null) {
            game.removeEntity(bullet);
        }
    }

    @Override
    public double getRotationLimit() {
        return rotationLimit;
    }

    @Override
    public int getScore() {
        return 10;
    }

    @Override
    protected void pushedBy(GameEntity entity, double collideX, double collideY) {
    }
}
