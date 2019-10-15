package com.simple.other;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Binaire {

    public static void main(String[] args) {
        int m = 56;
        String rep = Integer.toBinaryString(m);
        int val = Arrays.stream(rep.split("")).map(Integer::parseInt).reduce(Integer::sum).orElse(0);


        BigInteger x = new BigInteger("12345678901");
        rep = x.toString(16);
        rep = rep.replaceAll("\\d", "");

        String[] l = rep.split("");
        Arrays.sort(l);

        rep = Arrays.stream(l).collect(Collectors.joining());

        l = rep.split("(?<=(.))(?!\\1)");

        String v = Arrays.stream(l).map(z->z.subSequence(0,1)).collect(Collectors.joining());

        System.out.println(v);

    }


}
