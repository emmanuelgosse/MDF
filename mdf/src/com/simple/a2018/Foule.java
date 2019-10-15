package com.simple.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 *
 * MDF 2018
 * session 18h00
 * Q1
 *
 */
public class Foule {

    public static String REP = "C:\\tfs\\code\\mdf\\foule\\";

    public static int id = 0;

    public static List<String> getInputs() {
        String[] names = new File(REP).list((dir, name) -> name.toLowerCase().startsWith("input"));
        return Arrays.stream(names).sorted().collect(Collectors.toList());
    }

    public static List<String> getOutputs() {
        String[] names = new File(REP).list((dir, name) -> name.toLowerCase().startsWith("output"));
        return Arrays.stream(names).sorted().collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException, ParseException {
        List<String> inputs = getInputs();
        List<String> outputs = getOutputs();

        for (int i = 0; i < inputs.size(); i++) {
            if (id > 0 && ! inputs.get(i).contains("" + id)) {
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

    public static String function(File input) throws IOException, ParseException {

        String line;
        Scanner sc = new Scanner(input);

        // ===================================================

        int total = Integer.parseInt(sc.nextLine());
        int nb_mn = Integer.parseInt(sc.nextLine());

        int count = 0;
        int nbpers = 0;
        int minutes = 0;
        LocalTime lastTime = null;

        while (sc.hasNextLine()) {
            count++;

            line = sc.nextLine();
            StringTokenizer st = new StringTokenizer(line, " ");
            LocalTime lt = LocalTime.parse(st.nextToken());
            String sens = st.nextToken();

            if (sens.equals("E")) {
                nbpers++;
            } else {
                nbpers--;
            }

            if (lastTime != null && count <= total)
                minutes += lastTime.until(lt, ChronoUnit.MINUTES);

            if (nbpers > (10 * nb_mn)) {
                lastTime = lt;
            } else {
                lastTime = null;
            }
        }

        if (count <= total) {
            if (nbpers > (10 * nb_mn)) {
                minutes += lastTime.until(LocalTime.parse("23:00"), ChronoUnit.MINUTES);
            }
        }

        System.out.println(minutes);


        // ===================================================


        return "" + minutes;

    }


}

