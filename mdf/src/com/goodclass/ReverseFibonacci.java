package com.goodclass;

import java.math.BigInteger;
import java.util.Arrays;

public class ReverseFibonacci {

    static void reverseFibonacci(int n) {
        BigInteger[] a = new BigInteger[n];

        // assigning first and second elements
        a[0] = new BigInteger("0");
        a[1] = new BigInteger("1");

        for (int i = 2; i < n; i++) {
            a[i] = a[i - 2].add(a[i - 1]);
        }


        for (int i = n - 1; i >= 0; i--) {
            System.out.print(a[i] +" ");
        }
    }

    public static void main(String[] args) {
        int n = 50;
        reverseFibonacci(n);

    }
}
