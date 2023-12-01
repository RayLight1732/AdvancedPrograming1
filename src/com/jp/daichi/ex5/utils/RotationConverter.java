package com.jp.daichi.ex5.utils;

public interface RotationConverter {
    static RotationConverter getEmptyInstance() {
        return rotation -> rotation;
    }
    double convert(double rotation);
}
