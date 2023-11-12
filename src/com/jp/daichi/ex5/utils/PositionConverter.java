package com.jp.daichi.ex5.utils;

import com.jp.daichi.ex4.Vec2d;

public interface PositionConverter {
    static PositionConverter getEmptyInstance() {
        return Vec2d::new;
    }

    Vec2d convert(double x,double y);
}
