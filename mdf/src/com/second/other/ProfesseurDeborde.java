package com.second.other;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ProfesseurDeborde {

    public static final String STR = "-";
    public static String NAME = "professeurdeborde";

    public static String REP = "C:\\tfs\\code\\mdf\\resources\\";

    public static int id = 0;

    public static List<String> getFiles(String key) {
        String[] names = new File(REP + NAME + "\\").list((dir, name) -> name.toLowerCase().startsWith(key));
        if (names == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(names).sorted().collect(Collectors.toList());
    }


    public static void main(String[] args) throws IOException {
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
                anwser.append(sc.nextLine()).append(STR);
            }
            anwser.append("true");

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

    /**
     * 0 : id
     * 1 : a
     * 2 : b
     * 3 : neighbours a
     * 4: neighbours b
     * 5 : chosen
     */
    public static String function(File input) throws IOException {

        Scanner sc = new Scanner(input);
        // ===================================================

        int total = Integer.parseInt(sc.nextLine());

        // Load StringTokenizer list
        int[][] arr = new int[total][6];

        int row = 0;
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");

            arr[row][0] = row;
            arr[row][1] = Integer.parseInt(st.nextToken());
            arr[row][2] = Integer.parseInt(st.nextToken());
            row++;
        }

        search(arr);
        boolean ok = checkFinal(arr);
        String result = output(arr);

        var x = result.contains("0") ? "KO" + STR : result;
        System.out.println(x);

        // ===================================================

        return x + ok;

    }

    static void search(int[][] arr) {
        int nbFound = 0;
        int localFound = -1;
        while (localFound != nbFound) {
            nbFound = localFound;

            computeNeighbours(arr);
            int minNeighbours = minNeighbours(arr);
            List<Integer> neighbours = findMinNeighbours(arr, minNeighbours);

            if (minNeighbours == 0) {
                for (Integer pos : neighbours) {
                    compute(arr, pos, minNeighbours);
                }
                if (isFinished(arr)) {
                    break;
                }
                search(arr);

            } else {
                for (Integer pos : neighbours) {
                    int[][] cop = Arrays.copyOf(arr, arr.length);
                    boolean addeed = compute(cop, pos, minNeighbours);
                    // impossible to add -> dead branch
                    if (!addeed) {
                        continue;
                    }

                    if (isFinished(cop)) {
                        arr = cop;
                        break;
                    }

                    search(cop);
                }
            }
            localFound = nbFound(arr);
        }
    }

    static boolean checkInsert(int[][] arr, int index, int pos) {
        boolean ok = true;
        for (int[] ints : arr) {
            int position = ints[5];
            if (position > 0 && func(arr[index][pos], ints[position])) {
                ok = false;
                break;
            }
        }
        return ok;
    }

    static boolean compute(int[][] arr, int pos, int minNeighbours) {
        boolean added = false;
        if (arr[pos][3] == minNeighbours) {
            added = checkInsert(arr, pos, 1);
            if (added) {
                arr[pos][5] = 1;
            }
        }
        if (arr[pos][4] == minNeighbours && arr[pos][3] != minNeighbours) {
            if (!added) {
                added = checkInsert(arr, pos, 2);
                if (added) {
                    arr[pos][5] = 2;
                }
            }
        }

        if (arr[pos][3] == minNeighbours || arr[pos][4] == minNeighbours) {
            arr[pos][3] = Integer.MAX_VALUE;
            arr[pos][4] = Integer.MAX_VALUE;
        }
        return added;
    }

    // keep 1 or 2 on chosen and both values on unchosen
    static int minNeighbours(int[][] arr) {
        int min = Integer.MAX_VALUE;

        for (int[] ints : arr) {
            // Line not yet chosen
            if (ints[5] == 0) {
                min = Math.min(min, ints[3]);
                min = Math.min(min, ints[4]);

            } else if (ints[5] == 1) {
                // Line chosen with first value
                min = Math.min(min, ints[3]);
            } else {
                // Line chosen with second value
                min = Math.min(min, ints[4]);
            }
        }
        return min;
    }

    static void computeNeighbours(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][5] == 0) {
                arr[i][3] = findValueInColumn(arr, arr[i][1]);
                arr[i][4] = findValueInColumn(arr, arr[i][2]);
            }
        }
    }

    static int findValueInColumn(int[][] arr, int value) {
        // except me
        int count = -1;
        for (int[] ints : arr) {
            //Line not compute
            if (ints[5] == 0) {
                count += func(value, ints[1]) ? 1 : 0;
                count += func(value, ints[2]) ? 1 : 0;

            } else if (ints[5] == 1) {
                // Line chosen with first value
                count += func(value, ints[1]) ? 1 : 0;
            } else {
                // Line chosen with second value
                count += func(value, ints[2]) ? 1 : 0;
            }
        }
        return count;
    }

    static List<Integer> findMinNeighbours(int[][] arr, int value) {
        List<Integer> nei = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            //Line not compute
            if (arr[i][3] == value) {
                nei.add(i);
            } else if (arr[i][4] == value) {
                nei.add(i);
            }
        }
        return nei;
    }

    static boolean func(int a, int b) {
        return Math.abs(a - b) <= 60;
    }

    static int nbFound(int[][] arr) {
        return (int) Arrays.stream(arr).map(x -> x[5]).filter(x -> x != 0).count();
    }

    // is all lines has been marked as chosen (1 or 2)
    static boolean isFinished(int[][] arr) {
        return arr.length == nbFound(arr);
    }

    // Just check if my answer is valid
    static boolean checkFinal(int[][] arr) {
        List<Integer> finalValues = new ArrayList<>();
        for (int[] ints : arr) {
            int choice = ints[5];
            finalValues.add(ints[choice]);
        }

        boolean ok = false;
        for (int i = 0; i < finalValues.size(); i++) {
            for (int j = 0; j < finalValues.size(); j++) {
                if (i != j) {
                    ok = func(finalValues.get(i), finalValues.get(j));
                    if (ok) {
                        break;
                    }
                }
            }
        }
        return !ok;
    }

    // Concat result on one line
    static String output(int[][] arr) {
        StringBuilder s = new StringBuilder();
        for (int[] ints : arr) {
            s.append(ints[5]).append(STR);
        }
        return s.toString();
    }

}

