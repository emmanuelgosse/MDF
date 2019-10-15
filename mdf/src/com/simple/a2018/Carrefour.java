package com.simple.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Carrefour {

    public static String REP = "C:\\tfs\\code\\mdf\\carrefour\\";

    public static int id = 0;

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

    public static String function(File input) throws IOException, ParseException {

        String line;
        Scanner sc = new Scanner(input);

        // ===================================================

        int nbFeuVert = Integer.parseInt(sc.nextLine());

        boolean breakPoint = false;
        LinkedList<LocalTime> lastNS = new LinkedList<>();
        LinkedList<LocalTime> lastEO = new LinkedList<>();
        while (sc.hasNextLine()) {
            if (breakPoint) {
                break;
            }

            line = sc.nextLine();

            StringTokenizer st = new StringTokenizer(line, " ");
            LocalTime lt = LocalTime.parse(st.nextToken());
            String sens = st.nextToken();



            if (sens.equals("N") || sens.equals("S")) {
                for (LocalTime current : lastEO) {
                    long diff = Math.abs(current.until(lt, ChronoUnit.MINUTES));
                    if (diff < 3) {
                        breakPoint = true;
                        break;
                    }
                }
                lastNS.add(lt);
            }

            if (sens.equals("E") || sens.equals("O")) {
                lastEO.add(lt);

                for (LocalTime current : lastNS) {
                    long diff = Math.abs(current.until(lt, ChronoUnit.MINUTES));
                    if (diff < 3) {
                        breakPoint = true;
                        break;
                    }
                }
            }

        }

        System.out.println(breakPoint? "COLLISION" : "OK");

        // ===================================================

        return breakPoint? "COLLISION" : "OK";
    }
}
