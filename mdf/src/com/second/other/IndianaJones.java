package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/** https://fr.wikipedia.org/wiki/Tri_de_cr%C3%AApes */
public class IndianaJones {

  public static String NAME = "indiana";

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

  public static void main(String[] args) throws IOException, ParseException {
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

  public static String function(File input) throws IOException, ParseException {

    Scanner sc = new Scanner(input);
    // ===================================================
    int total = Integer.parseInt(sc.nextLine());

    // Load Integer list
    int[] arr = new int[total];

    for (int i = 0; i < total; i++) {
      arr[i] = sc.nextInt();
    }

    String seq = "";
    for (int i = 0; i < total; i++) {
      seq += pancakeSort(arr, total - i - 1) + " ";
    }

    System.out.println(seq);

    // ===================================================

    return "" + seq;
  }

  static int posMax(int[] arr, int n) {
    int m = 0;
    int pos = 0;
    for (int i = 0; i < n; i++) {
      if (arr[i] > m) {
        pos = i + 1;
        m = arr[i];
      }
    }
    return pos;
  }

  static void swap(int[] arr, int pos) {
    int[] sub = Arrays.copyOf(arr, pos);
    for (int i = 0; i < sub.length; i++) {
      arr[sub.length -1 - i] = sub[i];
    }
  }

  static String pancakeSort(int[] arr, int n) {
    String seq = "";

    int posMax = posMax(arr, n);
    if (posMax != -1) {
      swap(arr, posMax);
    }

    swap(arr, n+1);

    seq += posMax + " " + (n+1) ;
    return seq;
  }
}
