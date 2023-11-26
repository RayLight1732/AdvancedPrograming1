package com.jp.daichi.ex7.operator;

import com.jp.daichi.ex7.Fraction;
import com.jp.daichi.ex7.MathObject;

/**
 * 演算子
 * 前と後ろで何かする
 */
public interface Operator extends MathObject {

    int PRIORITY_NORMAL = 0;
    int PRIORITY_HIGH = 10;
    int PRIORITY_HIGHEST = 20;

    char NULL = ';';

    Fraction operate(Fraction f1, Fraction f2);

    int getPriority();

    char getChar();
}
