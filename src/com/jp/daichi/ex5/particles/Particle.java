package com.jp.daichi.ex5.particles;

import com.jp.daichi.ex4.RotationalObject;
import com.jp.daichi.ex5.AGameEntity;
import com.jp.daichi.ex5.Game;
import com.jp.daichi.ex5.GameObject;

import java.awt.*;

public interface Particle extends GameObject {


    /**
     * 描画が終わっているか
     * @return 描画が終わっているならtrue
     */
    boolean isEnd();

    void setEnd(boolean end);
}
