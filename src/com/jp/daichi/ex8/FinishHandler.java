package com.jp.daichi.ex8;

import com.jp.daichi.ex8.tools.ToolExecutor;

/**
 * 処理が終了した時に呼び出される
 */
public interface FinishHandler {

    /**
     * 処理が終了した時に呼び出される
     * @param executor 実体
     */
    void onFinish(ToolExecutor executor);
}
