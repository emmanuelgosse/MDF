package com.goodclass;

import java.util.*;

public class SubString {

    /**
     * test -> [tt, st, tet, tes, test, e, est, es, et, te, s, tst, t, ts]
     */
    private static Set<String> generateAllSubsequence(String word) {
        Set<String> set = new HashSet<>();
        generateAllSubsequence(set, word, "", 0);
        set.remove("");
        return set;
    }

    private static void generateAllSubsequence(Set<String> set, String init, String current, int pos) {
        if (pos == init.length()) {
            set.add(current);
            return;
        }
        generateAllSubsequence(set, init, current + init.charAt(pos), pos + 1);
        generateAllSubsequence(set, init, current, pos + 1);
    }

    /**
     * abcdef -> [a, ab, abc, abcd, abcde, abcdef, b, bc, bcd, bcde, bcdef, c, cd, cde, cdef, d, de, def, e, ef, f]
     */
    private static Set<String> generateAllSubOrdered(String text) {
        Set<String> all = new HashSet<>();
        for (int i = 0; i < text.length(); i++) {
            for (int j = i + 1; j <= text.length(); j++) {
                all.add(text.substring(i, j));
            }
        }
        return all;
    }

    /**
     * with size 3
     * abcdef -> [abc, bcd, cde, def]
     */
    private static Set<String> generateAllSubsequencesWithSpecificSize(String text, int size) {
        int text_length = text.length();
        Set<String> all = new HashSet<>();
        for (int i = 0; i < text_length; i++) {
            if (i + size > text_length) {
                break;
            }
            all.add(text.substring(i, i + size));
        }
        return all;
    }

    public static void main(String[] args) {
        Set<String> all = generateAllSubsequence("test");
        System.out.println(all);

        Set<String> a = generateAllSubOrdered("abcdef");
        List<String> a_bis = new ArrayList<>(a);
        Collections.sort(a_bis);
        System.out.println(a_bis);

        Set<String> b = generateAllSubOrdered("cdef");
        a.retainAll(b);

        String best = a.stream().sorted((x, y) -> Integer.compare(y.length(), x.length())).findFirst().get();

        System.out.println(best);


        System.out.println(generateAllSubsequencesWithSpecificSize("example", 3));
    }
}
