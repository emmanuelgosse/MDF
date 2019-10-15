package com.second.other;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PowerPlant {

  public static String NAME = "PowerPlant";

  public static String REP = "C:\\tfs\\code\\mdf\\";

  public static int id = 0;

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

    String size = sc.nextLine();
    String[] ss = size.split(" ");

    // Load Array
    int row = 0;
    int x = Integer.parseInt(ss[0]);
    int y = Integer.parseInt(ss[1]);

    String[][] tab = new String[x][y];

    while (sc.hasNextLine()) {
      tab[row++] = sc.nextLine().split("");
    }

    int[][] values = new int[x][y];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        values[i][j] = Integer.MAX_VALUE;
      }
    }

    for (int i = 0; i < x; i++) {
      work(tab, values, i);
    }

    int result = Arrays.stream(values[x - 1]).min().getAsInt() - 1;
    if (result == Integer.MAX_VALUE - 1) {
      result = -1;
    }

    System.out.println("" + result);

    // ===================================================

    return "" + result;
  }

  static void work(String[][] tab, int[][] values, int row) {
    if (row == 0) {
      String[] first = tab[0];
      int t = 0;
      for (String s : first) {
        values[row][t++] = s.equals(".") ? 1 : Integer.MAX_VALUE;
      }
    } else {
      String[] previous = tab[row - 1];
      String[] current = tab[row];
      int[] prev_values = values[row - 1];
      int[] cur_values = values[row];

      for (int i = 0; i < current.length; i++) {
        // good
        if (current[i].equals(".")) {

          if (previous[i].equals(".") && prev_values[i] != Integer.MAX_VALUE) {
            if (cur_values[i] > prev_values[i] + 1) {
              cur_values[i] = prev_values[i] + 1;
            }
          } else {
            if (i > 0 && current[i - 1].equals(".") && cur_values[i - 1] < Integer.MAX_VALUE) {
              cur_values[i] = cur_values[i - 1] + 1;
            }
          }

        } else {
          cur_values[i] = Integer.MAX_VALUE;
        }
      }
    }
  }
}
