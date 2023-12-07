package com.jp.daichi.ex7.operator;

import com.jp.daichi.ex7.Fraction;

import java.math.BigDecimal;

/**
 * 足し算
 */
public class Add implements Operator {

    @Override
    public Fraction operate(Fraction f1,Fraction f2) {
        if (f1.getDenominator().compareTo(f2.getDenominator()) == 0) {
            return new Fraction(f1.getNumerator().add(f2.getNumerator()),f1.getDenominator());
        } else {
            BigDecimal numerator = f1.getNumerator().multiply(f2.getDenominator()).add(f2.getNumerator().multiply(f1.getDenominator()));
            BigDecimal denominator = f1.getDenominator().multiply(f2.getDenominator());
            Fraction result = new Fraction(numerator,denominator);
            result.reduction();
            return result;
        }
    }

    @Override
    public String toString() {
        return "+";
    }

    @Override
    public int getPriority() {
        return PRIORITY_NORMAL;
    }

    @Override
    public char getChar() {
        return '+';
    }
}
