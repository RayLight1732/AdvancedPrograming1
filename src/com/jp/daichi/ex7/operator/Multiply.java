package com.jp.daichi.ex7.operator;

import com.jp.daichi.ex7.Fraction;

public class Multiply implements Operator {
    @Override
    public Fraction operate(Fraction f1, Fraction f2) {
        Fraction result = new Fraction(f1.getNumerator().multiply(f2.getNumerator()),f1.getDenominator().multiply(f2.getDenominator()));
        result.reduction();
        return result;
    }

    @Override
    public int getPriority() {
        return PRIORITY_HIGH;
    }

    @Override
    public String toString() {
        return "×";
    }

    @Override
    public char getChar() {
        return '*';
    }
}
