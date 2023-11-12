package com.jp.daichi.ex5;

import com.jp.daichi.ex5.particles.Particle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SimpleGame implements Game {

    private final List<GameEntity> entityList = new ArrayList<>();//登録されているエンティティのリスト
    private List<Particle> particles = new ArrayList<>();//登録されているパーティクルのリスト
    private GameState state;
    @Override
    public List<GameEntity> getEntities() {
        return new ArrayList<>(entityList);//コピーして返却
    }

    @Override
    public boolean removeEntity(GameEntity entity) {
        boolean result = entityList.remove(entity);//削除
        if (result) {
            entity.onRemove();
        }
        return result;
    }

    @Override
    public void addEntity(GameEntity entity) {
        entityList.add(entity);
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public void tick(double deltaTime) {
        state = GameState.CollisionTick;//コリジョンティック開始
        List<GameEntity> entities = getEntities();//登録されているオブジェクトのリスト
        for (GameEntity e : entities) {
            e.collisionTick(deltaTime);//衝突前処理を行う
        }
        //一度衝突判定したペアは二度目はやらないようなループ
        loop1:for (int i = 0; i < entities.size();i++) {
            GameEntity e1 = entities.get(i);//判定対象オブジェクト1
            for (int i2 = i+1; i2 < entities.size();i2++) {
                GameEntity e2 = entities.get(i2);//判定対象オブジェクト2
                if (!e1.isVisible()) {
                    continue loop1;
                }
                if (!e2.isVisible()) {
                    continue;
                }
                if (e1.getCollisionRulePriority() > e2.getCollisionRulePriority()) {
                    if (e1.doCollision(e2) && e1.isCollide(e2)) {
                        e1.collideWith(e2);
                        e2.collideWith(e1);
                    }
                } else {
                    if (e2.doCollision(e1) && e2.isCollide(e1)) {
                        e1.collideWith(e2);
                        e2.collideWith(e1);
                    }
                }
            }
        }
        state = GameState.GameTick;//ゲームティック開始
        for (GameEntity e : entities) {
            e.tick(deltaTime);//ティック処理
        }
        particles = particles.stream().filter(p->!p.isEndDrawing()).collect(Collectors.toList());
        for (Particle particle:new ArrayList<>(particles)) {
            particle.tick(deltaTime);
        }

    }

    @Override
    public int getWidth() {
        return 1920;
    }

    @Override
    public int getHeight() {
        return 1080;
    }

    @Override
    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    @Override
    public List<Particle> getParticles() {
        return new ArrayList<>(particles);
    }

    @Override
    public void drawEntity(Graphics2D g) {
        getEntities().forEach(e->e.draw(g));
    }

    @Override
    public void drawParticle(Graphics2D g) {
        getParticles().forEach(p->p.draw(g));
    }


}
