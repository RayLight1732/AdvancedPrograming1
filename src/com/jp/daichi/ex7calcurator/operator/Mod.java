package com.jp.daichi.ex7calcurator.operator;

import com.jp.daichi.ex7calcurator.Fraction;

public class Mod implements Operator {
    @Override
    public Fraction operate(Fraction f1, Fraction f2) {
        Fraction fraction = Operators.DIVIDE.operate(f1,f2);
        Fraction result = new Fraction(fraction.getNumerator().remainder(fraction.getDenominator()));
        result.reduction();
        return result;
    }

    @Override
    public int getPriority() {
        return PRIORITY_HIGHEST;
    }

    @Override
    public char getChar() {
        return NULL;
    }

    @Override
    public String toString() {
        return "mod";
    }
}
