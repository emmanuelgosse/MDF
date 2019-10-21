package com.mdf19;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class FirstExercice {

  public static String NAME = "first";

  public static String REP = "C:\\tfs\\code\\mdf\\live\\";

  public static int id = 5;

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

    int favori = Integer.parseInt(sc.nextLine());
    System.err.println(favori);
    int nb_mn = Integer.parseInt(sc.nextLine());
    System.err.println(nb_mn);

    int[] pilote = new int[20];
    for (int i = 0; i < 20; i++) {
      pilote[i] = i+1;
    }

    while (sc.hasNextLine()) {
      String lin = sc.nextLine();
        System.err.println(lin);

        StringTokenizer st = new StringTokenizer(lin);

      String id = st.nextToken();
      String event = st.nextToken();

      if (event.equals("D")) {
        dep(pilote, Integer.parseInt(id));
      }
      if (event.equals("I")) {
        inc(pilote, Integer.parseInt(id));
      }
    }

    int pos = -1;
    for (int i = 0; i < pilote.length; i++) {
      if (pilote[i] == favori) {
        pos = i;
        break;
      }
    }

    String res = pos == -1 ? "KO" : "" + (pos+1);

    System.out.println(res);

    // ===================================================

    return res;
  }

  public static void dep(int[] array, int pilote) {
    int pos = 0;
    for (int i = 0; i < array.length; i++) {
      if (array[i] == pilote) {
        pos = i;
        break;
      }
    }

    int val = array[pos-1];

      array[pos-1] = array[pos];
      array[pos] = val;

  }

  public static void inc(int[] array, int pilote) {
    int pos = 0;
    for (int i = 0; i < array.length; i++) {
      if (array[i] == pilote) {
        pos = i;
        break;
      }
    }

    int[] cop = Arrays.copyOfRange(array, pos+1, array.length);

    int row = 0;
    for (int i = pos; i < array.length; i++) {
      if (row < cop.length) {
        array[i] = cop[row++];
      } else {
        array[i] = 21;
      }
    }
  }
}
