package com.jp.daichi.ex8;

import com.jp.daichi.ex8.canvasobject.CanvasObject;

import java.io.Serial;
import java.io.Serializable;

/**
 * 履歴の内容物
 * @param name 名前
 * @param canvasObject キャンバスオブジェクト
 */
public record HistoryStaff(int id,String name, CanvasObject canvasObject) implements Serializable {
    @Serial
    private static final long serialVersionUID = 8217484315767063802L;

}
