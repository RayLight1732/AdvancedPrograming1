package com.jp.daichi.ex2;

// 敵に関するクラス（設計図）
class Teki {
    // *** フィールド変数部 ***
    // クラスに所属する変数
    int x;    // 敵の x 座標
    int y;    // 敵の y 座標
    int vx;   // 敵の x 方向速度
    int vy;   // 敵の y 方向速度

    // *** コンストラクタ部 ***
    // インスタンス（オブジェクト）が作成されたときに
    // 一度だけ呼び出される関数　クラス名と同じ名前で型名なし
    // Math という既存クラスの round メソッドと random メソッドを利用
    // round は引数を四捨五入して返すメソッド
    // random は 0 と 1 の間の乱数を返すメソッド（引数無し）
    Teki() {
        // x 座標（初期値を 5〜95 内でランダムに設定）
        x  = (int) Math.round(Math.random()*90+5);
        // y 座標（初期値を 5〜95 内でランダムに設定）
        y  = (int) Math.round(Math.random()*90+5);
        // x 速度（初期値を-2〜2 内でランダムに設定）
        vx = (int) Math.round(Math.random()*4-2);
        // y 速度（初期値を-2〜2 内でランダムに設定）
        vy = (int) Math.round(Math.random()*4-2);
    }

    // *** メソッド部 ***
    // クラスに所属する様々な関数
    /* 単位時間毎に位置や形のアップデートするメソッド */
    void update() {
        x=x+vx;        // x 座標を x 方向の速度分加算する
        y=y+vy;        // y 座標を y 方向の速度分加算する
    }
    /* オブジェクトを描画するメソッド */
    void draw() {
        // 後で実装
    }
    /* 座標値を標準出力に表示するメソッド */
    void dispXY() {
        System.out.print("(x,y)=(" + x + ',' + y + ')');
    }
    /* 衝突判定を行うメソッド */
    void collesionChk() {
        // 後で実装
    }
}
