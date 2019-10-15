package com.second.other;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ADN {

  public static String NAME = "adn";

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

    }
  }



  public static String function(File input) throws IOException {

    Scanner sc = new Scanner(input);
    // ===================================================

    int total = Integer.parseInt(sc.nextLine());

    // Load Integer list
    List<String> list = new ArrayList<>();
    int sizeLetter = 0;
    while (sc.hasNextLine()) {
      String seq = sc.nextLine();
      list.add(seq);
      sizeLetter += seq.length();
    }

    List<String> all = new ArrayList<>();
    rec(list, all, "", sizeLetter);

    String good = "";
    for (String t : all) {
      if (check(t)) {
        good = t.replace(" # ", "#").trim();
      }
    }

    System.out.println(good);

    // ===================================================
    return good;
  }

  public static void rec(List<String> list, List<String> all, String text,  int sizeLetter) {
    String local = text.replace(" ", "");
    if (local.length() == sizeLetter / 2) {
      text += " #";
    }

    if (list.isEmpty()) {
      all.add(text);
    }

    for (int i = 0; i < list.size(); i++) {
      List<String> sub = new ArrayList<>(list);
      sub.remove(i);
      rec(sub, all, text + " " + list.get(i), sizeLetter);
    }
  }

  public static boolean check(String aa) {
    String[] sep = aa.replace(" ", "").split("#");
    if (sep.length != 2) {
      return false;
    }

    String[] valA = sep[0].split("");
    String[] valB = sep[1].split("");

    if (valA.length != valB.length) {
      return  false;
    }

    for (int i = 0; i < valA.length; i++) {
      String a = valA[i];
      String b = valB[i];

      if (a.equals("A") && !b.equals("T")) return false;
      if (a.equals("T") && !b.equals("A")) return false;
      if (a.equals("C") && !b.equals("G")) return false;
      if (a.equals("G") && !b.equals("C")) return false;
    }

    return true;
  }
}
