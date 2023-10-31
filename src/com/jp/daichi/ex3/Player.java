package com.jp.daichi.ex3;

/**
 * サイコロ転がしゲームのプレイヤークラス
 */
public class Player{
    /*■■■ メンバー変数部 ■■■*/
    private String name_;      // プレイヤーの名前
    private int winCount_ = 0; // プレイヤーの勝った回数
    private int diceSpot_;     // プレイヤーの出した目
    private int max;//サイコロの最大値

    /*■■■ コンストラクタ部 ■■■*/
    public Player(String name) {
        this(name,12);
    }

    public Player(String name,int max) {
        this.name_ = name;
        this.max = max;
    }

    /*■■■ メソッド部 ■■■*/
    /**
     * サイコロを振るメソッド
     * メソッドが呼び出される度に乱数でサイコロを振る
     *
     * @return サイコロの目（1から6のint型）
     */
    public int rollTheDice() {
        diceSpot_ = (int)(Math.random()*max+1);//randomが 0 <= x < 1の範囲で乱数を生成するので、*max+1することで、最小1,最大maxの乱数を生成
        return diceSpot_;
    }

    /**
     * 審判から勝敗を聞くメソッド
     *
     * @param result true:勝ち,false:負け
     */
    public void notifyResult(boolean result) {
        if (result){
            winCount_ += 1; // 勝った場合は勝ち数を加算する
        }
    }

    /**
     * 自分の勝った回数を答えるメソッド
     *
     * @return 勝った回数
     */
    public int getWinCount() {
        return winCount_;
    }

    /**
     * 自分の名前を答えるメソッド
     *
     * @return 名前
     */
    public String getName() {
        return name_;
    }
}