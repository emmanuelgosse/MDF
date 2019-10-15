package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class CouloirTresor {
    public static String NAME = "couloirtresor";

    public static String REP = "C:\\tfs\\code\\mdf\\resources\\";

    public static int id = 4;

    public static List<String> getFiles(String key) {
        String[] names = new File(REP + NAME + "\\").list((dir, name) -> name.toLowerCase().startsWith(key));
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
            String anwser = sc.nextLine();

            String computed = function(in);
            System.out.println(NAME + " -> " + in.getName() + " " + out.getName() + " ::: objectif: " + anwser + "  -->  result: " + computed);
            if (anwser.equals(computed)) {
                System.out.println("========== OK");
            } else {
                System.out.println("KO");
            }

            System.out.println();
        }
    }

    static int total;


    public static String function(File input) throws IOException, ParseException {

        Scanner sc = new Scanner(input);
        // ===================================================

        total = Integer.parseInt(sc.nextLine());

        String lineText = sc.nextLine();
        int X = lineText.indexOf("X");

        String left = lineText.substring(0, X);
        List<String> leftList = Arrays.asList(left.split(""));
        Collections.reverse(leftList);

        String right = lineText.substring(X + 1);
        List<String> rightList = Arrays.asList(right.split(""));

        List<String> all = new ArrayList<>();
        buildAllSubStrings(leftList, rightList, "", all);

        all.sort(Collections.reverseOrder());
        String maxScore = all.get(0);

        //  all.forEach(x -> System.out.println(x + " " + value(x)));
        // String maxScore = all.stream().sorted((a, b) -> Integer.compare(value(b), value(a))).findFirst().get();

        System.out.println(maxScore);

        // ===================================================

        return maxScore;

    }

    public static void buildAllSubStrings(List<String> left, List<String> right, String text, List<String> all) {

        // Maybe I have finished on the previous recursive round
        if (text.length() == total - 1) {
            all.add(text);
        }
        if (left.isEmpty() && right.isEmpty()) {
            return;
        }

        // Optim : finish adding right if left is over
        if (left.isEmpty()) {
            all.add(text + String.join("", right));
        }

        // Optim : finish adding left if right is over
        if (right.isEmpty()) {
            all.add(text + String.join("", left));
        }

        // Where are the next o's
        int indexLeft = left.indexOf("o");
        int indexRight = right.indexOf("o");

        // Case : zero's
        while (true) {
            if (indexLeft == 0 || indexRight == 0) {
                // Taking all left's
                if (indexLeft == 0) {
                    text += "o";
                    left = left.subList(1, left.size());
                    indexLeft = left.indexOf("o");
                }
                // Taking all right's
                if (indexRight == 0) {
                    text += "o";
                    right = right.subList(1, right.size());
                    indexRight = right.indexOf("o");
                }
            } else {
                break;
            }
        }

        // Maybe we have all
        if (text.length() == total - 1) {
            all.add(text);
            return;
        }

        // Left o is nearer than right o
        if (indexLeft < indexRight && indexLeft > 0) {
            int i;
            for (i = 0; i < indexLeft; i++) {
                text += left.get(i);
            }
            buildAllSubStrings(left.subList(i, left.size()), right, text, all);

            // Right o is nearer than left o
        } else if (indexRight < indexLeft && indexRight > 0) {
            int i;
            for (i = 0; i < indexRight; i++) {
                text += right.get(i);
            }
            buildAllSubStrings(left, right.subList(i, right.size()), text, all);

            // Right and left o are as far as each other
        } else {

            // Try left with a sub branch case
            if (indexLeft >= 0) {
                String local = text + String.join("", left.subList(0, indexLeft));
                buildAllSubStrings(left.subList(indexLeft, left.size()), right, local, all);
            }

            // Try right with a sub branch case
            if (indexRight >= 0) {
                String local = text + String.join("", right.subList(0, indexRight));
                buildAllSubStrings(left, right.subList(indexRight, right.size()), local, all);
            }

            // only * stay in left list, add them
            if (indexLeft < 0 && !left.isEmpty()) {
                text += String.join("", left);
            }

            // only * stay in right list, add them
            if (indexRight < 0 && !right.isEmpty()) {
                text += String.join("", right);
            }

            // on a mistake, I can conclude ...
            if (text.length() == total - 1) {
                all.add(text);
            }
        }
    }

    public static int value(String s) {
        int value = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'o')
                value += 1;
            else
                value *= 2;
        }
        return value;
    }
}