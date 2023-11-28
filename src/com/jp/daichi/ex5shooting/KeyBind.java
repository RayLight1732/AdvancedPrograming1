package com.jp.daichi.ex5shooting;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class KeyBind {

    private final List<KeyManager> managers = new ArrayList<>();

    /**
     * キーボードのボタン管理用クラス
     * @param panel 監視対象のパネル
     */
    public KeyBind(JPanel panel) {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                for (KeyManager manager:managers) {
                    if (manager.keyCode == e.getKeyCode()) {
                        manager.pressed = true;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                for (KeyManager manager:managers) {
                    if (manager.keyCode == e.getKeyCode()) {
                        manager.pressed = false;
                    }
                }
            }
        });
    }

    /**
     * 監視対象のキーを追加
     * @param keyCode キーコード
     * @return 追加できたか
     */
    public boolean addKeyBind(int keyCode) {
        if (!isRegistered(keyCode)) {
            managers.add(new KeyManager(keyCode));
            return true;
        } else {
            return false;
        }
    }

    /**
     * 監視対象のキーを削除
     * @param keyCode キーコード
     * @return 削除できたか
     */
    public boolean removeKeyBind(int keyCode) {
        for (KeyManager manager:managers) {
            if (manager.keyCode == keyCode) {
                managers.remove(manager);
                return true;
            }
        }
        return false;
    }

    /**
     * このキーが登録されているか
     * @param keyCode 検索対象のキー
     * @return 登録されているならtrue
     */
    public boolean isRegistered(int keyCode) {
        for (KeyManager manager:managers) {
            if (manager.keyCode == keyCode) {
                return true;
            }
        }
        return false;
    }

    /**
     * キーが押されているか
     * @param keyCode キーコード
     * @return 押されているならtrue
     */
    public boolean isPressed(int keyCode) {
        for (KeyManager manager:managers) {
            if (manager.keyCode == keyCode) {
                return manager.pressed;
            }
        }
        return false;
    }

    public static class KeyManager {
        private final int keyCode;
        private boolean pressed = false;
        private KeyManager(int keyCode) {
            this.keyCode = keyCode;
        }

        public int getKeyCode() {
            return keyCode;
        }

        public boolean isPressed() {
            return pressed;
        }
    }
}
