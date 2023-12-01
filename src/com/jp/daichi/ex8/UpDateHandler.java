package com.jp.daichi.ex8;

import com.jp.daichi.ex8.tools.ToolExecutor;

/**
 * ToolExecutorの状態が更新されたときに呼び出される
 */
public interface UpDateHandler {
    /**
     * アップデートが起こったときに呼び出され鵜
     * @param executor 実体
     */
    void onUpdate(ToolExecutor executor);
}
