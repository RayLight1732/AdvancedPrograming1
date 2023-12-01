package com.jp.daichi.ex7.formula;

import com.jp.daichi.ex7.Fraction;
import com.jp.daichi.ex7.operator.Operators;

import java.math.BigDecimal;

public abstract class Newton {
    public abstract Fraction f(Fraction f);
    public abstract Fraction fDash(Fraction f);

    /**
     * 初期値をfirstとしてニュートン法で計算
     * @param first 初期値
     * @param scale 最大の有効桁数
     * @return ニュートン法を行った結果
     */
    public Fraction calculate(Fraction first, int scale) {
        Fraction n = first;
        Fraction nPlus1 = null;
        StringBuilder builder = new StringBuilder("0.");
        for (int i = 0;i < scale-1;i++) {
            builder.append("0");
        }
        builder.append("1");
        BigDecimal compare = new BigDecimal(builder.toString());
        do {
            if (nPlus1 != null) {
                n = nPlus1;
            }
            nPlus1 = Operators.SUBTRACT.operate(n,Operators.DIVIDE.operate(f(n),fDash((n))));
        } while (compare(Operators.SUBTRACT.operate(n,nPlus1).toBigDecimal(),compare) > 0 );
        return nPlus1;
    }

    private int compare(BigDecimal b1,BigDecimal b2) {
        return b1.abs().compareTo(b2);
    }

}
