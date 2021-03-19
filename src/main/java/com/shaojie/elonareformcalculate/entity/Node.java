package com.shaojie.elonareformcalculate.entity;

import lombok.Data;

@Data
public class Node<E> {
    public E data;
    public Node<E> next;
    public Node<E> pre;

    public Node(E data, Node<E> next,Node<E> pre) {
        this.data = data;
        this.next = next;
        this.pre = pre;
    }

    public Node() {
    }
    public Node(E data) {
        this.data = data;
        this.next = null;
        this.pre = null;
    }
}
