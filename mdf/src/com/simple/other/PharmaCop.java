package com.simple.other;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PharmaCop {

  public static String NAME = "pharma";

  public static String REP = "C:\\tfs\\code\\mdf\\resources\\";

  public static int id = 1;

  public static List<String> getFiles(String key) {
    String[] names =
        new File(REP + NAME + "\\").list((dir, name) -> name.toLowerCase().startsWith(key));
    if (names == null) {
      return Collections.emptyList();
    }
    return Arrays.stream(names).sorted().collect(Collectors.toList());
  }

  public static void main(String[] args) throws IOException {
    List<String> inputs = getFiles("input");
    List<String> outputs = getFiles("output");

    for (int i = 0; i < inputs.size(); i++) {
      if (id > 0 && !inputs.get(i).contains("" + id)) {
        continue;
      }

      File in = new File(REP + NAME + "\\" + inputs.get(i));
      File out = new File(REP + NAME + "\\" + outputs.get(i));
      Scanner sc = new Scanner(out);
      StringBuilder anwser = new StringBuilder();
      while (sc.hasNextLine()) {
        anwser.append(sc.nextLine());
        // .append(" / ");
      }

      String computed = function(in);
      System.out.println(
          NAME
              + " -> "
              + in.getName()
              + " "
              + out.getName()
              + " ::: objectif: "
              + anwser
              + "  -->  result: "
              + computed);
      if (anwser.toString().equals(computed)) {
        System.out.println("========== OK");
      } else {
        System.out.println("KO");
      }

      System.out.println();
    }
  }

  public static String function(File input) throws IOException {

    Scanner sc = new Scanner(input);
    // ===================================================

    System.err.println("==============================");

    int N = Integer.parseInt(sc.nextLine());
    System.err.println(N);

    int total = 3 * N - 2;
    String[][] array = new String[total][total];

    for (int i = 0; i < total; i++) {
      for (int j = 0; j < total; j++) {
        array[i][j] = ".";
      }
    }

    for (int i = 0; i < 4; i++) {
        rec(array, N);
        rotateLeft(1, array);
    }

    printTab(array);
    System.out.println("");

    // ===================================================

    return "";
  }


  static void rec(String[][] array, int N) {
    int x = 0;
    for (int i = N - 1; i < 2 * N-2; i++) {
      array[0][i] = "X";
    }

      int y= N - 1;
      for (int i = 0; i <  N; i++) {
          array[i][y] = "X";
      }
      y= 2 * N-2;
      for (int i = 0; i <  N; i++) {
          array[i][y] = "X";
      }
  }

  static void rotateLeft(int nbTurn, String[][] mat) {
    int N = mat.length;
    for (int i = 0; i < nbTurn; i++) {
      for (int x = 0; x < N / 2; x++) {
        for (int y = x; y < N - x - 1; y++) {
          String temp = mat[x][y];
          mat[x][y] = mat[y][N - 1 - x];
          mat[y][N - 1 - x] = mat[N - 1 - x][N - 1 - y];
          mat[N - 1 - x][N - 1 - y] = mat[N - 1 - y][x];
          mat[N - 1 - y][x] = temp;
        }
      }
    }
  }

  public static void printTab(String[][] tab) {
    for (String[] strings : tab) {
      for (int j = 0; j < tab[0].length; j++) {
        System.out.print(strings[j]);
      }
      System.out.println("");
    }
    System.out.println("");
  }
}
