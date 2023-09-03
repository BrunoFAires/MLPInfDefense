package com.example.demo.model;


import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Character extends GameObject {
    protected String name;
    protected int power;
    protected int resistance;
    protected int range;

    public Character(String name, int power, int resistance, int range, Point2D point, Color color, int size) {
        super(new Rectangle(), size, color, point, false);
        this.name = name;
        this.power = power;
        this.resistance = resistance;
        this.range = range;
    }

    public abstract Character copy();
}
