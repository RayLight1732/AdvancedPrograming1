package com.jp.daichi;

public class PrintUnicode {
    public static void main(String[] args) {
        String name = "齋藤大智";
        //名前の長さ文ループ
        for (int i = 0;i < name.length();i++) {
            //unicode出力
            System.out.println(name.codePointAt(i));
        }
    }
}
