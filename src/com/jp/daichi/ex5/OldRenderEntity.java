package com.jp.daichi.ex5;

import com.jp.daichi.ex4.RotationalObject;

/**
 * 古い(ex4)レンダラーを使用している場合のマーカー
 */
@Deprecated
public interface OldRenderEntity {
    /**
     * 内部で使用されている描画用オブジェクトを取得
     * @return 内部で使用されている描画用オブジェクト
     */
    RotationalObject getRotationalObject();
}
