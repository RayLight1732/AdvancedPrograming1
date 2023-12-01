package com.jp.daichi.ex8;

import com.jp.daichi.ex8.canvasobject.CanvasObject;

import java.util.List;

public interface History {
    /**
     * 現在の履歴の状態に新しい物を追加する
     * @param name 履歴の名前(重複可能)
     * @param obj 追加するオブジェクト
     * @return id(0以上の値)
     */
    int add(String name, CanvasObject obj);

    /**
     * idの場所までundo,redoする
     * @param id 履歴のid(-1なら履歴なしの状態まで戻る)
     * @return 移動に成功したならtrue
     */
    boolean to(int id);

    /**
     * 現在位置のidを取得
     * @return 現在位置のid 指定されていないときは-1
     */
    int getCurrentHistoryID();

    /**
     * 現在位置までのすべての履歴を取得する
     * @return 現在位置までのすべての履歴
     */
    List<HistoryStaff> painted();

    /**
     * すべての履歴を取得
     * @return すべての履歴
     */
    List<HistoryStaff> getAll();

    /**
     * 履歴の全体の中のインデックスを取得
     * @param id 対象のid
     * @return インデックス(見つからなかった場合は-1)
     */
    int getIndex(int id);

}
