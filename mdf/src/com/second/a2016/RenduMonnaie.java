package com.second.a2016;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class RenduMonnaie {

    public static String NAME = "rendu_monnaie";

    public static String REP = "C:\\tfs\\code\\mdf\\";

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

    public static String function(File input) throws IOException, ParseException {

        Scanner sc = new Scanner(input);
        // ===================================================

        int rendu = Integer.parseInt(sc.nextLine());
        int type = Integer.parseInt(sc.nextLine());


        // Load StringTokenizer list
        List<TypePiece> listPieces = new ArrayList<>();
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            int nb = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());
            listPieces.add(new TypePiece(value, nb));
        }

        int count = count(rendu, 0, listPieces);

        System.out.println((count == Integer.MAX_VALUE) ? "IMPOSSIBLE" : "" + count);

        // ===================================================

        return (count == Integer.MAX_VALUE) ? "IMPOSSIBLE" : "" + count;
    }


    public static int count(int reste, int nbPieces, List<TypePiece> typePieces) {
        if (typePieces.isEmpty()) {
            if (reste == 0) {
                return nbPieces;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        TypePiece typePiece = typePieces.get(0);
        int nbPiecesEntieres = reste / typePiece.value;

        int nbPiecesdeCeType = Math.min(typePiece.nb, nbPiecesEntieres);

        List<Integer> pieces = new ArrayList<>();
        for (int i = 0; i <= nbPiecesdeCeType; ++i) {
            int newReste = reste - typePiece.value * i;
            pieces.add(count(newReste, nbPieces + i, typePieces.subList(1, typePieces.size())));
        }

        return pieces.stream().sorted().findFirst().get();
    }

    static class TypePiece {
        public int value;
        public int nb;

        public TypePiece(int value, int nb) {
            this.value = value;
            this.nb = nb;
        }
    }
}
