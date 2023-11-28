package com.jp.daichi.ex7calcurator.formula;

import com.jp.daichi.ex7calcurator.Fraction;
import com.jp.daichi.ex7calcurator.operator.Operators;

/**
 * 二乗
 */
public class Square extends Formula {
    @Override
    public Fraction calculate() {
        Fraction fraction = super.calculate();
        return Operators.MULTIPLY.operate(fraction,fraction);
    }

    @Override
    public String toPlainString(boolean expandChild) {
        if (size()==1) {
            return super.toPlainString(expandChild)+"^2";
        } else {
            return "("+super.toPlainString(expandChild)+(isCloseBracket() ? getCloseBracketSymbol() : "")+"^2";
        }
    }

    @Override
    public String getCloseBracketSymbol() {
        return ")";
    }

    @Override
    protected String toPlainString(boolean expandChild, boolean isChild) {
        return this.toPlainString(expandChild);
    }
}
