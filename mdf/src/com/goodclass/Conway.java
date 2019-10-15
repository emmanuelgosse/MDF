package com.goodclass;

/** GameOfLife */
public class Conway {

  public static void main(String[] args) {
    int M = 10, N = 10;

    int nbSurpopulation = 4;
    int nbBorn = 2;

    // Intiliazing the grid.
    int[][] grid = {
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
      {0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
      {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    // Displaying the grid
    System.out.println("Original Generation");
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        if (grid[i][j] == 0) System.out.print(".");
        else System.out.print("*");
      }
      System.out.println();
    }
    System.out.println();

    for (int i = 0; i < 4; i++) {
      nextGeneration(grid, M, N, nbSurpopulation, nbBorn);
    }
  }

  // Function to print next generation
  static void nextGeneration(int[][] grid, int M, int N, int nbSurpopulation, int nbBorn) {
    int[][] future = new int[M][N];

    // Loop through every cell
    for (int l = 1; l < M - 1; l++) {
      for (int m = 1; m < N - 1; m++) {
        // finding no Of Neighbours that are alive
        int aliveNeighbours = 0;
        for (int i = -1; i <= 1; i++)
          for (int j = -1; j <= 1; j++) aliveNeighbours += grid[l + i][m + j];

        // The cell needs to be subtracted from
        // its neighbours as it was counted before
        aliveNeighbours -= grid[l][m];

        // Implementing the Rules of Life

        // Cell is lonely and dies
        if ((grid[l][m] == 1) && (aliveNeighbours < 2)) future[l][m] = 0;

        // Cell dies due to over population
        else if ((grid[l][m] == 1) && (aliveNeighbours > nbSurpopulation)) future[l][m] = 0;

        // A new cell is born
        else if ((grid[l][m] == 0) && (aliveNeighbours == nbBorn)) future[l][m] = 1;

        // Remains the same
        else future[l][m] = grid[l][m];
      }

      grid = future;
    }

    System.out.println("Next Generation");
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        if (future[i][j] == 0) System.out.print(".");
        else System.out.print("*");
      }
      System.out.println();
    }
  }
}
