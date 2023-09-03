package com.example.demo.model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameObject {
    private Rectangle sprite;
    private int size;
    private Color color;
    private Point2D point;
    private boolean path = true;


    public GameObject(Rectangle sprite, int size, Color color, Point2D point, boolean path){
        this.sprite = sprite;
        this.size = size;
        this.color = color;
        this.point = point;
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public Point2D getPoint() {
        return point;
    }

    public void setPoint(Point2D point) {
        this.point = point;
    }

    public Rectangle getSprite() {
        return sprite;
    }

    public void setSprite(Rectangle sprite) {
        this.sprite = sprite;
    }

    public Rectangle draw() {
        sprite.setFill(color);
        sprite.setWidth(size);
        sprite.setHeight(size);
        sprite.setLayoutX(getPoint().getX()*20);
        sprite.setLayoutY(getPoint().getY()*20);
        return sprite;
    }

    public boolean isPath() {
        return path;
    }
}
