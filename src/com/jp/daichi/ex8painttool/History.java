package com.jp.daichi.ex8painttool;

public interface History {
    /**
     * 現在の履歴の状態に新しい物を追加する
     * @param name 履歴の名前(重複可能)
     * @param obj 追加するオブジェクト
     * @return id
     */
    int add(String name,Object obj);
}
