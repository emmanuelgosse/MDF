package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class CasinoCrapette {

  public static String NAME = "casino_crapette";

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

  public static String function(File input) throws IOException {

    Scanner sc = new Scanner(input);
    // ===================================================

    int pilevide = Integer.parseInt(sc.nextLine());
    System.err.println(pilevide);
    int nb_carte = Integer.parseInt(sc.nextLine());
    System.err.println(nb_carte);

    // Load StringTokenizer list
    ArrayDeque<Integer> cartes = new ArrayDeque<>();
    while (sc.hasNextLine()) {
      StringTokenizer st = new StringTokenizer(sc.nextLine());
      while (st.hasMoreTokens()) {
        cartes.add(Integer.parseInt(st.nextToken()));
      }
    }

    List<ArrayDeque<Integer>> piles = new ArrayList<>(pilevide);
    piles.add(0, cartes);

    ArrayDeque<Integer> vides = new ArrayDeque<>(pilevide);

    String sol = move(nb_carte, piles, 0, 1, vides);

    System.out.println(sol);

    // ==================================================

    return sol;
  }

  public static String move( int n, List<ArrayDeque<Integer>> piles, int depart, int arrivee, ArrayDeque<Integer> vides) {
    if (n == 1) {
      return swap_head(piles, depart, arrivee);
    }

    if (vides.isEmpty()) {
      System.out.println("fail");
    }

    Integer intermediaire = vides.pop();
    ArrayDeque<Integer> copy_vides = new ArrayDeque<Integer>(vides);
    if (piles.get(arrivee) == null) {
      vides.add(arrivee);
    }

    String a = move(n - 1, piles, depart, intermediaire, vides);

    String c = swap_head(piles, depart, arrivee);

    vides = copy_vides;

    if (piles.get(depart) == null) {
      vides.add(depart);
    }

    String b = move(n - 1, piles, intermediaire, arrivee, vides);

    return String.format("%d;%d;%d", a, b, c);
  }

  public static String swap_head(List<ArrayDeque<Integer>> piles, int depart, int arrivee) {
    ArrayDeque<Integer> stask = piles.get(depart);
    Integer carte = stask.pop();

    stask = piles.get(arrivee);
    stask.add(carte);

    return String.format("%d %d", carte, arrivee);
  }
}
