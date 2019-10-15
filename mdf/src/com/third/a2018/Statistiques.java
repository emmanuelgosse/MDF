package com.third.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Statistiques {

    public static String REP = "C:\\tfs\\code\\mdf\\statistiques\\";

    public static int id = 1;

    public static List<String> getFiles(String key) {
        String[] names = new File(REP).list((dir, name) -> name.toLowerCase().startsWith(key));
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

            File in = new File(REP + inputs.get(i));
            File out = new File(REP + outputs.get(i));
            Scanner sc = new Scanner(out);
            String anwser = sc.nextLine();

            String computed = function(in);
            System.out.println(in.getName() + " " + out.getName() + " ::: objectif: " + anwser + "  -->  result: " + computed);
            if (anwser.equals(computed)) {
                System.out.println("========== OK");
            } else {
                System.out.println("KO");
            }

            System.out.println();
        }
    }

    /**
     *  val lis = Array.fill(n)(1)
     *     val lds = Array.fill(n)(1)
     *     for (i <- 1 until n) {
     *       for (j <- 0 until i) {
     *         if (t(j) < t(i)) {
     *           lis(i) = math.max(lis(i), 1 + lis(j))
     *         }
     *         if (t(n-1-j) < t(n-1-i)) {
     *           lds(n-1-i) = math.max(lds(n-1-i), 1 + lds(n-1-j))
     *         }
     *       }
     *     }
     *     val res = (1 until (n-1)).filter(i => lis(i) >= 2 && lds(i) >= 2)
     *       .map(i => lis(i) + lds(i) - 1).max
     *     println(s"$res")
     */
    public static String function(File input) throws IOException, ParseException {

        String line;
        Scanner sc = new Scanner(input);

        // ===================================================

        int total = Integer.parseInt(sc.nextLine());
        System.err.println(total);

        LinkedList<Integer> list = new LinkedList<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            int value = Integer.parseInt(line);
            list.add(value);
        }

        List<Integer> posSommet = new ArrayList<>();
        for (int i = 1; i < list.size() - 1; i++) {
            int prec = list.get(i - 1);
            int cur = list.get(i);
            int suc = list.get(i + 1);
            if (cur > prec && cur > suc) {
                posSommet.add(i);
            }
        }

        int max = 0;
        for (Integer sommet : posSommet) {
            int curPos = sommet;
            int countLeft = 0;
            int leftCurPos = curPos;
            int left = list.get(leftCurPos);
            while (true) {
                if (leftCurPos >= 0 && left >= list.get(leftCurPos - 1)) {
                    left = list.get(leftCurPos - 1);
                    countLeft++;
                    leftCurPos--;
                } else {
                    break;
                }
            }
            int countRight = 0;
            int right = list.get(curPos);
            int rightCurPos = curPos;
            while (true) {
                if (rightCurPos <= list.size() && right > list.get(rightCurPos + 1)) {
                    right = list.get(rightCurPos + 1);
                    countRight++;
                    rightCurPos++;
                } else {
                    break;
                }
            }

            max = Math.max(max, countLeft + countRight +1);
        }

        System.out.println("" + max);


        // ===================================================


        return "" + max;

    }
}
