package com.jp.daichi.ex7calcurator;

import com.jp.daichi.ex7calcurator.formula.Formula;
import com.jp.daichi.ex7calcurator.operator.*;

import java.util.Stack;

/**
 * 計算機の計算処理を行うクラス
 */
public class CalculationOperator {
    /*
   イコールが入力されたとき
     なにかしら入力されていたら
        inputEqual=true
        waitingNumber=null
        waitingOperator=null
     前回=が入力されていたら
        演算子とそのあとの数字を式に追加

   数字が入力されたとき
     イコールには続けて入力できない(inputEqual=falseのみ入力可能)
     演算子が待機していたら、その演算子を式に入力し、waitingOperator=null、waitingNumberに代入
     数字が入力されていたら、続けて入力
   演算子が入力されたとき
     数字が待機していたら数字を式に入力し、waitingNumber=null、waitingOperatorに代入
     inputEqualならwaitingOperatorに代入
     waitingOperator!=nullなら、waitingOperatorに代入
     これらにより、何も入力されていない時にwaitingOperatorが入力されることはない

    以上より、=に続けて入力できるのは演算子のみ


     */

    private final History history;
    private Formula head;
    private final Stack<Formula> formulaStack = new Stack<>();
    private boolean inputDot = false;

    /**
     * 新しい計算処理用クラスを作成
     * @param history 履歴
     */
    public CalculationOperator(History history) {
        this(new Formula(),history);
    }

    /**
     * 式がすでに入力されている状態で新しい計算処理用クラスを作成
     * 式はコピーされない
     * @param formula 式
     * @param history 履歴
     */
    public CalculationOperator(Formula formula, History history) {
        this.history = history;
        formulaStack.push(formula);
        head = formula;
    }


    private boolean inputEqual = false;

    /**
     * 符号を反転させる
     */
    public void flipSignum() {
        MathObject tail = formulaStack.peek().getTail();
        if (tail instanceof Fraction fraction) {
            formulaStack.peek().removeTail();
            formulaStack.peek().append(fraction.negative());
        }
    }

    /**
     * 数字(0~9,.,(,))が入力されたとき処理を行う
     * それ以外は何もしない
     * @param n 入力されたchar
     */
    public void input(char n) {
        if (inputEqual) {//イコールが入力されていたら何もしない
            return;
        }
        if (Character.isDigit(n)) {
            if (formulaStack.peek().getTail() instanceof Fraction fraction) {
                formulaStack.peek().removeTail();
                if (inputDot) {
                    append(new Fraction(fraction.getNumerator().toPlainString()+"."+ n));
                    inputDot = false;
                } else {
                    append(new Fraction(fraction.getNumerator().toPlainString() + n));
                }
            } else if (canInputFirstNumber()){
                if (inputDot) {
                    append(new Fraction("0."+n));
                } else {
                    append(new Fraction(Character.toString(n)));
                }
            }
        } else if (n == '.') {
            Fraction fraction = getLastInputFraction();
            if (fraction != null && !containsDot(fraction)) {
                inputDot = true;
                if (canInputFirstNumber()) {
                    formulaStack.peek().append(new Fraction("0"));
                }
            }
        } else if (n == '(') {
            //(が入力されたとき
            //最後が演算子でなく、何かしらが入力されているなら×を挿入
            if (!lastIsOperator() && !topFormulaIsEmpty()) {
                append(Operators.MULTIPLY);
            }
            Formula formula = new Formula();
            append(formula);
            formula.setCloseBracket(false);
            formulaStack.push(formula);
            inputDot = false;
        } else if (n == ')') {
            closeBracket();
        }
    }

    private void closeBracket() {
        //)が入力されたとき
        //最後が演算子なら取り除く
        //取り除いた後、何も入力されていなければ、括弧を無かったことにする
        //そうでないならstackから取り除く
        if (formulaStack.size() >= 2) {
            if (formulaStack.peek().getTail() instanceof Operator) {
                formulaStack.peek().removeTail();
            }
            Formula formula = formulaStack.pop();
            if (formula.isEmpty()) {
                formulaStack.peek().removeTail();//式
            } else {
                formula.setCloseBracket(true);
            }
        }
        inputDot = false;
    }

    private boolean canInputFirstNumber() {
        return topFormulaIsEmpty() || lastIsOperator();
    }

    private boolean topFormulaIsEmpty() {
        return formulaStack.peek().size() == 0;
    }
    private boolean lastIsOperator() {
        return formulaStack.peek().getTail() instanceof Operator;
    }

    private boolean lastIsNumber() {
        return formulaStack.peek().getTail() instanceof Fraction;
    }

    private boolean containsDot(Fraction fraction) {
        return fraction.getDenominator().toPlainString().indexOf('.') != -1;
    }


    private void append(MathObject object) {
        formulaStack.peek().append(object);
        inputDot = false;
    }

    /**
     * 演算子が入力されたとき
     * @param operator 演算子
     */
    public void input(Operator operator) {
        if (formulaStack.peek().isEmpty()) {
           return;
        } else if (formulaStack.peek().getTail() instanceof Operator) {
            //最後が演算子の時置き換え
            formulaStack.peek().removeTail();
            append(operator);
        } else {
            append(operator);
        }
        inputEqual = false;
        inputDot = false;
    }

    /**
     * イコールが入力されたとき
     */
    public void inputEqual() {
        inputEqual(true);
    }

