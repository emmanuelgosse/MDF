package com.second.other;

import java.util.Scanner;

public class Timothé {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    int nombre_personne = sc.nextInt();

    String plat = "pizza";

    if (nombre_personne == 1) {
      plat = plat + " tout seul";

    } else if (nombre_personne == 2) {
      plat = plat + " en amoureux";

    } else if (nombre_personne == 3) {
      plat = plat + " avec son chien";

    } else {
      plat = plat + " en famille";
    }

    System.out.println(plat);
  }
}
