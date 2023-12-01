package com.jp.daichi.ex8painttool;

import com.jp.daichi.ex8painttool.canvasobject.CanvasObject;

/**
 * 履歴の内容物
 * @param name 名前
 * @param canvasObject キャンバスオブジェクト
 */
public record HistoryStaff(int id,String name, CanvasObject canvasObject) {
}
