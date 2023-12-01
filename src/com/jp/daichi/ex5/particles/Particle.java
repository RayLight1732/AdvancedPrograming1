package com.jp.daichi.ex5.particles;

import com.jp.daichi.ex5.GameObject;

public interface Particle extends GameObject {


    /**
     * 描画が終わっているか
     * @return 描画が終わっているならtrue
     */
    boolean isEnd();

    void setEnd(boolean end);
}
