package com.jp.daichi.ex7.formula;

import com.jp.daichi.ex7.FormulaSyntaxException;
import com.jp.daichi.ex7.Fraction;
import com.jp.daichi.ex7.MathObject;
import com.jp.daichi.ex7.NumberObject;
import com.jp.daichi.ex7.operator.Operator;

import java.util.ArrayList;
import java.util.List;

/**
 * 式の一つのまとまり
 */
public class Formula implements NumberObject,Cloneable {


    protected List<MathObject> list = new ArrayList<>();
    private boolean isCloseBracket = true;


    @Override
    public Formula clone() {
        try {
            Formula clone = (Formula) (super.clone());
            clone.list = new ArrayList<>();
            for (MathObject mathObject : list) {
                if (mathObject instanceof Formula f) {
                    clone.list.add(f.clone());
                } else {
                    clone.list.add(mathObject);
                }
            }
            return clone;
        } catch (CloneNotSupportedException ignore) {
            return null;
        }
    }

    public void append(MathObject mathObject) {
        if (mathObject != null) {
            list.add(mathObject);
        }
    }

    /**
     * 計算を行う
     * @return 計算結果
     */
    public Fraction calculate() {
        Formula formula = this;
        while (formula.list.size() > 1) {
            int maxPriority = Integer.MIN_VALUE;
            int maxPriorityIndex = -1;
            for (int i = 0;i < formula.list.size();i++) {
                if (formula.list.get(i) instanceof Operator op) {
                    if (op.getPriority() > maxPriority) {
                        maxPriority = op.getPriority();
                        maxPriorityIndex = i;
                    }
                }
            }
            if (maxPriorityIndex == -1) {//演算子が見つからなかったとき
                throw new FormulaSyntaxException();
            } else {
                Operator operator = (Operator) formula.list.get(maxPriorityIndex);
                Formula newFormula = new Formula();
                for (int i = 0;i < maxPriorityIndex-1;i++) {
                    newFormula.append(formula.list.get(i));
                }
                NumberObject n1;
                NumberObject n2;
                try {
                    n1 = (NumberObject)formula.list.get(maxPriorityIndex-1);
                    n2 = (NumberObject)formula.list.get(maxPriorityIndex+1);
                } catch (ClassCastException | IndexOutOfBoundsException e) {
                    throw new FormulaSyntaxException();
                }

                newFormula.append(operator.operate(n1.getFraction(),n2.getFraction()));
                for (int i = maxPriorityIndex+2;i < formula.list.size();i++) {
                    newFormula.append(formula.list.get(i));
                }
                formula = newFormula;
            }
        }
        if (formula.list.size() == 0) {
            return null;
        } else {
            return ((NumberObject) formula.list.get(0)).getFraction();
        }
    }

    @Override
    public String toString() {
        return calculate().toPlainString();
    }

    /**
     * 式を単純な文字列に変換する
     * @param expandChild 子に式が含まれている場合、その式も展開するか
     * @return 文字列
     */
    public String toPlainString(boolean expandChild) {
        StringBuilder builder = new StringBuilder();
        for (MathObject o : list) {
            if (!builder.isEmpty()) {
                builder.append(" ");
            }
            append(builder,o,expandChild,true);
        }
        return builder.toString();
    }

    /**
     * builderの後にMathObjectを追加する
     * @param builder 対象となるbuilder
     * @param o 対象となるMathObject
     * @param expandChild MathObjectが式の場合、その子も展開するか
     * @param isChild MathObjectが子であるかどうか
     */
    protected void append(StringBuilder builder,MathObject o,boolean expandChild,boolean isChild) {
        if (expandChild && o instanceof Formula formula) {
            builder.append(formula.toPlainString(expandChild,isChild));
        } else if (o instanceof NumberObject no) {
            Fraction fraction = no.getFraction();
            if (!fraction.isPositive()) {
                builder.append("(");
                builder.append(no.getFraction());
                builder.append(")");
            } else {
                builder.append(no.getFraction());
            }
        } else {
            builder.append(o);
        }
    }

    /**
     * 式を単純な文字列に変換する
     * @param expandChild 子に式が含まれている場合、その式も展開するか
     * @param isChild この式が子であるか
     * @return 文字列
     */
    protected String toPlainString(boolean expandChild,boolean isChild) {
        if (isChild) {
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            builder.append(toPlainString(expandChild));
            if (isCloseBracket) {
                builder.append(")");
            }
            return builder.toString();
        } else {
            return toPlainString(expandChild);
        }
    }

    @Override
    public Fraction getFraction() {
        return calculate();
    }

    /**
     * 括弧が閉じられているかどうか
     * 式の計算には影響しない装飾的な括弧
     * @return 閉じられているならtrue
     */
    public boolean isCloseBracket() {
        return isCloseBracket;
    }

    /**
     * 括弧が閉じられているかを設定する
     * @param closeBracket 括弧が閉じられているか
     */
    public void setCloseBracket(boolean closeBracket) {
        isCloseBracket = closeBracket;
    }

    /**
     * この式に含まれている演算子も含めた要素の数を返す。
     * @return 要素の数(演算子も含む)
     */
    public int size() {
        return list.size();
    }

    /**
     * この式に含まれている要素のリストのコピーを返す
     * @return この式に含まれている要素のコピー
     */
    public List<MathObject> getList() {
        return new ArrayList<>(list);
    }

    /**
     * 最後尾の要素を削除する
     */
    public void removeTail() {
        if (list.size() >= 1) {
            list.remove(list.size()-1);
        }
    }

    /**
     * 最後尾の要素を取得する
     * @return 最後尾の要素
     */
    public MathObject getTail() {
        if (list.size() >= 1) {
            return list.get(list.size()-1);
        } else {
            return null;
        }
    }

    /**
     * この式が空かどうか
     * @return 空の場合true
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 閉じ括弧の記号を取得する
     * @return 閉じ括弧
     */
    public String getCloseBracketSymbol() {
        return ")";
    }

}
