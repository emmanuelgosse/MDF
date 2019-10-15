package com.goodclass;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Genere toutes les sequences d'Id pour etre  utilisées sur le tableau de données
 *
 */
public class Sequence {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(args[0]));

        // Load Array
        int row = 0;
        int[][] array = new int[row][row];

        while (sc.hasNextLine()) {
            String[] x = sc.nextLine().split(" ");
            array[row] = Stream.of(x).mapToInt(Integer::parseInt).toArray();
            row++;
        }

        sequence(array);
    }

    private static int sequence(int[][] data) {
        List<Integer> list = new ArrayList<>();
        int[] array = new int[data.length];
        Arrays.fill(array, -1);
        int deep = 0;
        for (int i = 0; i < data.length; i++) {
            int[] copy = Arrays.copyOf(array, array.length);
            copy[deep] = i;
            sub(copy, deep + 1, data, list);
        }
        // max
        list = list.stream().sorted((a, b) -> Integer.compare(b, a)).collect(Collectors.toList());

        return list.get(0);
    }

    private static void sub(int[] array, int deep, int[][] data, List<Integer> list) {
        if (array.length == deep) {
            function(data, array, list);
            return;
        }
        for (int i = 0; i < array.length; i++) {
            int finalI = i;
            if (Arrays.stream(array).noneMatch(x -> x == finalI)) {
                int[] copy = Arrays.copyOf(array, array.length);
                copy[deep] = i;
                sub(copy, deep + 1, data, list);
            }
        }
    }

    // Get min de chaque liste
    private static void function(int[][] data, int[] array, List<Integer> list) {
        int start = array[0];
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < array.length; i++) {
            int second = array[i];
            min = Math.min(min, data[start][second]);
            start = second;
        }
        list.add(min);

        printValue(data, array, min);
    }

    private static void printId(int[] positions) {
        System.out.println(Arrays.stream(positions).mapToObj(String::valueOf).collect(Collectors.joining("-")));
    }

    private static void printValue(int[][] data, int[] positions, int min) {
        int start = positions[0];
        String val = "";
        for (int i = 1; i < positions.length; i++) {
            int second = positions[i];
            val += data[start][second] + " ";
            start = second;
        }
        val += "-> " + min;
        System.out.println(val);
    }


}
