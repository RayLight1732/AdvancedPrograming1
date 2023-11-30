package com.jp.daichi.ex8painttool.tools;

import com.jp.daichi.ex8painttool.Canvas;
import com.jp.daichi.ex8painttool.FinishHandler;
import com.jp.daichi.ex8painttool.UpDateHandler;

import java.awt.*;

/**
 * 描画用ツール
 */
public interface Tool {

    /**
     * Pointを始点としてツールの実行処理を担当するオブジェクトを新しく作成
     * @param canvas 対象となるキャンバス
     * @param point 始点
     * @param callback 終了した時に呼び出されるコールバック
     * @return ツールの実行処理を担当するオブジェクト
     */
    ToolExecutor createExecutor(Canvas canvas, Point point, UpDateHandler upDateHandler, FinishHandler finishHandler);
}
