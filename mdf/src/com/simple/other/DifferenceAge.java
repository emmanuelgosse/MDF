package com.simple.other;

public class DifferenceAge {

    public static void main(String[] args) {
        int m = 3;
        int y = 18;
        int n = 14;

        age(m,y,n);
    }

    static void age(int m, int y, int n) {
        int b = (y - y * m - n) / (1 - m);
        int a = b + n;
//        (a-y) = (b-y)*m;
//        a = n + b;
//
//        n+b-y = bm-ym;
//        b-bm = -ym +y -n;
//        b(1-m) = y-ym-n;
//        b = (y - y * m - n) / (1 - m);
//        a = n + b;
        System.out.println("Alice : " + a);
        System.out.println("Bob : " + b);
    }


}
