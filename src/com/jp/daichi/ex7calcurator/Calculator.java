package com.jp.daichi.ex7calcurator;

import com.jp.daichi.ex7calcurator.formula.Formula;
import com.jp.daichi.ex7calcurator.operator.Operator;
import com.jp.daichi.ex7calcurator.operator.Operators;

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
        cOperator.inputEqual(false);
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
        return getTopDisplay(cOperator);
    }

    private String getTopDisplay(CalculationOperator cOperator) {
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
        return getBottomDisplay(cOperator);
    }

    private String getBottomDisplay(CalculationOperator cOperator) {
        try {
            if (cOperator.isInputEqual()) {
                if (displayAsFraction()) {
                    Fraction fraction = cOperator.getFormula().calculate();
                    fraction.reduction();
                    return fraction.toStringAsFraction();
                } else {
                    return cOperator.getFormula().calculate().toString();
                }
            } else if (cOperator.getWaitingNumber() != null) {
                return cOperator.getWaitingNumber();
            } else {
                return "";
            }
        } catch (Exception e) {
            return "エラー";
        }
    }

    /**
     * 履歴に記録されている式のString表現を取得する
     * @param index 履歴のインデックス
     * @return return[0]にtopDisplay,return[1]にbottomDisplay
     */
    public String[] getHistoryDisplay(int index) {
        CalculationOperator operator = new CalculationOperator(history.getFormulas().get(index),new History());
        operator.inputEqual();
        String[] result = new String[2];
        result[0] = getTopDisplay(operator);
        result[1] = getBottomDisplay(operator);
        return result;
    }
}
