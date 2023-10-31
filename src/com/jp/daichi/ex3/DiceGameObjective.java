package com.jp.daichi.ex3;

/**
 * オブジェクト指向によるサイコロ転がしゲーム
 * JankenObjective は main メソッドのみを持ち，
 * プログラムのスタートと各クラスのインスタンスの作成を担う
 */
public class DiceGameObjective {
    public static void main(String[] args) {
        // 審判（佐藤さん）のインスタンス生成
        // 設計図（クラス）は Judge.class
        Judge sato = new Judge();

        Player[] players = new Player[5];//Plyer配列を初期化
        for (int i = 0;i < players.length;i++) {
            //Playerを初期化して代入
            players[i] = new Player("プレイヤー"+(i+1));
        }
        sato.startDiceGame(players);
    }
}
