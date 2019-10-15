package com.simple.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EmpreinteFichier {

  public static String NAME = "empreinte_fichier";

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

    String text = sc.nextLine();
      String t = "";
    while (true) {
      t = text.replace("000", "00");
      if (t.equals(text)) {
        break;
      }
      text = t;
    }
    while (true) {
        t = text.replace("111", "1");
        if (t.equals(text)) {
            break;
        }
        text = t;
    }
    while (true) {
        t = text.replace("10", "1");
        if (t.equals(text)) {
            break;
        }
        text = t;
    }

    System.out.println(text);

    // ===================================================

    return text;
  }
}
