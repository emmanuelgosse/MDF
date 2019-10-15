package com.second.other;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Evaporateur {

  public static String NAME = "evaporateur";

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

      System.out.println();
    }
  }

  public static String function(File input) throws IOException {

    Scanner sc = new Scanner(input);
    // ===================================================

    int total = Integer.parseInt(sc.nextLine());

    // Load Array
    int row = 0;
    String[][] tab = new String[total][];
    while (sc.hasNextLine()) {
      String lin = sc.nextLine();
      tab[row] = lin.split("");
      row++;
    }

    List<String> c = new ArrayList<>();
    c.add("X");
    List<Position> list = Position.findValuesInTab(c, tab);

    Set<Position> set = new HashSet<>();

    for (Position p : list) {
      set.addAll(Position.allPositions(p, tab, false, true, true));
    }

    System.out.println("" + set.size());

    // ===================================================

    return "" + set.size();
  }

  public static class Position {
    public int x;
    public int y;
    public String value;

    public Position(int x, int y, String value) {
      this.x = x;
      this.y = y;
      this.value = value;
    }

    public static List<Position> findValuesInTab(List<String> values, String[][] tab) {
      List<Position> pos = new ArrayList<>();
      for (int i = 0; i < tab.length; i++) {
        for (int j = 0; j < tab[0].length; j++) {
          if (values.contains(tab[i][j].toLowerCase())
              || values.contains(tab[i][j].toUpperCase())) {
            pos.add(new Position(i, j, tab[i][j]));
          }
        }
      }
      return pos;
    }

    public static Set<Position> allPositions(
        Position p, String[][] tab, boolean include, boolean straight, boolean diag) {
      Set<Position> pos = new HashSet<>();
      if (straight) {
        pos.addAll(validStraightPositions(p, tab));
      }
      if (diag) {
        pos.addAll(validDiagPositions(p, tab));
      }

      if (include) {
        pos.add(one(p.x, p.y, tab));
      }
      return pos;
    }

      @Override
      public boolean equals(Object o) {
          if (this == o) {
              return true;
          }
          if (o == null || getClass() != o.getClass()) {
              return false;
          }

          Position position = (Position) o;

          if (x != position.x) {
              return false;
          }
          return y == position.y;
      }

      @Override
      public int hashCode() {
          int result = x;
          result = 31 * result + y;
          return result;
      }
  }

  private static Position one(int x, int y, String[][] tab) {
    return (x >= 0 && x < tab.length && y >= 0 && y < tab[0].length && tab[x][y].equals("."))
        ? new Position(x, y, tab[x][y])
        : null;
  }

  public static Set<Position> validStraightPositions(
      Position p, String[][] tab) {
    Set<Position> pos = new HashSet<>();
    pos.add(one(p.x, p.y - 1, tab));
    pos.add(one(p.x, p.y + 1, tab));
    pos.add(one(p.x - 1, p.y, tab));
    pos.add(one(p.x + 1, p.y, tab));
    pos.remove(null);
    return pos;
  }

  public static Set<Position> validDiagPositions(
      Position p, String[][] tab) {
    Set<Position> pos = new HashSet<>();
    pos.add(one(p.x - 1, p.y - 1, tab));
    pos.add(one(p.x + 1, p.y - 1, tab));
    pos.add(one(p.x - 1, p.y + 1, tab));
    pos.add(one(p.x + 1, p.y + 1, tab));
    pos.remove(null);
    return pos;
  }
}
