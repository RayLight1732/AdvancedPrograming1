package com.jp.daichi.ex7calcurator.operator;

import com.jp.daichi.ex7calcurator.Fraction;

import java.math.BigDecimal;

public class Pow implements Operator {

    @Override
    public Fraction operate(Fraction f1, Fraction f2) {
        Fraction result = new Fraction(BigDecimal.valueOf(Math.pow(f1.toBigDecimal().doubleValue(),f2.toBigDecimal().doubleValue())));
        result.reduction();
        return result;
    }

    @Override
    public int getPriority() {
        return PRIORITY_HIGHEST;
    }

    @Override
    public char getChar() {
        return '^';
    }

    @Override
    public String toString() {
        return "^";
    }

}
