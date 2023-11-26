package com.jp.daichi.ex7;

import com.jp.daichi.ex7.formula.Formula;
import com.jp.daichi.ex7.operator.Operator;
import com.jp.daichi.ex7.operator.Operators;

public class Calculator {
    private final History history;
    private Formula memory;
    private CalculationOperator cOperator;
    private boolean displayAsFraction = false;

    public Calculator() {
        this.history = new History();
        clearMemory();
        this.cOperator = new CalculationOperator(history);
    }

    /**
     * 現在処理を行っているCalculatorOperatorを取得する
     * @return 現在処理を行っているCalculatorOperator
     */
    public CalculationOperator getOperator() {
        return cOperator;
    }

    /**
     * 何かしら入力されているならそれを削除
     * 入力されていないなら、これまでの計算結果含め削除
     * メモリはクリアされない
     */
    public void clear() {
        if (cOperator.getWaitingNumber() != null) {
            cOperator.clearInputNumber();
        } else {
            cOperator = new CalculationOperator(history);
        }
    }

    /**
     * clearメソッドが呼ばれたときにすべて削除するかどうか
     * @return すべて削除するならtrue
     */
    public boolean doClearAll() {
        return cOperator.getWaitingNumber() == null;
    }

    /**
     * メモリをクリアする
     */
    public void clearMemory() {
        memory = new Formula();
    }

    /**
     * 履歴を取得する
     * @return 履歴
     */
    public History getHistory() {
        return history;
    }

    /**
     * 指定した履歴を読み込む
     * @param index 読み込む履歴のindex
     */
    public void loadHistory(int index) {
        this.cOperator = new CalculationOperator(history.getFormulas().get(index),history);
    }

    public void saveMemory() {
        if (cOperator.isInputEqual()) {
            memory = cOperator.getFormula().clone();
        } else if (cOperator.getWaitingNumber() != null && !cOperator.getWaitingNumber().isEmpty()) {
            memory = new Formula();
            memory.append(new Fraction(cOperator.getWaitingNumber()));
        }
    }

    public void loadMemory() {
        if (!memory.isEmpty()) {
            Formula formula = new Formula();
            formula.append(memory.clone());
            cOperator = new CalculationOperator(formula, history);
        }
    }

    public void addToMemory() {
        calcWithMemory(Operators.ADD);
    }

    public void subtractFromMemory() {
        calcWithMemory(Operators.SUBTRACT);
    }


    public void deleteInputNumber() {
        cOperator.clearWaitingNumber();
    }

    private void calcWithMemory(Operator operator) {

        NumberObject input = null;
        if (cOperator.isInputEqual()) {
            input = cOperator.getFormula().clone();
        } else if (cOperator.getLastInputFraction() != null) {
            input = cOperator.getLastInputFraction();
        }
        if (input != null) {
            if (memory.size() == 0) {//初めてメモリーに追加するとき
                if (operator == Operators.SUBTRACT) {//引き算なら符号反転
                    input = input.getFraction().negative();//計算結果を代入
                }
                memory.append(input.getFraction());
            } else {
                memory.append(operator);
                memory.append(input.getFraction());
            }
        }
        System.out.println(memory.toPlainString(true));
    }

    public boolean displayAsFraction() {
        return displayAsFraction;
    }

    public void setDisplayAsFraction(boolean displayAsFraction) {
        this.displayAsFraction = displayAsFraction;
    }

    /**
     * 今まで入力された計算式を表示するディスプレイ用の文字列を取得
     * @return 今まで入力された計算式を表示するディスプレイ用の文字列
     */
    public String getTopDisplay() {
        if (cOperator.isInputEqual()){
            return cOperator.getFormula().toPlainString(true)+" =";
        } else {
            Formula formula = cOperator.getFormula().clone();
            Formula bottom = formula;
            while (bottom.getTail() instanceof Formula tailFormula) {
                bottom = tailFormula;
            }
            boolean removeTail;
            if (formula == bottom) {//一番上のとき
                removeTail = bottom.getTail() instanceof Fraction;
            } else {//階層が下の時
                removeTail = !bottom.isCloseBracket() && bottom.getTail() instanceof Fraction;//括弧が閉じておらず、最後尾が数字のとき
            }
            if (removeTail) {
                bottom.removeTail();
                return formula.toPlainString(true);
            } else {
                return cOperator.getFormula().toPlainString(true);
            }
        }
    }

    /**
     * 入力や計算結果を表示するディスプレイ用の文字列を取得
     * @return 入力や計算結果を表示するディスプレイ用の文字列
     */
    public String getBottomDisplay() {
        if (cOperator.isInputEqual()) {
            if (displayAsFraction()) {
                Fraction fraction = cOperator.getFormula().calculate();
                fraction.reduction();
                return fraction.toStringAsFraction();
            } else {
                return cOperator.getFormula().calculate().toString();
            }
        } else if (cOperator.getWaitingNumber() != null){
            return cOperator.getWaitingNumber();
        } else {
            return "";
        }
    }
}
