package com.jp.daichi;

public class HelloWorld {
    //メイン関数
    public static void main(String[] args) {
        for(int n = 1; n <= 10;n++) {
            for(int k = 1;k <= n;k++) {
                System.out.println("C("+n+","+k+") = "+C(n,k));
            }
        }

    }

    public static long C(int n,int k) {
        //kがnより大きかったらエラー
        if(k > n) {
            throw new IllegalArgumentException("k must be less than or equal to n");
        }
        //結果 (intだと小さすぎる可能性)
        long result = 1;
        //for文内でnPkする
        for (int i = 0;i < k;i++) {
            result *= n;
            n--;
        }
        //while文内でnCkの分母する
        while (k > 0) {
            result /= k;
            k--;
        }
        return result;
    }

}
