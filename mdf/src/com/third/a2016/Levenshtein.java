package com.third.a2016;

import com.goodclass.DamerauLevenshteinAlgorithm;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Levenshtein {

    public static String NAME = "levenshtein";

    public static String REP = "C:\\tfs\\code\\mdf\\resources\\";

    public static int id = 0;

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
            String allanswer = "";
            while (sc.hasNextLine()) {
                allanswer += sc.nextLine() + "|";
            }

            String computed = function(in);
            System.out.println(NAME + " -> " + in.getName() + " " + out.getName() + " ::: objectif: " + allanswer + "  -->  result: " + computed);
            if (allanswer.equals(computed)) {
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

        int nbDict = Integer.parseInt(sc.nextLine());

        List<String> words = new ArrayList<>();
        for (int i = 0; i < nbDict; i++) {
            words.add(sc.nextLine());
        }

        int nbSearchedWords = Integer.parseInt(sc.nextLine());
        List<String> searchedWords = new ArrayList<>();
        for (int i = 0; i < nbSearchedWords; i++) {
            searchedWords.add(sc.nextLine());
        }

        DamerauLevenshteinAlgorithm dam = new DamerauLevenshteinAlgorithm(2, 2, 3, 3);

        String answser = "";


        List<String> nearestWord = new ArrayList<>();
        for (String searched : searchedWords) {
            int min = Integer.MAX_VALUE;
            for (String dictWord : words) {
                int curMin = dam.execute(searched, dictWord);
                if (curMin<min) {
                    min = curMin;
                    nearestWord.clear();
                    nearestWord.add(dictWord);
                } else if (curMin == min) {
                    nearestWord.add(dictWord);
                }
            }

            answser += nearestWord.stream().sorted().findFirst().get()+"|";
        }


        System.out.println(answser);


        // ===================================================


        return answser;

    }
}
