package com.jp.daichi.ex5;

import com.jp.daichi.ex5.base.enemy.Enemy;
import com.jp.daichi.ex5.particles.Particle;
import com.jp.daichi.ex5.stage.Stage;
import com.jp.daichi.ex5.stage.StageFlow;

import java.util.ArrayList;
import java.util.List;

public class SimpleGame implements Game {

    private final List<GameEntity> entityList = new ArrayList<>();//登録されているエンティティのリスト
    private final List<Particle> particles = new ArrayList<>();//登録されているパーティクルのリスト
    private GameState state = GameState.IN_GAME;
    private StageFlow flow;
    private Player player;
    private Stage stage;
    private double playerDeathTime = 0;
    private int score;
    private final List<GameEntity> markedToRemove = new ArrayList<>();

    @Override
    public List<GameEntity> getEntities() {
        return new ArrayList<>(entityList);//コピーして返却
    }

    @Override
    public void removeEntity(GameEntity entity) {
        markedToRemove.add(entity);
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
        LivingEntity player = getPlayer();
        if (player != null && player.isDead()) {
            playerDeathTime += deltaTime;
        }
        if (playerDeathTime > 2) {
            state = GameState.GAME_OVER;
            return;
        }
        if (flow != null) {
            if (stage == null) {
                stage = flow.next();
            }
            if (stage != null) {
                if (!stage.started()) {
                    stage.startStage(this);
                }
                stage.tick(deltaTime);
            }
        }
        if (stage != null && stage.isEnded()) {
            stage = null;
        }
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
        for (GameEntity e : entities) {
            e.tick(deltaTime);//ティック処理
        }
        particles.removeIf(Particle::isEnd);
        for (Particle particle:new ArrayList<>(particles)) {
            particle.tick(deltaTime);
        }

        List<GameEntity> succeeded = new ArrayList<>();//削除に成功したもの
        for (GameEntity entity:markedToRemove) {
            if (this.entityList.remove(entity)) {
                succeeded.add(entity);
            }
        }
        markedToRemove.clear();
        for (GameEntity entity:succeeded) {
            entity.onRemoved();
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
    public void setStageFlow(StageFlow stageFlow) {
        this.flow = stageFlow;
    }

    @Override
    public LivingEntity getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void killedBy(GameEntity victim, GameEntity attacker) {
        if (attacker instanceof Player && victim instanceof Enemy enemy) {
            setScore(getScore()+enemy.getScore());
        }
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }
}
