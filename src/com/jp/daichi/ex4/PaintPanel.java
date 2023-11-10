package com.jp.daichi.ex4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 描画用のパネル
 */
public class PaintPanel extends JPanel {

    //描画される図形のリスト
    private final List<AObject> objects = new ArrayList<>();

    public PaintPanel() {
        setDoubleBuffered(true);//ダブルバッファリング有効化
    }

    /**
     * 描画される図形を追加する
     * @param o　追加する図形
     */
    public void addObject(AObject o) {
        objects.add(o);
    }

    /**
     * 描画される図形を削除する
     * @param o 削除する図形
     * @return 削除できたか
     */
    public boolean removeObject(AObject o) {
        return objects.remove(o);
    }

    /**
     * 描画されるオブジェクトのリストのコピーを返す
     * @return 描画されるオブジェクトのリストのコピー
     */
    public List<AObject> getObjects() {
        return new ArrayList<>(objects);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //すべてのオブジェクトについて
        for (AObject o1:getObjects()) {
            g.setColor(Color.BLACK);
            o1.draw(g);//描画処理実行
        }
    }



}
