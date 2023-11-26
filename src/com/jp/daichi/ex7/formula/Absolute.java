package com.jp.daichi.ex7.formula;

import com.jp.daichi.ex7.Fraction;
import com.jp.daichi.ex7.MathObject;
import com.jp.daichi.ex7.NumberObject;

/**
 * 絶対値
 */
public class Absolute extends Formula {
    @Override
    public Fraction calculate() {
        Fraction fraction = super.calculate();
        if (fraction != null) {
            if (fraction.isPositive()) {//正
                return fraction;
            } else {//負
                return fraction.negative();
            }
        } else {
            return null;
        }
    }


    @Override
    public String toPlainString(boolean expandChild) {
        StringBuilder builder = new StringBuilder();
        builder.append("|");
        for (MathObject o : list) {
            if (builder.toString().length() >= 2) {//|と何かしらの数字,式の入力が終わったとき
                builder.append(" ");
            }
            append(builder,o,expandChild,false);
        }

        if (isCloseBracket()) {
            builder.append("|");
        }
        return builder.toString();
    }

    @Override
    protected String toPlainString(boolean expandChild, boolean isChild) {
        return this.toPlainString(expandChild);
    }

    @Override
    public String getCloseBracketSymbol() {
        return "|";
    }
}
