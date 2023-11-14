package com.jp.daichi.ex5.particles;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.utils.PositionConverter;
import com.jp.daichi.ex5.utils.RotationConverter;

import java.awt.*;

public class Charge extends SmoothRoundSlaveParticle {
    private final double expansion;

    private boolean end = false;
    private final double originalRadius;
    private final Color color;
    private double time = 0;

    /**
     * チャージ時のパーティクル
     * 位置はマスターのx,y
     * @param entity 対象となるエンティティ
     * @param radius 半径
     * @param expansion 拡大する時間
     * @param color 描画色
     */
    public Charge(GameEntity entity, double radius, double expansion, Color color) {
        this(entity,radius,expansion, PositionConverter.getEmptyInstance(), color);
    }

    /**
     * チャージ時のパーティクル
     * @param entity 対象となるエンティティ
     * @param radius 半径
     * @param expansion 拡大する時間
     * @param positionConverter 位置を変換するオブジェクト
     * @param color 描画色
     */
    public Charge(GameEntity entity, double radius, double expansion, PositionConverter positionConverter, Color color) {
        super(entity, positionConverter, RotationConverter.getEmptyInstance());
        this.originalRadius = radius;
        this.expansion = expansion;
        this.color = color;
    }

    private double lastSpawnTime = 0;

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        time += deltaTime;
        if (time-lastSpawnTime > 0.1) {
            lastSpawnTime = time;
            double rotation = Math.random()*Math.PI*2;//0~2PIまでの乱数
            double radius = Math.random()*originalRadius/2;//最大半径の1/2までのランダムな半径
            double posRadius = Math.random()*this.getRadius()*0.3+this.getRadius()*1.2;//1.2radius~1.5*radiusまでの乱数
            System.out.println();
            Vec2d pos = new Vec2d(getX()+posRadius*Math.cos(rotation),getY()+posRadius*Math.sin(rotation));
            getMaster().getGame().addParticle(new Child(pos,radius));
        }
        if (time < expansion) {
            setRadius(originalRadius*time/expansion);
        }
    }

    @Override
    protected void draw(Graphics2D g, double x, double y, double radius, double step) {
        g.setColor(color);
        if (time >= expansion) {
            radius = originalRadius+(Math.random()-0.5)*originalRadius*0.05;//半径の1/20で振動
        }
        g.fillOval((int)Math.round(x-radius),(int)Math.round(y-radius),(int)Math.round(radius*2),(int)Math.round(radius*2));
    }


    @Override
    public boolean isEndDrawing() {
        return end;
    }

    public void end(boolean end) {
        this.end = end;
    }

    private class Child extends SmoothParticle {

        private double time = 0;
        private final double radius;
        private boolean end = false;
        private Child(Vec2d position,double radius) {
            setX(position.x);
            setY(position.y);
            this.radius = radius;
        }

        @Override
        public void tick(double deltaTime) {
            super.tick(deltaTime);
            this.time += deltaTime;
            if (time < 1) {

                Vec2d vec = new Vec2d(Charge.this.getX() - getX(), Charge.this.getY() - getY());
                if (vec.normalize()) {
                    vec.multiple(5 * originalRadius * deltaTime);
                    setX(getX()+ vec.x);
                    setY(getY()+ vec.y);
                }
            } else {
                end = true;
            }
        }

        @Override
        protected void draw(Graphics2D g, double x, double y, double step) {
            g.setColor(color);
            g.fillOval((int)Math.round(x-radius),(int)Math.round(y-radius),(int)Math.round(radius*2),(int)Math.round(radius*2));
        }

        @Override
        public boolean isEndDrawing() {
            return end || Charge.this.isEndDrawing();
        }
    }
}
