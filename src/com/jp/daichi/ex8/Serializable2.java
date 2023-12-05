package com.jp.daichi.ex8;

import java.io.Serializable;

public interface Serializable2 extends Serializable {
    /**
     * デシリアライズされたとき
     */
    void onDeserialized();
}
