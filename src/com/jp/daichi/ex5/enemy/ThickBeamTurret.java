package com.jp.daichi.ex5.enemy;

import com.jp.daichi.ex4.PathObject;
import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.LivingEntity;
import com.jp.daichi.ex5.bullet.Bullet;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.bullet.ThickBeam;
import com.jp.daichi.ex5.particles.Charging;
import com.jp.daichi.ex5.utils.PositionConverter;
import com.jp.daichi.ex5.utils.RotationConverter;

import java.awt.*;
import java.awt.geom.Path2D;

public class ThickBeamTurret extends TurretEnemy {

    private static double getOutLineThick(double size) {
        if (size < 20) {
            return size*0.1;
        } else {
            return 10;
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
    private static final double rotationLimit = Math.toRadians(20);

    private double time = 0;
    private boolean spawnChargeParticle = false;
    private Charging charging;

    private Bullet bullet;


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
        super(game, createShape(x,y,size),size, hp,target);
    }

    /**
     * 太いビームを放つ敵
     * 角度はsetRotationで変更しない限り変化しない
     * @param game ゲーム
     * @param x x座標
     * @param y y座標
     * @param hp hp
     * @param size 大きさ
     */
    public ThickBeamTurret(Game game, double x, double y, double hp, double size) {
        this(game,null,x,y, size, hp);
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        time += deltaTime;
        if (!spawnChargeParticle) {
            charging = new Charging(this,size-getOutLineThick(size),2, beamColor);
            getGame().addParticle(charging);
            spawnChargeParticle = true;
        }
        if (time > 4 && bullet == null) {
            bullet = new ThickBeam(game,this,Math.sqrt(game.getHeight()*game.getHeight()+game.getWidth()*game.getWidth()),getOutLineThick(size), PositionConverter.getEmptyInstance(), RotationConverter.getEmptyInstance(),5);
            bullet.setColor(beamColor);
            game.addEntity(bullet);
        }
    }

    @Override
    public void onRemoved() {
        if (charging != null) {
            charging.end(true);
        }
        if (bullet != null) {
            game.removeEntity(bullet);
        }
    }

    @Override
    public double getRotationLimit() {
        return rotationLimit;
    }
}
