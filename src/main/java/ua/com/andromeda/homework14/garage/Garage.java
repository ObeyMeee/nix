package ua.com.andromeda.homework14.garage;

import ua.com.andromeda.homework10.model.Vehicle;

import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Garage<T extends Vehicle> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void add(T vehicle) {
        if (head == null) {
            head = tail = new Node<>(null, vehicle, null);
        } else {
            tail.next = new Node<>(tail, vehicle, null);
            tail = tail.next;
        }
        ++size;
    }

    public void add(int index, T vehicle) {
        Node<T> newNode;
        if (index == 0) {
            newNode = new Node<>(null, vehicle, head);
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            add(vehicle);
        } else {
            Node<T> prev = getNode(index - 1);
            newNode = new Node<>(prev, vehicle, prev.next);
            prev.next = newNode;
            newNode.next.prev = newNode;
        }
        ++size;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " must be less than list size( " + size + ")");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    public T get(int index) {
        return getNode(index).vehicle;
    }

    public void remove(int index) {
        checkIndex(index);
        if (isEmpty()) {
            throw new IllegalStateException("Cannot remove element from empty garage");
        }

        if (index == 0) {
            head = head.next;
            head.prev = null;
        } else if (index == size - 1) {
            tail = tail.prev;
            tail.next = null;
        } else {
            Node<T> beforeRemovedNode = getNode(index - 1);
            Node<T> afterRemovedNode = getNode(index + 1);
            beforeRemovedNode.next = afterRemovedNode;
            afterRemovedNode.prev = beforeRemovedNode;
        }
    }

    public void set(int index, T vehicle) {
        checkIndex(index);
        Node<T> nodeToBeUpdated = getNode(index);
        nodeToBeUpdated.restylingNumber++;
        nodeToBeUpdated.updatedDate = new Date();
        nodeToBeUpdated.vehicle = vehicle;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public int getRestylingNumber(int index) {
        checkIndex(index);
        return getNode(index).restylingNumber;
    }

    public Date getCreatedDate(int index) {
        checkIndex(index);
        return getNode(0).createdDate;
    }

    public Date getUpdatedDate(int index) {
        checkIndex(index);
        return getNode(0).updatedDate;
    }

    @Override
    public Iterator<T> iterator() {

        final Garage<T> garage = this;
        return new Iterator<>() {
            final Node<T> firstNode = garage.head;
            Node<T> currentNode = null;

            @Override
            public boolean hasNext() {
                if (garage.isEmpty()) {
                    return false;
                } else if (currentNode == null) {
                    return true;
                } else return currentNode != garage.tail;
            }

            @Override
            public T next() {
                if (garage.isEmpty()) {
                    throw new NoSuchElementException();
                } else if (currentNode == null) {
                    this.currentNode = firstNode;
                    return currentNode.vehicle;
                } else if (currentNode.next == null) {
                    throw new NoSuchElementException();
                }
                this.currentNode = currentNode.next;
                return currentNode.vehicle;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Garage{\n");
        for (Node<T> current = head; current != null; current = current.next) {
            builder.append(current.vehicle)
                    .append(", created date=").append(current.createdDate)
                    .append(System.lineSeparator());
        }

        builder.append("}\n");
        return builder.toString();
    }

    private static class Node<T> {

        T vehicle;
        Node<T> next;
        Node<T> prev;
        Date createdDate;
        Date updatedDate;
        int restylingNumber;

        public Node(Node<T> prev, T vehicle, Node<T> next) {
            this.prev = prev;
            this.vehicle = vehicle;
            this.next = next;
            createdDate = new Date();
            updatedDate = new Date(createdDate.getTime());
        }

    }
}
