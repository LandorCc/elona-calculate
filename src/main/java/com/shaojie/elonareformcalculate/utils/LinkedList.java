package com.shaojie.elonareformcalculate.utils;

import com.shaojie.elonareformcalculate.entity.Node;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
public class LinkedList<E> {

    private Node<E> head;
    private Node<E> tail;
    private Integer size = 0;

    public LinkedList() {
    }

    public boolean checkEmpty() {
        return head.next == null;
    }

//    public Node<E> findPre(int index) {
//        Node<E> rnode = head;
//        int dex = -1;
//        while (rnode.next != null) {
//
//        }
//    }

    /**
     * 在链表尾部插入节点
     * @param o 节点中的data
     */
    public void add(E o) {
        size++;
        Node<E> p = head;
        // 链表的辅助变量temp指向链表的最后一个元素
        while (p != null && p.next != null) {
            p = p.next;
        }
        Node<E> node = new Node<>(o);
        if (p == null) {
            head = node;
        } else {
            p.next = node;
        }
    }

    public List<E> show() {
        Node<E> p = head;
        List<E> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(p.data);
            p = p.next;
        }
        return list;
    }
}

