package com.example.android_chicken_invaders.bsp;

public class BSPTree {
    private Node root;

    public void insert(Point point) {
        root = insert(root, point, true);
    }

    private Node insert(Node node, Point point, boolean splitX) {
        if (node == null) {
            return new Node(point);
        }

        if ((splitX && point.x < node.point.x) || (!splitX && point.y < node.point.y)) {
            node.left = insert(node.left, point, !splitX);
        } else {
            node.right = insert(node.right, point, !splitX);
        }

        return node;
    }

    public boolean search(Point point) {
        return search(root, point, true);
    }

    private boolean search(Node node, Point point, boolean splitX) {
        if (node == null) {
            return false;
        }

        if (node.point.x == point.x && node.point.y == point.y) {
            return true;
        }

        if ((splitX && point.x < node.point.x) || (!splitX && point.y < node.point.y)) {
            return search(node.left, point, !splitX);
        } else {
            return search(node.right, point, !splitX);
        }
    }
}