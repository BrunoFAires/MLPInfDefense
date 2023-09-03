package com.example.demo.model;

import com.example.demo.utils.GameUtil;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.*;

public class GameMap {
    private static final int MAP_WIDTH = 30;
    private static final int MAP_HEIGHT = 20;
    private GameObject[][] map = new GameObject[MAP_HEIGHT][MAP_WIDTH];
    private int x, y;

    public void initializeMap() {
        Random rand = new Random();
        y = rand.nextInt(MAP_HEIGHT-3)+3;
        x = 0;
        map[y][x] = new GameObject(new Rectangle(), 20, Color.BLUE, new Point2D(x, y), true);
        while (x != MAP_WIDTH - 1) {
            int movement = rand.nextInt(3);
            switch (movement) {
                case 0:
                    moveUp(x, y);
                    break;

                case 1:
                    moveDown(x, y);
                    break;
                case 2:
                    moveRight(x, y);
                    break;
            }


        }
    }

    public boolean isValidPosition(int x, int y) {
        if (x <= MAP_WIDTH && y <= MAP_HEIGHT) {
            return map[y][x] == null;
        }
        return true;
    }

    public GameObject getObject(int x, int y) {
        return map[y][x];
    }


    private void moveRight(int x, int y) {
        map[y][x + 1] = new GameObject(new Rectangle(), 20, Color.BLUE, new Point2D(x + 1, y), true);
        this.x = x + 1;
    }

    private void moveUp(int x, int y) {
        if (y - 1 >= 3 && map[y - 1][x] == null) {
            map[y - 1][x] = new GameObject(new Rectangle(), 20, Color.BLUE, new Point2D(x, y - 1), true);
            this.y = y - 1;
        } else {
            moveRight(x, y);
        }
    }

    private void moveDown(int x, int y) {
        if (y + 1 <= MAP_HEIGHT - 1 && map[y + 1][x] == null) {
            map[y + 1][x] = new GameObject(new Rectangle(), 20, Color.BLUE, new Point2D(x, y + 1), true);
            this.y = y + 1;
        } else {
            moveRight(x, y);
        }
    }

    public void draw(Pane gameScene, List<Tower> towers, List<Enemy> enemies, int killedEnemis, int totalCoins) {
        gameScene.getChildren().clear();
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                Rectangle rectangle = new Rectangle(20, 20);
                rectangle.setLayoutX(j * 20);
                rectangle.setLayoutY(i * 20);
                if (map[i][j] != null) {
                    gameScene.getChildren().add(map[i][j].draw());
                } else {
                    rectangle.setFill(Color.BLACK);
                    gameScene.getChildren().add(rectangle);
                }
            }
        }
        for (int i = 0; i < towers.size(); i++) {
            gameScene.getChildren().add(towers.get(i).draw());
        }

        for (int i = 0; i < enemies.size(); i++) {
            gameScene.getChildren().add(enemies.get(i).draw());
        }

        gameScene.getChildren().add(GameUtil.text("Inimigos Mortos: " + killedEnemis, new Point2D(0, 0)));
        gameScene.getChildren().add(GameUtil.text("Moedas: " + totalCoins, new Point2D(15, 0)));
    }

    public List<Point2D> findPath(Point2D start, Point2D end) {
        List<SingleLinkedListNode> reachable =  new ArrayList<>();
        var r = new SingleLinkedListNode(start, null);
        reachable.add(r);
        List<Point2D> explored = new ArrayList<>();
        List<Point2D> a = new ArrayList<>();

        while (!reachable.isEmpty()) {
            Random random = new Random();
            int pos = random.nextInt(reachable.size());
            SingleLinkedListNode point = reachable.get(pos);
            if (point.getData().getX() == end.getX() && point.getData().getY() == end.getY()) {
                return buildPath(point);
            }

            explored.add(point.getData());
            reachable.remove(point);

            List<SingleLinkedListNode> newReachable = getAdjacentPoints(point.data);
            for (int i = 0; i < newReachable.size(); i++) {
                var adjacent = newReachable.get(i);
                if (!reachable.contains(adjacent) && !explored.contains(adjacent.data)) {
                    adjacent.setLinkPrevious(point);
                    reachable.add(adjacent);
                }
            }
        }

        return null;
    }

    private List<Point2D> buildPath(SingleLinkedListNode node) {
        List<Point2D> path = new ArrayList<>();
        while (node.getLinkPrevious() != null) {
            path.add(node.data);
            node = node.getLinkPrevious();
        }
        Collections.reverse(path);

        return path;
    }


    private List<SingleLinkedListNode> getAdjacentPoints(Point2D point) {
        Point2D newPoint = new Point2D(point.getX() - 1, point.getY() - 1);
        List<SingleLinkedListNode> adjacetPoints = new ArrayList<>();
        for (int i = (int) (newPoint.getX()); i <=  (int) (point.getX()+1); i++) {
            for (int j = (int) newPoint.getY(); j <=  (int) (point.getY()+1); j++) {
                Point2D pointToAdd = new Point2D(i, j);
                if (pointToAdd.getX() >= 0 && pointToAdd.getX() <= 29 &&
                        pointToAdd.getY() >= 0 && pointToAdd.getY() <= 19
                        && !isValidPosition((int) pointToAdd.getX(), (int) pointToAdd.getY()) &&
                        !point.equals(pointToAdd)) {
                    adjacetPoints.add(new SingleLinkedListNode(pointToAdd, null));
                }
            }
        }

        return adjacetPoints;
    }

    public List<Point2D> validPositions(int column){
        List<Point2D> positions = new ArrayList<>();
        for (int i = 3; i < 20; i++) {
            if (!isValidPosition(column, i)) {
                positions.add(getObject(column, i).getPoint());
            }
        }

        return positions;
    }

}
