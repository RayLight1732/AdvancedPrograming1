package com.jp.daichi.ex8painttool;

import com.jp.daichi.ex8painttool.tools.ToolExecutor;

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
