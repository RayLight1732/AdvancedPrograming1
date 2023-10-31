package com.jp.daichi.ex3;

public class Judge {

    /**
     * サイコロゲームを進行するメソッド
     * 誰かが3回勝利したらゲーム終了
     * 引数は Player配列
     * @param players 判定対象プレイヤー
     */
    public void startDiceGame(Player[] players) {
        startDiceGame(players,3);
    }

    /**
     * サイコロゲームを進行するメソッド
     * 引数は Player配列
     * @param players 判定対象プレイヤー
     * @param winCount 最終的な勝者を判定する閾値
     */
    public void startDiceGame(Player[] players,int winCount) {
        System.out.println("【サイコロ振りゲーム開始】");

        //最終的な勝者
        Player finalWinner;
        int cnt = 0;//何回戦か
        // 無限ループ
        while (true){
            System.out.println("【" + (cnt + 1) + "回戦目】"); // 何回戦目か表示

            // ★ プレイヤーの手を見て、どちらが勝ちかを判定する
            // Player クラスタイプで winner という変数を定義し，
            // 自分自身のクラス (this) に所属する judgeDiceRoll メソッドの戻り値を winner に入れる
            Player winner = this.judgeDiceRoll(players);

            if (winner != null) { // 誰かが勝った場合
                System.out.println("\n" + winner.getName() + "が勝ちました\n"); // 勝者を表示
                winner.notifyResult(true); // 勝ったプレイヤーへ結果を伝える
            } else {
                // 引き分けの場合
                System.out.println("\n引き分けです\n");
            }

            //最終的な勝者
            finalWinner = judgeFinalWinner(players,winCount);
            if (finalWinner != null) { //存在するなら
                break;//ループを抜ける
            }
            //対戦回数インクリメント
            cnt++;
        }

        // ★ ジャンケンの終了を宣言する
        System.out.println("【サイコロ振りゲーム終了】");
        System.out.println("勝者は"+finalWinner.getName()+"でした。");

    }

    /**
     * 各プレイヤーにサイコロを振らせ、それぞれの手を見てどちらが勝ちかを判定するメソッド
     *
     *
     * @param players 判定対象プレイヤーの配列
     * @return 勝ったプレイヤー。引き分けの場合は null を返す。
     */
    private Player judgeDiceRoll(Player[] players) {
        Player winner = null;//勝者
        int max = 0;//出た目の最大値

        for (int i = 0; i< players.length;i++) {
            //振る
            int diceSpot = players[i].rollTheDice();
            //手を表示
            System.out.print(diceSpot);
            //最後以外はvsを追加
            if(i != players.length-1) {
                System.out.print(" vs. ");
            }

            if (diceSpot > max) {
                //出た目が最大値より大きい場合は勝者としてマーク、最大値を更新
                winner = players[i];
                max = diceSpot;
            } else if(diceSpot == max) {
                //出た目が最大値と同じ場合は勝者を取り消し
                winner = null;
            }
        }
        //改行
        System.out.println("");

        return winner;
    }

    /**
     * 勝者を判定するメソッド
     *
     * @param players 判定対象プレイヤー
     * @param winCount ゲーム終了となる勝利回数
     * @return 勝ったプレイヤー。勝者がいない場合は　null を返す。
     */
    private Player judgeFinalWinner(Player[] players,int winCount) {
        for (Player player:players) {//すべてのプレイヤーについて
            if (player.getWinCount() >= winCount) {//勝利数が規程勝利数より多いとき
                return player;//リターン
            }
        }
        return null;
    }
}
