package com.jp.daichi.ex5.particles;

import java.awt.*;

public interface Particle {

    void tick(double deltaTime);

    void draw(Graphics2D g);

    /**
     * 描画が終わっているか
     * @return 描画が終わっているならtrue
     */
    boolean isEndDrawing();
}
