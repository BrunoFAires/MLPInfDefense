package com.example.demo.model;


import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.List;

public class Enemy extends Character {
    private int velocity;
    private List<Point2D> path;
    private boolean alive = true;
    private int coins;

    public Enemy(String name, int power, int resistance, int range, int size, int velocity, int coins, Point2D point, Color color, List<Point2D> path) {
        super(name, power, resistance, range, point, color, size);
        this.velocity = velocity;
        this.path = path;
        this.coins = coins;
    }

    @Override
    public Character copy() {
        return new Enemy(super.name, super.power, super.resistance, super.range, getSize(), getVelocity(), this.coins, getPoint(), getColor(), getPath());

    }

    public int getVelocity() {
        return velocity;
    }

    public List<Point2D> getPath() {
        return path;
    }

    public void addDamage(int damage) {
        var a = getSprite();
        if (alive) {
            a.setOpacity((double) (super.resistance - damage) / super.resistance);
        }
        setSprite(a);
        super.resistance = super.resistance - damage;
        if (resistance <= 0) {
            alive = false;
        }
    }

    public void setPath(List<Point2D> path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public int getRange() {
        return range;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getCoins() {
        return coins;
    }

    public int getPower(){
        return super.power;
    }

    public int getResistance(){
        return resistance;
    }

    public boolean hasFinishedPath(){
        return getPath().isEmpty();
    }

    public boolean ableToMove(int counter){
        return counter % getVelocity() == 0
                && isAlive()
                && !getPath().isEmpty();
    }

    public void move(){
        setPoint(getPath().get(0));
        getPath().remove(0);
    }
}
