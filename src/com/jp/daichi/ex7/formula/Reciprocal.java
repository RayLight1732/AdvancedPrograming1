package com.jp.daichi.ex7.formula;

import com.jp.daichi.ex7.Fraction;
import com.jp.daichi.ex7.operator.Operators;

import java.math.BigDecimal;

/**
 * 逆数
 */
public class Reciprocal extends Formula {

    @Override
    public Fraction calculate() {
        Fraction fraction = super.calculate();
        if (fraction != null) {
            return Operators.DIVIDE.operate(new Fraction(BigDecimal.ONE),fraction);
        } else {
            return null;
        }
    }


    @Override
    public String toPlainString(boolean expandChild) {
        if (size()==1) {
            return "1/"+super.toPlainString(expandChild);//たとえ関数でも、括弧はplainStringがつける
        } else {
            return "1/("+super.toPlainString(expandChild)+(isCloseBracket() ? ")" : "");
        }
    }

    @Override
    protected String toPlainString(boolean expandChild, boolean isChild) {
        return this.toPlainString(expandChild);
    }
}
