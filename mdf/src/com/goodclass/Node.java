package com.goodclass;

import java.util.HashSet;
import java.util.Set;

public class Node {
    public String key;
    public Set<Node> children = new HashSet<>();

    public Node(String key) {
        this.key = key;
    }

    public Node(String key, String value) {
        this.key = key;
        this.children.add(new Node(value));
    }

    public String getKey() {
        return key;
    }

    public Set<Node> getChildren() {
        return children;
    }

    public int height() {
        if (children.isEmpty()) {
            return 0;
        }
        int height = 1;
        for (Node subtree : children) {
            height = Math.max(height, subtree.height());
        }
        return height + 1;
    }


    public int nbChildren(int level, int current_level) {
        current_level++;
        if (level == current_level) {
            return children.size();
        }

        int sum = 0;
        for (Node subtree : children) {
            sum += subtree.nbChildren(level, current_level);
        }
        return sum;
    }
}
