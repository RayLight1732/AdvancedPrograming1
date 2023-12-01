package com.jp.daichi.ex8painttool.tools;

import com.jp.daichi.ex8painttool.canvasobject.CanvasObject;

import java.awt.event.MouseAdapter;

/**
 * 描画ツールの実際の処理を担当する
 */
public interface ToolExecutor {
    /**
     * プレビューを描画するオブジェクトを取得する
     * @return プレビュー描画用のオブジェクト
     */
    CanvasObject getPreviewCanvasObject();

    /**
     * 描画用のオブジェクトを取得する
     * @return 描画用のオブジェクト
     */
    CanvasObject getFinalCanvasObject();

    /**
     * マウスアダプターを取得する
     * 返されるマウスアダプターは常に同じインスタンスである必要がある
     * @return マウスアダプター
     */
    MouseAdapter getMouseAdapter();
}