    public void inputEqual(boolean addToHistory) {
        //括弧が開いていれば閉じる
        while (formulaStack.size() != 1) {
            closeBracket();
        }
        if (formulaStack.peek().getTail() instanceof Operator) {
            //最後が演算子の時取り除く
            formulaStack.peek().removeTail();
        }
        if (isInputEqual() && formulaStack.peek().size() >= 2 && formulaStack.peek().getList().get(formulaStack.peek().size()-2) instanceof Operator && formulaStack.peek().getTail() instanceof NumberObject) {//サイズが2以上(演算子と数字/式が入力されているとき)
            append(formulaStack.peek().getList().get(formulaStack.peek().size()-2));//演算子
            append(formulaStack.peek().getList().get(formulaStack.peek().size()-2));//数字/式
        }
        if (!formulaStack.peek().isEmpty()) {//何かしらが入力されていうとき
            if (addToHistory) {
                history.add(head);
            }
            inputDot = false;
            inputEqual = true;
        }
    }

    /**
     * 関数が入力されたとき
     * @param factory 関数
     */
    public void inputSpecialFormula(SpecialFormulaFactory factory) {
        if (inputEqual) {
            if (formulaStack.size() != 1) {//念のため
                throw new  IllegalArgumentException("stack size is not 0");
            } else {
                Formula formula = factory.create();
                formula.append(this.head);
                formula.setCloseBracket(true);
                this.head = new Formula();
                head.append(formula);
                formulaStack.clear();
                formulaStack.push(head);
            }
        } else if (formulaStack.peek().getTail() instanceof Fraction fraction) {
            Formula formula = factory.create();
            formula.append(fraction);
            formulaStack.peek().removeTail();
            append(formula);
        } else {
            if (!lastIsOperator()&& !topFormulaIsEmpty()) {
                append(Operators.MULTIPLY);
            }
            Formula formula = factory.create();
            append(formula);
            formula.setCloseBracket(false);
            formulaStack.push(formula);
            inputDot = false;
        }
    }
    public void clearInputNumber() {
        if (formulaStack.peek().getTail() instanceof Fraction) {
            formulaStack.peek().removeTail();
            inputDot = false;
        }
    }

    /**
     * 計算式を取得
     * @return 計算式
     */
    public Formula getFormula() {
        return head;
    }

    /**
     * 確定されていない演算子を取得
     * @return 演算子
     */
    public Operator getWaitingOperator() {
        if (formulaStack.peek().getTail() instanceof Operator operator) {
            return operator;
        } else {
            return null;
        }
    }

    /**
     * 入力途中の数字を取得
     * @return 入力途中の数字
     */
    public String getWaitingNumber() {
        if (!inputEqual && formulaStack.peek().getTail() instanceof Fraction fraction) {
            return fraction.getNumerator().toPlainString()+(inputDot?".":"");
        } else {
            return null;
        }
    }

    public void clearWaitingNumber() {
        if (formulaStack.peek().getTail() instanceof Fraction) {
            formulaStack.peek().removeTail();
        }
    }

    /**
     * 演算子なら入力を取り消す
     * 数字なら1の位を消す
     */
    public void delete() {
        if (!inputEqual) {
            MathObject mathObject = formulaStack.peek().getTail();
            /*
            tail instanceof Formula size == 0:(が入力されているので親から削除

            tail instanceof Fraction 一桁減らす
            tail instanceof Operator 消す

             */
            if (mathObject == null) {
                Formula formula = formulaStack.peek();
                Formula holder = getHolder(formula);
                if (holder != null) {
                    holder.removeTail();
                    if (formulaStack.size() >= 2) {
                        formulaStack.pop();
                    }
                }
            }
            if (mathObject instanceof Fraction fraction) {
                if (inputDot) {
                    inputDot = false;
                } else {
                    formulaStack.peek().removeTail();
                    String str = fraction.toPlainString();
                    if ((str.startsWith("-") && str.length() == 2)||str.length()==1) {//一桁の時
                        return;//消したら何も残らない
                    } else {
                        formulaStack.peek().append(new Fraction(str.substring(0,str.length()-1)));
                    }
                }
            } else if (mathObject instanceof Operator) {
                formulaStack.peek().removeTail();
            } else if (mathObject instanceof Formula formula) {
                if (formula.isEmpty()) {
                    Formula holder = getHolder(formula);
                    if (holder != null) {
                        holder.removeTail();
                    }
                } else {
                    formula.setCloseBracket(false);
                    formulaStack.push(formula);
                }
            }
        }
    }


    public Formula getHolder(NumberObject numberObject) {
        return getHolder(head,numberObject);
    }

    private Formula getHolder(Formula formula,NumberObject numberObject) {
        for (MathObject mathObject:formula.getList()) {
            if (mathObject == numberObject) {
                return formula;
            } else if (mathObject instanceof Formula formula2) {
                Formula result = getHolder(formula2,numberObject);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 最後にイコールが入力されたか
     * @return 最後にイコールが入力されたならtrue
     */
    public boolean isInputEqual() {
        return inputEqual;
    }

    /**
     * 最後に入力した数字を取得
     * @return 最後に入力した数字 数字以外が入力されていたらnull
     */
    public Fraction getLastInputFraction() {
        if (formulaStack.peek().getTail() instanceof Fraction fraction) {
            return fraction;
        } else {
            return null;
        }
    }

    public int getStackSize() {
        return formulaStack.size();
    }

    public Formula getTopBracketHolder() {
        if (getStackSize() >= 2) {
            return formulaStack.peek();
        } else {
            return null;
        }
    }

    public interface SpecialFormulaFactory {
        Formula create();
    }
}
