package com.example.demo.model;

import javafx.geometry.Point2D;

public class SingleLinkedListNode {
    protected Point2D data;
    protected SingleLinkedListNode previous;

    public SingleLinkedListNode(Point2D d, SingleLinkedListNode n) {
        data = d;
        previous = n;
    }

    public void setLinkPrevious(SingleLinkedListNode n) {
        previous = n;
    }


    public SingleLinkedListNode getLinkPrevious() {
        return previous;
    }

    public Point2D getData() {
        return data;
    }

    @Override
    public int hashCode() {
        return this.data.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        SingleLinkedListNode a = (SingleLinkedListNode) obj;
        return this.data.equals(a.getData());
    }
}
