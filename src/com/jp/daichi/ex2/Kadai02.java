package com.jp.daichi.ex2;

class Kadai02 {
    public static void main(String[] args) {
        Teki[] tekis = new Teki[5];  // Teki配列を宣言
        //配列にTekiインスタンスを生成して代入
        for (int i =0; i< tekis.length;i++) {
            tekis[i] = new Teki();
        }

        for (int i=1; i<100; i++) {
            //敵の位置をすべて表示
            for (int i2 = 0;i2 < tekis.length;i2++) {
                System.out.print("敵"+(i2+1)+"の位置:");
                tekis[i2].dispXY();
            }
            System.out.println("");//改行
            //すべての敵の位置を更新
            for (Teki teki : tekis) {
                teki.update();
            }
        }
    }
}
