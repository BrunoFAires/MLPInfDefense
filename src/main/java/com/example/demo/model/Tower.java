package com.example.demo.model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Tower extends Character {
    private int fireRate;
    private int coast;

    public Tower(String name, int power, int resistance, int size, int range, int fireRate, int coast, Point2D point, Color color) {
        super(name, power, resistance, range, point, color, size);
        this.fireRate = fireRate;
        this.coast = coast;
    }

    @Override
    public Tower copy() {
        return new Tower(super.name, super.power, super.resistance, getSize(), super.range, this.fireRate, this.coast, getPoint(), getColor());
    }

    public void shoot(Enemy enemy) {
        enemy.addDamage(super.power);
    }

    public String getName() {
        return name;
    }

    public int getRange() {
        return range;
    }

    public int getFireRate() {
        return fireRate;
    }

    public int getPower(){
        return power;
    }
    public int getResistance(){
        return resistance;
    }

    public int getCoast() {
        return coast;
    }
}
