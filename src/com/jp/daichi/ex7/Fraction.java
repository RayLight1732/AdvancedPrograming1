package com.jp.daichi.ex7;

import com.jp.daichi.ex7.operator.Operators;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Fraction implements NumberObject {

    public static final Fraction PI = new Fraction(BigDecimal.valueOf(Math.PI));

    public static final Fraction E = new Fraction(BigDecimal.valueOf(Math.E));
    public static BigDecimal gcm(BigDecimal a, BigDecimal b) {
        BigDecimal temp;
        while ((temp = a.remainder(b)).compareTo(BigDecimal.ZERO) != 0) {
            a = b;
            b = temp;
        }
        return b;
    }


    private BigDecimal numerator;//分子
    private BigDecimal denominator;//分母

    public Fraction(String number) {
        this(new BigDecimal(number));
    }

    public Fraction(BigDecimal number) {
        this(number,BigDecimal.ONE);
    }
    public Fraction(BigDecimal numerator, BigDecimal denominator) {
        if (denominator.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("denominator must not be 0");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public BigDecimal getNumerator() {
        return numerator;
    }
    public BigDecimal getDenominator() {
        return denominator;
    }

    public BigDecimal toBigDecimal() {
        if (isInteger()) {
            return numerator;
        } else {
            try {
                return numerator.divide(denominator);
            } catch (ArithmeticException e) {
                return numerator.divide(denominator,Main.maxCalculateScale,RoundingMode.FLOOR);
            }
        }
    }


    public void reduction() {
        int scaleByPowerOfTen = Math.max(
                numerator.scale(),
                denominator.scale()
        );
        BigDecimal tempNumerator = numerator.scaleByPowerOfTen(scaleByPowerOfTen);
        BigDecimal tempDenominator = denominator.scaleByPowerOfTen(scaleByPowerOfTen);
        BigDecimal gcm;
        while ((gcm = gcm(tempNumerator, tempDenominator)).compareTo(BigDecimal.ONE) != 0) {
            tempNumerator = tempNumerator.divide(gcm);
            tempDenominator = tempDenominator.divide(gcm);
        }
        this.numerator = tempNumerator;
        this.denominator = tempDenominator;
    }

    public boolean isInteger() {
        return denominator.compareTo(BigDecimal.ONE)==0;
    }

    @Override
    public String toString() {
        return toPlainString(Main.maxDisplayScale);
    }

    public String toEngineeringString() {
        BigDecimal result = toBigDecimal().stripTrailingZeros();
        return result.toEngineeringString();
    }

    public String toPlainString() {
        BigDecimal result = toBigDecimal().stripTrailingZeros();
        return result.toPlainString();
    }

    public String toPlainString(int limitScale) {
        BigDecimal bd = toBigDecimal().stripTrailingZeros();
        if (Math.abs(bd.scale()) > limitScale) {
            return bd.toEngineeringString();
        } else {
            return toPlainString();
        }
    }

    /**
     * 分数の形で出力 a/b
     * @return 分数
     */
    public String toStringAsFraction() {
        return numerator.toPlainString()+"/"+denominator.toPlainString();
    }

    /**
     * -thisのFractionを返す
     * @return -this
     */
    public Fraction negative() {
        return new Fraction(getNumerator().negate(),getDenominator());
    }

    /**
     * 10^scale倍した新しいFractionを返す
     * @param scale スケール
     * @return 10^scale倍
     */
    public Fraction scaleByPowerOfTen(int scale) {
        Fraction fraction = new Fraction("1" + "0".repeat(Math.max(0, scale)));
        if (scale >= 0) {
            return Operators.MULTIPLY.operate(this,fraction);
        } else {
            return Operators.DIVIDE.operate(this,fraction);
        }
    }

    //正の時(0を含む)
    public boolean isPositive() {
        return toBigDecimal().compareTo(BigDecimal.ZERO) >= 0;
    }


    @Override
    public Fraction getFraction() {
        return this;
    }
}
