package com.example.demo.utils;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class GameUtil {

    public static Label text(String text, Point2D point2D){
            Label label = new Label(text);
            label.setLayoutY(point2D.getX());
            label.setLayoutX(point2D.getY());
            label.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
            label.setStyle("-fx-text-fill: white;");

            return label;
    }
}
