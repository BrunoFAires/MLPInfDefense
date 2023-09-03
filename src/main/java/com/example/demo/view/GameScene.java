package com.example.demo.view;

import com.example.demo.controller.GameSceneController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameScene {
    private static final int LARGURA = 600;
    private static final int ALTURA = 400;
    private Scene scene;
    private Pane pane;


    private GameSceneController gameSceneController;

    public GameScene() {
        this.pane = new Pane();
        pane.setPrefSize(LARGURA, ALTURA);
        scene = new Scene(pane);
        clickScreen();
        gameSceneController = new GameSceneController(this);
    }

    public void clickScreen() {
        pane.setOnMouseClicked(event -> {
            int clickX = (int) event.getSceneX();
            int clickY = (int) event.getSceneY();
            gameSceneController.showMenu(clickX, clickY);
        });
    }

    public Scene getScene() {
        return scene;
    }
    public Pane getPane() {
        return pane;
    }
}
