package com.example.demo.utils;

import com.example.demo.model.Enemy;
import com.example.demo.model.GameMap;
import com.example.demo.model.Tower;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.List;

public class FunctionalGameObject {
    Pane pane;
    List<Tower> towerMenuList;
    java.util.List<Tower> towerList;
    List<Enemy> enemyList;
    Enemy enemy;
    GameMap gameMap;
    int contador;
    List<Point2D> spanwPosition;
    List<Point2D> finishPositions;
    int lifes;
    int totalKilledEnemies;
    int totalCoins;


    public FunctionalGameObject(Pane pane, List<Tower> towerMenuList, List<Tower> towerList, List<Enemy> enemyList, Enemy enemy, GameMap gameMap, int contador, List<Point2D> spanwPosition, List<Point2D> finishPositions, int lifes, int totalKilledEnemies, int totalCoins) {
        this.pane = pane;
        this.towerMenuList = towerMenuList;
        this.towerList = towerList;
        this.enemyList = enemyList;
        this.enemy = enemy;
        this.gameMap = gameMap;
        this.contador = contador;
        this.spanwPosition = spanwPosition;
        this.finishPositions = finishPositions;
        this.lifes = lifes;
        this.totalKilledEnemies = totalKilledEnemies;
        this.totalCoins = totalCoins;
    }

    public static FunctionalGameObject copy(FunctionalGameObject functionalGameObject, List<Enemy> newEnemyList, List<Tower> towerList){
        return new FunctionalGameObject(functionalGameObject.getPane(), functionalGameObject.getTowerMenuList(), towerList,
                newEnemyList, functionalGameObject.getEnemy(), functionalGameObject.getGameMap(), functionalGameObject.getContador() + 1, functionalGameObject.getSpanwPosition(), functionalGameObject.getFinishPositions(),
                functionalGameObject.getLifes(), functionalGameObject.getTotalKilledEnemies(), functionalGameObject.getTotalCoins());
    }

    public Pane getPane() {
        return pane;
    }

    public List<Tower> getTowerMenuList() {
        return towerMenuList;
    }

    public List<Tower> getTowerList() {
        return towerList;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public int getContador() {
        return contador;
    }

    public List<Point2D> getSpanwPosition() {
        return spanwPosition;
    }

    public List<Point2D> getFinishPositions() {
        return finishPositions;
    }

    public int getLifes() {
        return lifes;
    }

    public int getTotalKilledEnemies() {
        return totalKilledEnemies;
    }

    public int getTotalCoins() {
        return totalCoins;
    }
}
