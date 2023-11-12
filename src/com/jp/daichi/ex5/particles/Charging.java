package com.jp.daichi.ex5.particles;

import com.jp.daichi.ex4.Vec2d;
import com.jp.daichi.ex5.GameEntity;
import com.jp.daichi.ex5.utils.PositionConverter;
import com.jp.daichi.ex5.utils.RotationConverter;
import com.jp.daichi.ex5.utils.Utils;

import java.awt.*;

public class Charging extends SlaveParticle {
    private final double expansion;

    private boolean end = false;
    private final double originalRadius;
    private double radius = 0;
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
    public Charging(GameEntity entity,double radius,double expansion,Color color) {
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
    public Charging(GameEntity entity,double radius,double expansion, PositionConverter positionConverter,Color color) {
        super(entity, positionConverter, RotationConverter.getEmptyInstance());
        this.originalRadius = radius;
        this.expansion = expansion;
        this.color = color;
    }

    private double lastSpawnTime = 0;

    @Override
    public void tick(double deltaTime) {
        time += deltaTime;
        if (time-lastSpawnTime > 0.1) {
            lastSpawnTime = time;
            double rotation = Math.random()*Math.PI*2;//0~2PIまでの乱数
            double radius = Math.random()*originalRadius/2;//最大半径の1/2までのランダムな半径
            double posRadius = Math.random()*this.radius*0.3+this.radius*1.2;//1.2radius~1.5*radiusまでの乱数
            Vec2d pos = getPosition();
            master.getGame().addParticle(new Child(new Vec2d(pos.x+posRadius*Math.cos(rotation),pos.y+posRadius*Math.sin(rotation)),radius));
        }
        if (time < expansion) {
            radius = originalRadius*time/expansion;
        } else {
            radius = originalRadius+(Math.random()-0.5)*originalRadius*0.05;//半径の1/20で振動
        }

    }

    @Override
    public void draw(Graphics2D g) {
        Vec2d position = getPosition();
        g.setColor(color);
        g.fillOval((int)Math.round(position.x-radius),(int)Math.round(position.y-radius),(int)Math.round(radius*2),(int)Math.round(radius*2));
    }

    @Override
    public boolean isEndDrawing() {
        return end;
    }

    public void end(boolean end) {
        this.end = end;
    }

    private class Child implements Particle {

        private double time = 0;
        private final Vec2d position;
        private final double radius;
        private boolean end = false;
        private Child(Vec2d position,double radius) {
            this.position = position;
            this.radius = radius;
        }

        @Override
        public void tick(double deltaTime) {
            this.time += deltaTime;
            if (time < 1) {
                Vec2d masterPos = getPosition();
                Vec2d vec = new Vec2d(masterPos.x - position.x, masterPos.y - position.y);
                if (vec.normalize()) {
                    vec.multiple(5 * originalRadius * deltaTime);
                    position.x += vec.x;
                    position.y += vec.y;
                }
            } else {
                end = true;
            }
        }

        @Override
        public void draw(Graphics2D g) {
            g.setColor(color);
            g.fillOval((int)Math.round(position.x-radius),(int)Math.round(position.y-radius),(int)Math.round(radius*2),(int)Math.round(radius*2));
        }

        @Override
        public boolean isEndDrawing() {
            return end || Charging.this.isEndDrawing();
        }
    }
}
