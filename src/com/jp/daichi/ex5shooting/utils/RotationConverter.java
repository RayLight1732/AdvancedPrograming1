package com.jp.daichi.ex5shooting.utils;

public interface RotationConverter {
    static RotationConverter getEmptyInstance() {
        return rotation -> rotation;
    }
    double convert(double rotation);
}
