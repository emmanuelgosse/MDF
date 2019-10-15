package com.goodclass;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    final private Board board;
    final private Position start;
    final private Position end;


    public PathFinder(Board board, int startX, int startY, int endX, int endY) {
        this.board = board;
        this.start = new Position(startX, startY);
        this.end = new Position(endX, endY);
    }

    /**
     * Gets the shortest path from the start to end positions.  This method
     * takes all of the paths, then determines which one is shortest and returns that.
     */
    public List<Position> shortestPath() {
        List<List<Position>> allPaths = this.getAllPaths();
        List<Position> shortestPath = null;
        for (List<Position> path : allPaths) {
            if (shortestPath == null) {
                shortestPath = path;
            } else if (shortestPath.size() > path.size()) {
                shortestPath = path;
            }
        }
        return shortestPath;
    }

    /**
     * Convenience method for starting the getAllPaths process.
     *
     * @return all of the paths from the start to end positions
     */
    private List<List<Position>> getAllPaths() {
        return this.getAllPaths(new ArrayList<>(), new ArrayList<>(), start);
    }

    /**
     * Gets all of the paths from the start to end position.  This is   done recursively by visiting every
     * position, while following the rules that you can only move to a  position with a value greater
     * than the position you're currently on.  When reaching the end position, the path is added to
     * the list of all found paths, which is returned.
     */
    private List<List<Position>> getAllPaths(List<List<Position>> paths, List<Position> path, Position position) {
        path.add(position);
        if (position.equals(end)) {
            paths.add(path);
            return paths;
        }

        //x+
        if (position.x + 1 < board.getWidth()) {
            Position xp = new Position(position.x + 1, position.y);
            if (board.positionValue(position) < board.positionValue(xp)) {
                getAllPaths(paths, new ArrayList<>(path), xp);
            }
        }
        //x-
        if (position.x - 1 >= 0) {
            Position xm = new Position(position.x - 1, position.y);
            if (board.positionValue(position) < board.positionValue(xm)) {
                getAllPaths(paths, new ArrayList<>(path), xm);
            }
        }
        //y+
        if (position.y + 1 < board.getHeight()) {
            Position yp = new Position(position.x, position.y + 1);
            if (board.positionValue(position) < board.positionValue(yp)) {
                getAllPaths(paths, new ArrayList<>(path), yp);
            }
        }
        //y-
        if (position.y - 1 >= 0) {
            Position ym = new Position(position.x, position.y - 1);
            if (board.positionValue(position) < board.positionValue(ym)) {
                getAllPaths(paths, new ArrayList<>(path), ym);
            }
        }

        return paths;
    }

    /**
     * Run the example then print the results.
     *
     * @param args na
     */
    public static void main(String[] args) {
        int[][] array = {{5, 13, 2, 5, 2},
                {14, 24, 32, 3, 24},
                {15, 7, 33, 1, 7},
                {45, 40, 37, 24, 70},
                {47, 34, 12, 25, 2},
                {52, 56, 68, 76, 100}
        };

        final Board board = new Board(array);
        final Position end = new Position(board.getWidth() - 1, board.getHeight() - 1);
        final PathFinder pathFinder = new PathFinder(board, 0, 0, board.getWidth() - 1, board.getHeight() - 1);

        final List<Position> path = pathFinder.shortestPath();

        System.out.println("Shortest Path: ");
        for (Position position : path) {
            if (!position.equals(end)) {
                System.out.print(position + " -> ");
            } else {
                System.out.println(position);
            }
        }
        System.out.println();
    }

    public static class Board {
        final int[][] board;

        public Board(int[][] board) {
            this.board = board;
        }

        final int positionValue(Position position) {
            return this.board[position.y][position.x];
        }

        final int getWidth() {
            return board[0].length;
        }

        final int getHeight() {
            return board.length;
        }
    }

    public static class Position {
        final private int x;
        final private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            if (x != position.x) return false;
            return y == position.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}