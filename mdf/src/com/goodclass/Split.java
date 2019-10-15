package com.goodclass;

import java.util.Arrays;

public class Split {

    public static void main(String[] args) {
        String text = "aaabbbbccc";

        String[] tabLeft = text.split("(?<=(.))(?!\\1)");

        Arrays.stream(tabLeft).forEach(System.out::println);
    }
}
