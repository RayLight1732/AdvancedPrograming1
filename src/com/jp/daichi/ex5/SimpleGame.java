package com.jp.daichi.ex5;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleGame implements Game {

    private final List<GameEntity> entityList = new ArrayList<>();//登録されているエンティティのリスト
    private GameState state;
    private JPanel panel;
    @Override
    public List<GameEntity> getEntities() {
        return new ArrayList<>(entityList);//コピーして返却
    }

    @Override
    public boolean removeEntity(GameEntity entity) {
        return entityList.remove(entity);//削除
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
                    System.out.println("not visible");
                    System.out.println(e1.getClass());
                    continue loop1;
                }
                if (!e2.isVisible()) {
                    System.out.println("not visible2");
                    continue;
                }
                if (e1.doCollision(e2) || e2.doCollision(e1)) {
                    if (e1.isCollide(e2)) {
                        e1.collideWith(e2);//衝突したことを通知
                        e2.collideWith(e1);//衝突したことを通知
                    }
                }
            }
        }
        state = GameState.GameTick;//ゲームティック開始
        for (GameEntity e : entities) {
            e.tick(deltaTime);//ティック処理
        }
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public int getWidth() {
        return panel.getWidth();
    }

    @Override
    public int getHeight() {
        return panel.getHeight();
    }

}
