package com.jp.daichi.ex5.utils;

import com.jp.daichi.ex4.Vec2d;

public interface PositionConverter {
    static PositionConverter getEmptyInstance() {
        return vec2d-> vec2d;
    }

    Vec2d convert(Vec2d vec2d);
}
