package com.jp.daichi.ex7.operator;

import com.jp.daichi.ex7.Fraction;
import com.jp.daichi.ex7.ZeroDivideException;

import java.math.BigDecimal;

public class Divide implements Operator {

    @Override
    public Fraction operate(Fraction f1, Fraction f2) {
        if (f2.getDenominator().compareTo(new BigDecimal("0")) == 0) {
            throw new ZeroDivideException();
        }
        return new Fraction(f1.getNumerator().multiply(f2.getDenominator()),f1.getDenominator().multiply(f2.getNumerator()));
    }

    @Override
    public int getPriority() {
        return PRIORITY_HIGH;
    }

    @Override
    public String toString() {
        return "÷";
    }

    @Override
    public char getChar() {
        return '/';
    }
}
