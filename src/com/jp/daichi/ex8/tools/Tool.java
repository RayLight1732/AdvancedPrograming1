package com.jp.daichi.ex8.tools;

import com.jp.daichi.ex8.Canvas;
import com.jp.daichi.ex8.FinishHandler;
import com.jp.daichi.ex8.KeyManager;
import com.jp.daichi.ex8.UpDateHandler;

import java.awt.*;

/**
 * 描画用ツール
 */
public interface Tool {

    /**
     * Pointを始点としてツールの実行処理を担当するオブジェクトを新しく作成
     * @param canvas 対象となるキャンバス
     * @param point 始点
     * @param manager キーマネージャー
     * @param upDateHandler アップデートハンドラー
     * @param finishHandler フィニッシュハンドラー
     * @return ツールの実行処理を担当するオブジェクト
     */
    ToolExecutor createExecutor(Canvas canvas, Point point, KeyManager manager, UpDateHandler upDateHandler, FinishHandler finishHandler);

    String getName();
}
