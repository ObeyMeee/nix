package ua.com.andromeda.homework15;

import ua.com.andromeda.homework10.model.Vehicle;

import java.math.BigDecimal;
import java.util.Comparator;

public class MyTree<T extends Vehicle> {
    private final Comparator<T> comparator;
    private Node<T> root;
    private int size;

    public MyTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public boolean add(T data){
        if (data == null) {
            return false;
        }
        root = add(root, data);
        size++;
        return true;
    }

    private Node<T> add(Node<T> current, T data) {
        if(data == null){
            return current;
        }
        if(current == null){
            return new Node<>(data);
        }

        int compare = comparator.compare(data, current.data);
        if(compare < 0){
            current.left = add(current.left, data);
        } else if (compare > 0) {
            current.right = add(current.right, data);
        }
        return current;
    }

    public void print(){
        print(root);
    }

    // direct tree traversal
    // tree is shown in sorted order
    private void print(Node<T> node){
        if (node == null) {
            return;
        }
        print(node.left);
        if(node == this.root){
            System.out.println("root ==> " + node.data);
        }else {
            System.out.println(node.data);
        }
        print(node.right);

    }

    public BigDecimal getLeftTreePrice(){
        return calculateSummaryPrice(root.left);
    }

    public BigDecimal getRightTreePrice(){
        return calculateSummaryPrice(root.right);
    }

    private BigDecimal calculateSummaryPrice(Node<T> current) {
        if (current == null) {
            return BigDecimal.ZERO;
        }

        return calculateSummaryPrice(current.left)
                .add(current.data.getPrice())
                .add(calculateSummaryPrice(current.right));
    }

    public int size() {
        return size;
    }

    private static class Node<T> {
        Node<T> left;
        Node<T> right;
        T data;

        Node(T data) {
            this.data = data;
        }
    }
}
