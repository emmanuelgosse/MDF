package com.third.other;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EcoleBuissonniere {

  public static String NAME = "ecole_buissoniere";

  public static String REP = "C:\\tfs\\code\\mdf\\resources\\";

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

      long start = System.currentTimeMillis();
      String computed = function(in);
      long end = System.currentTimeMillis();
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
      System.out.println("time " + (end - start) / 1000 + " s.");
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

    int total = Integer.parseInt(sc.nextLine());
    System.err.println(total);
    int limit = Integer.parseInt(sc.nextLine());

    // Load Integer list
    int[] array = new int[total];
    int row = 0;
    while (sc.hasNextLine()) {
      array[row++] = Integer.parseInt(sc.nextLine());
    }

    int good = total / limit - 1;
    List<int[]> list = new ArrayList<>();
    ids(new int[good], total, limit + 1, 0, good, 0, list);
    good++;
    ids(new int[good], total, limit + 1, 0, good, 0, list);
    good++;
    ids(new int[good], total, limit + 1, 0, good, 0, list);

    int best = 0;
    for (int[] cur : list) {
      best = Math.max(best, score(Arrays.copyOf(array, array.length), cur));
    }

    System.out.println("" + best);

    // ===================================================

    return "" + best;
  }

  static int score(int[] result, int[] pos) {
    for (int i = 0; i < pos.length; i++) {
      result[pos[i]] = 0;
    }

    return Arrays.stream(result).sum();
  }

  static void ids(
      int[] array, int total, int limit, int row, int count, int start, List<int[]> list) {
    if (row == count) {
      if (check(array, total, limit)) {
        list.add(array);
      }
      return;
    }

    for (int i = start; i <total; i++) {
      int[] cop = Arrays.copyOf(array, array.length);
      cop[row] = i;
      ids(cop, total, limit, row + 1, count, i + 1, list);
    }
  }

  static boolean check(int[] array, int max, int limit) {
    int first = 0;
    boolean ok = true;
    for (int i : array) {
      if (limit < (i - first)) {
        ok = false;
        break;
      }
      first = i;
    }

    if (ok) {
      ok = limit > max - first;
    }

    if (!ok) {
      return ok;
    }

    return ok;
  }
}
