package com.example.demo.controller;

import com.example.demo.model.Character;
import com.example.demo.model.Tower;
import com.example.demo.model.Enemy;
import com.example.demo.model.GameMap;
import com.example.demo.view.GameScene;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;

import java.util.*;

public class GameSceneController {
    private List<Tower> towerMenuList = new ArrayList<>();
    private List<Tower> towerList = new ArrayList<>();
    private List<Enemy> enemyList = new ArrayList<>();
    private List<Enemy> enemyListSpawn   = new ArrayList<>();
    private ContextMenu menu = new ContextMenu();
    private GameScene gameScene;
    private GameMap map = new GameMap();
    private int lifes = 5;
    private int killedEnemies = 0;
    private int xMouseClick, yMouseClick;
    private int counter = 0;
    private int actualCoins = 150;
    private boolean runGame = true;
    Tower t1 = new Tower("Fullstack", 10, 50, 20, 1, 40, 20, new Point2D(0, 0), Color.YELLOW);
    Tower t2 = new Tower("Senior Dev", 15, 50, 20, 2, 30, 100, new Point2D(0, 0), Color.PINK);
    Tower t3 = new Tower("Intern", 5, 50, 20, 1, 300, 10, new Point2D(0, 0), Color.GREEN);
    Enemy e1 = new Enemy("Enemy 1", 25, 20, 3, 20, 15, 10, new Point2D(0, 0), Color.RED, Collections.emptyList());
    Enemy e2 = new Enemy("Enemy 1", 25, 30, 3, 20, 15, 20, new Point2D(0, 0), Color.RED, Collections.emptyList());
    Enemy e3 = new Enemy("Enemy 1", 25, 40, 3, 20, 15, 30, new Point2D(0, 0), Color.RED, Collections.emptyList());

    public GameSceneController(GameScene gameScene) {
        this.gameScene = gameScene;
        towerMenuList.add(t1);
        towerMenuList.add(t2);
        towerMenuList.add(t3);
        enemyListSpawn.add(e1);
        enemyListSpawn.add(e2);
        enemyListSpawn.add(e3);
        map.initializeMap();
        map.initializeMap();
        map.initializeMap();
        map.draw(gameScene.getPane(), Collections.emptyList(), Collections.emptyList(), 0, actualCoins);

        new Thread(() -> {
            try {
                gameLoop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void combat() {
        for (Tower c : towerList) {
            for (Enemy enemy : enemyList) {
                if (enemy.getPoint().distance(c.getPoint()) <= c.getRange() && counter % c.getFireRate() == 0) {
                    c.shoot(enemy);
                    if (!enemy.isAlive()) {
                        killedEnemies++;
                        actualCoins += enemy.getCoins();
                        enemyList.remove(enemy);
                        break;
                    }
                }
            }
        }
    }

    public void showMenu(int x, int y) {
        initializeMenu();
        menu.show(gameScene.getScene().getRoot(), x, y);
        xMouseClick = x;
        yMouseClick = y;
    }

    private void gameLoop() throws InterruptedException {
        while (runGame) {
            Thread.sleep(1000L / 30);
            if (lifes == 0) {
                runGame = false;
                System.out.println("GameOver");
            }
            combat();
            if (counter % 150 == 0) {
                Platform.runLater(() -> {
                    spawnEnemies();
                });
            }
            List<Character> enemies = new ArrayList<>();
            for (Enemy enemy : enemyList) {
                if (enemy.hasFinishedPath()) {
                    lifes--;
                    enemyList.remove(enemy);
                    break;
                }
                if (enemy.ableToMove(counter)) {
                    enemy.move();
                }
                if (enemy.isAlive()) {
                    enemies.add(enemy);
                }
            }
            counter++;
            Platform.runLater(() -> {
                map.draw(gameScene.getPane(), towerList, enemyList, killedEnemies, actualCoins);
            });
        }
    }

    private void addTower(Tower tower) {
        menu.hide();
        this.actualCoins -= tower.getCoast();
        int x = xMouseClick / 20;
        int y = yMouseClick / 20;
        if (map.isValidPosition(x, y) /*&& contador < 10*/) {
            Tower c = tower.copy();
            c.setPoint(new Point2D(x, y));
            towerList.add(c);
            map.draw(gameScene.getPane(), towerList, Collections.emptyList(), killedEnemies, actualCoins);
        }
    }

    private void initializeMenu() {
        menu.getItems().clear();
        for (int i = 0; i < towerMenuList.size(); i++) {
            Tower c = towerMenuList.get(i);
            MenuItem item = new MenuItem("Adicionar " + towerMenuList.get(i).getName());
            item.setOnAction(e -> addTower(c));
            item.setDisable(actualCoins < c.getCoast());
            menu.getItems().add(item);
        }
    }

    private void spawnEnemies() {
        List<Point2D> spawnPositions = map.validPositions(0);
        List<Point2D> endPositions = map.validPositions(29);
        Random rand = new Random();
        Point2D spawnPosition = spawnPositions.get(rand.nextInt(spawnPositions.size()));
        Point2D endPosition = endPositions.get(rand.nextInt(endPositions.size()));
        List<Point2D> enemyPath = map.findPath(spawnPosition, endPosition);
        Enemy e = (Enemy) enemyListSpawn.get(rand.nextInt(3)).copy();
        e.setPoint(spawnPosition);
        e.setPath(enemyPath);
        enemyList.add(e);

    }

}

