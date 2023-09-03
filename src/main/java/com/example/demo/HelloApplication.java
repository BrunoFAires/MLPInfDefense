package com.example.demo;

import com.example.demo.model.Enemy;
import com.example.demo.model.GameMap;
import com.example.demo.model.Tower;
import com.example.demo.utils.FunctionalGameObject;
import com.example.demo.view.GameScene;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HelloApplication extends Application {
    private AnimationTimer gameLoop;
    GameScene gameScene = new GameScene();



/*    @Override
    public void start(Stage stage) {
       stage.setScene(gameScene.getScene());

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {

            }
        };

        gameLoop.start();

        stage.show();
    }*/
    @Override
    public void start(Stage stage) {
        Scene cena;
        Pane pane;
        int lifes = 5;
        ContextMenu menu = new ContextMenu();

        Tower t1 = new Tower("Fullstack", 10, 50, 20, 1, 40, 20, new Point2D(0, 0), Color.YELLOW);
        Tower t2 = new Tower("Senior Dev", 15, 50, 20, 2, 30, 100, new Point2D(0, 0), Color.PINK);
        Tower t3 = new Tower("Intern", 100, 50, 20, 1, 300, 10, new Point2D(0, 0), Color.GREEN);
        List<Tower> towerMenuList = new ArrayList<>(List.of(t1, t2, t3));
        pane = new Pane();
        pane.setPrefSize(600, 400);
        cena = new Scene(pane);

        stage.setScene(cena);
        GameMap map = new GameMap();
        map.initializeMap();
        map.initializeMap();
        map.initializeMap();
        map.initializeMap();
        map.initializeMap();

        List<Point2D> spawnPositions = map.validPositions(0);
        List<Point2D> endPositions = map.validPositions(29);
        Random rand = new Random();
        Point2D spawnPosition = spawnPositions.get(rand.nextInt(spawnPositions.size()));
        Point2D endPosition = endPositions.get(rand.nextInt(endPositions.size()));
        List<Point2D> enemyPath = map.findPath(spawnPosition, endPosition);
        Enemy e = new Enemy("Enemy 1", 25, 20, 3, 20, 50, 10, new Point2D(0, 0), Color.RED, Collections.emptyList());
        e.setPoint(spawnPosition);
        e.setPath(enemyPath);


        FunctionalGameObject functionalGameObject = new FunctionalGameObject(pane, towerMenuList, Collections.emptyList(), List.of(e), e, map, 1, spawnPositions, endPositions, 5, 0, 150);
        gameLoop(functionalGameObject);
        stage.show();


    }

    void gameLoop(FunctionalGameObject functionalGameObject) {
        javafx.animation.KeyFrame keyFrame = new javafx.animation.KeyFrame(
                javafx.util.Duration.millis(10),
                event -> {
                    List<Tower> towerList = towerLogic(functionalGameObject);
                    List<Enemy> newEnemyList = combat(functionalGameObject, enemiesLogic)
                            .stream()
                            .filter(e -> e.getResistance() > 0)
                            .collect(Collectors.toList());

                    FunctionalGameObject newFunctionalGameObject     = FunctionalGameObject.copy(functionalGameObject, newEnemyList, towerList);
                    functionalGameObject.getGameMap().draw(functionalGameObject.getPane(), towerList, functionalGameObject.getEnemyList(), functionalGameObject.getTotalKilledEnemies(), functionalGameObject.getTotalCoins());
                    gameLoop(newFunctionalGameObject);
                }
        );

        new javafx.animation.Timeline(keyFrame).play();
    }

    private List<Enemy> combat(FunctionalGameObject functionalGameObject, Function<FunctionalGameObject, List<Enemy>> enemiesLogic) {

        List<Enemy> enemies = enemiesLogic.apply(functionalGameObject);
        List<Tower> towers = functionalGameObject.getTowerList();

        return enemies.stream().map(e -> {
                    Tower tower = towers.stream().filter(t -> e.getPoint().distance(t.getPoint()) <= t.getRange()).findFirst().orElse(null);
                    if (tower != null) {
                        return new Enemy(e.getName(), e.getPower(), e.getResistance() - tower.getPower(),
                                e.getRange(), e.getSize(), e.getVelocity(), e.getCoins(), e.getPoint(), e.getColor(), e.getPath());
                    }
                    return e;
                }
        ).collect(Collectors.toList());
    }


    Function<Enemy, Enemy> move = (enemy) -> {
        Point2D point = enemy.getPath().get(0);
        return new Enemy(enemy.getName(), enemy.getPower(), enemy.getResistance(), enemy.getRange(),
                enemy.getSize(), enemy.getVelocity(), enemy.getCoins(), point, enemy.getColor(),
                enemy.getPath().subList(1, enemy.getPath().size()));
    };

    private Enemy applyEnemy(Enemy enemy, Function<Enemy, Enemy> enemyAction) {
        return enemyAction.apply(enemy);
    }


    Function<FunctionalGameObject, List<Enemy>> enemiesLogic = (functionalGameObject) -> {
        List<Enemy> newEnemyList = functionalGameObject.getEnemyList().stream().filter(e -> e.getPoint().getX() != 29)
                .map(e -> functionalGameObject.getContador() % e.getVelocity() == 0 ? applyEnemy(e, move) : e)
                .collect(Collectors.toList());
        if (functionalGameObject.getContador() % 500 == 0) {
            Random random = new Random();
            Enemy e = (Enemy) functionalGameObject.getEnemy().copy();
            Point2D spawnPoint = functionalGameObject.getSpanwPosition().get(random.nextInt(functionalGameObject.getSpanwPosition().size()));
            List<Point2D> enemyPath = functionalGameObject.getGameMap().findPath(spawnPoint,
                    functionalGameObject.getFinishPositions().get(random.nextInt(functionalGameObject.getFinishPositions().size())));
            e.setPath(enemyPath);
            e.setPoint(spawnPoint);
            newEnemyList.add(e);
        }

        return newEnemyList;
    };

    public List<Tower> towerLogic(FunctionalGameObject functionalGameObject) {
        List<Tower> towers = new ArrayList<>();
        List<Tower> towerMenuList = functionalGameObject.getTowerMenuList();
        int totalCoins = functionalGameObject.getTotalCoins();

        functionalGameObject.getTowerList().forEach(it -> towers.add(it.copy()));

        functionalGameObject.getPane().setOnMouseClicked(event -> {
            int clickX = (int) event.getSceneX() / 20;
            int clickY = (int) event.getSceneY() / 20;
            if (functionalGameObject.getGameMap().isValidPosition(clickX, clickY)) {
                if (event.isControlDown() && functionalGameObject.getTowerMenuList().get(0).getCoast() <= totalCoins) {
                    Tower c = new Tower(towerMenuList.get(0).getName(), towerMenuList.get(0).getPower(), towerMenuList.get(0).getResistance(), towerMenuList.get(0).getSize(), towerMenuList.get(0).getRange(), towerMenuList.get(0).getFireRate(), towerMenuList.get(0).getCoast(), new Point2D(clickX, clickY), towerMenuList.get(0).getColor());
                    towers.add(c);
                }

                if (event.isAltDown() && towerMenuList.get(1).getCoast() <= totalCoins) {
                    Tower c = new Tower(towerMenuList.get(1).getName(), towerMenuList.get(1).getPower(), towerMenuList.get(1).getResistance(), towerMenuList.get(0).getSize(), towerMenuList.get(1).getRange(), towerMenuList.get(1).getFireRate(), towerMenuList.get(1).getCoast(), new Point2D(clickX, clickY), towerMenuList.get(1).getColor());
                    towers.add(c);
                }

                if (event.isShiftDown() && towerMenuList.get(2).getCoast() <= totalCoins) {
                    Tower c = new Tower(towerMenuList.get(2).getName(), towerMenuList.get(2).getPower(), towerMenuList.get(2).getResistance(), towerMenuList.get(2).getSize(), towerMenuList.get(2).getRange(), towerMenuList.get(2).getFireRate(), towerMenuList.get(2).getCoast(), new Point2D(clickX, clickY), towerMenuList.get(2).getColor());
                    towers.add(c);
                }
            }
        });

        return towers;
    }

    public static void main(String[] args) {
        launch();
    }
}
