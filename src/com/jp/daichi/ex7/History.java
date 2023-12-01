package com.jp.daichi.ex7;

import com.jp.daichi.ex7.formula.Formula;

import java.util.ArrayList;
import java.util.List;

/**
 * 履歴を管理するクラス
 */
public class History {

    private final List<Formula> formulas = new ArrayList<>();

    /**
     * 履歴を追加
     * @param formula 追加する計算式(SyntaxErrorが出ないものとする)
     */
    public void add(Formula formula) {
        formulas.add(formula.clone());
    }

    /**
     * 履歴に追加されている式のコピーを返す
     * 式自体もコピーされているため、編集しても履歴には反映されない
     * @return 履歴に追加されている式のコピー
     */
    public List<Formula> getFormulas() {
        return formulas.stream().map(Formula::clone).toList();
    }
}
