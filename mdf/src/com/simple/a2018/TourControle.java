package com.simple.a2018;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class TourControle {

    public static String REP = "C:\\tfs\\code\\mdf\\tourcontrole\\";

    public static int id = 2;

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

        int nbaterrisage = Integer.parseInt(sc.nextLine());
        int max_att_45 = Integer.parseInt(sc.nextLine());

        Map<String, LinkedList<LocalTime>> allLanding = new HashMap<>();
        Map<String,LocalTime> lastlanding = new HashMap<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();

            StringTokenizer st = new StringTokenizer(line, " ");
            LocalTime lt = LocalTime.parse(st.nextToken());
            String piste = st.nextToken();

            // 6
            LocalTime last = lastlanding.get(piste);
            if (last != null) {
                long diff = last.until(lt, ChronoUnit.MINUTES);
                if (diff <=6)  {
                    System.out.println("COLLISION");
                    return "COLLISION";
                }
            }
            lastlanding.put(piste, lt);



            // 45
            LinkedList<LocalTime> registered = allLanding.get(piste);
            if (registered == null) {
                registered = new LinkedList<LocalTime>();
                allLanding.put(piste, registered);
            }

            registered.add(lt);

            LocalTime younger = registered.getLast();
            LocalTime older = registered.getFirst();
            long diff = older.until(younger, ChronoUnit.MINUTES);

            for (int i = 0; i < registered.size(); i++) {
                if (diff > 0 && diff <= 45 && registered.size() > max_att_45) {
                    System.out.println("COLLISION");
                    return "COLLISION";
                } else if (diff > 45) {
                    registered.removeFirst();
                    younger = registered.getLast();
                    older = registered.getFirst();
                    diff = older.until(younger, ChronoUnit.MINUTES);
                }
            }
        }

        System.out.println("OK");


        // ===================================================


        return "OK";

    }
}
