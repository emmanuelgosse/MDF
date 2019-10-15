package com.second.other;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class RectangleMinimal {

    public static String NAME = "grandrectangle";

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
            StringBuilder anwser = new StringBuilder();
            while (sc.hasNextLine()) {
                anwser.append(sc.nextLine());
                //.append(" / ");
            }

            String computed = function(in);
            System.out.println(NAME + " -> " + in.getName() + " " + out.getName() + " ::: objectif: " + anwser + "  -->  result: " + computed);
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

        int total = Integer.parseInt(sc.nextLine());

        // Load StringTokenizer list
        List<Rectangle> listObject = new ArrayList<>();
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            Rectangle r = new Rectangle();
            r.ax = Integer.parseInt(st.nextToken());
            r.ay = Integer.parseInt(st.nextToken());
            r.bx = Integer.parseInt(st.nextToken());
            r.by = Integer.parseInt(st.nextToken());
            listObject.add(r);
        }

        Rectangle best = new Rectangle();
        best.ax = Integer.MAX_VALUE;
        best.ay = Integer.MAX_VALUE;
        best.bx = Integer.MIN_VALUE;
        best.by = Integer.MIN_VALUE;

        for (Rectangle each : listObject) {
            int minx = Math.min(each.ax, each.bx);
            int maxx = Math.max(each.ax, each.bx);
            int miny = Math.min(each.ay, each.by);
            int maxy = Math.max(each.ay, each.by);

            if (minx < best.ax) {
                best.ax = minx;
            }
            if (miny < best.ay) {
                best.ay = miny;
            }
            if (maxx > best.bx) {
                best.bx = maxx;
            }
            if (maxy > best.by) {
                best.by = maxy;
            }
        }

        String result = best.ax + " " + best.ay + " " + best.ax + " " + best.by + " " + best.bx + " " + best.ay + " " + best.bx + " " + best.by;

        System.out.println(result);
        // ===================================================
        return result;
    }

    static class Rectangle {
        int ax, ay, bx, by;
    }
}