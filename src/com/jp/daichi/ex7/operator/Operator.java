package com.jp.daichi.ex7.operator;

import com.jp.daichi.ex7.Fraction;
import com.jp.daichi.ex7.MathObject;

/**
 * 演算子
 * 前と後ろで何かする
 */
public interface Operator extends MathObject {

    /**
     * 普通の優先度
     */
    int PRIORITY_NORMAL = 0;
    /**
     * 高い優先度
     */
    int PRIORITY_HIGH = 10;
    /**
     * 最高の優先度
     */
    int PRIORITY_HIGHEST = 20;

    /**
     * charが何も表さないとき
     */
    char NULL = ';';

    /**
     * 二つの値を使用して計算した結果を返す
     * @param f1 値1
     * @param f2 値2
     * @return 計算結果
     */
    Fraction operate(Fraction f1, Fraction f2);

    /**
     * 計算の優先度を返す
     * @return 計算の優先度
     */
    int getPriority();

    /**
     * このオペレーターを指し示すchar
     * @return char
     */
    char getChar();
}
