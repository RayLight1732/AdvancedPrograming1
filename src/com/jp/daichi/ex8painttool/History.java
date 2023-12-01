package com.jp.daichi.ex8painttool;

import com.jp.daichi.ex8painttool.canvasobject.CanvasObject;

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
     * @param id 履歴のid
     * @return 移動に成功したならtrue
     */
    boolean to(int id);

    /**
     * 現在位置のidを取得
     * @return 現在位置のid 指定されていないときは-1
     */
    int geCurrentHistoryID();

    /**
     * 現在位置までのすべてのキャンバスオブジェクトを取得する
     * @return 現在位置までのすべてのキャンバスオブジェクト
     */
    List<CanvasObject> getObjects();

    /**
     * すべての履歴を取得
     * @return すべての履歴
     */
    List<HistoryStaff> getAll();

}
