package ru.itmo.client.app.utility;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.itmo.common.model.HumanBeing;

import java.util.HashMap;
import java.util.Map;


/**
 * Класс для отрисовки человечка
 */
public class Animation {

    public Text setText(AnchorPane canvasPane, HumanBeing humanBeing) {
        Text textHumanInfo = new Text(humanBeing.toString());
        textHumanInfo.translateXProperty().bind(canvasPane.widthProperty().divide(2).add(humanBeing.getCoordinates().getX() - 15));
        textHumanInfo.translateYProperty().bind(canvasPane.heightProperty().divide(2).add(-humanBeing.getCoordinates().getY() - 250));
        textHumanInfo.setFill(Color.web(humanBeing.getUser().getColour()).darker());

        return textHumanInfo;
    }

    public Map<Integer, Shape> setClosedEyes(AnchorPane canvasPane, HumanBeing human) {
        Map<Integer, Shape> shapeMap = new HashMap<>();

        Shape leftHair = setLeftHair();
        Shape rightHair = setRightHair();
        Shape frontHair = setFrontHair();
        Shape leftCheek = setLeftCheek();
        Shape rightCheek = setRightCheek();
        Shape leftEye = new Rectangle(16, 16, 10, 4);
        leftEye.setFill(Color.rgb(61, 57, 52));
        Shape rightEye = new Rectangle(45, 16, 10, 4);
        rightEye.setFill(Color.rgb(61, 57, 52));
        Shape head = setHead();
        Shape neck = setNeck();
        Shape body = setBody(Color.web(human.getUser().getColour()));
        Shape leftHand = setLeftHand();
        Shape rightHand = setRightHand();
        Shape leftLeg = setLeftLeg();
        Shape rightLeg = setRightLeg();
        Shape leftBoot = setLeftBoot();
        Shape rightBoot = setRightBoot();

        setCoordinatesOnCanvas(head, human, canvasPane);
        setCoordinatesOnCanvas(leftHair, human, canvasPane);
        setCoordinatesOnCanvas(rightHair, human, canvasPane);
        setCoordinatesOnCanvas(frontHair, human, canvasPane);
        setCoordinatesOnCanvas(leftCheek, human, canvasPane);
        setCoordinatesOnCanvas(rightCheek, human, canvasPane);
        leftEye.translateXProperty().bind(canvasPane.widthProperty().divide(2).add(human.getCoordinates().getX() - 20));
        leftEye.translateYProperty().bind(canvasPane.heightProperty().divide(2).add(-human.getCoordinates().getY() - 50));
        rightEye.translateXProperty().bind(canvasPane.widthProperty().divide(2).add(human.getCoordinates().getX() - 20));
        rightEye.translateYProperty().bind(canvasPane.heightProperty().divide(2).add(-human.getCoordinates().getY() - 50));
        setCoordinatesOnCanvas(neck, human, canvasPane);
        setCoordinatesOnCanvas(body, human, canvasPane);
        setCoordinatesOnCanvas(leftHand, human, canvasPane);
        setCoordinatesOnCanvas(rightHand, human, canvasPane);
        setCoordinatesOnCanvas(leftLeg, human, canvasPane);
        setCoordinatesOnCanvas(rightLeg, human, canvasPane);
        setCoordinatesOnCanvas(leftBoot, human, canvasPane);
        setCoordinatesOnCanvas(rightBoot, human, canvasPane);

        shapeMap.put(3, body);
        shapeMap.put(9, leftHair);
        shapeMap.put(10, rightHair);
        shapeMap.put(4, frontHair);
        shapeMap.put(5, leftCheek);
        shapeMap.put(6, rightCheek);
        shapeMap.put(7, leftEye);
        shapeMap.put(8, rightEye);
        shapeMap.put(2, head);
        shapeMap.put(1, neck);
        shapeMap.put(11, leftHand);
        shapeMap.put(12, rightHand);
        shapeMap.put(13, leftLeg);
        shapeMap.put(16, rightLeg);
        shapeMap.put(14, leftBoot);
        shapeMap.put(15, rightBoot);

        return shapeMap;
    }

    public void animationStart(Shape shape){
        ScaleTransition transition = new ScaleTransition(Duration.millis(50), shape);
        transition.setFromX(0);
        transition.setFromY(0);
        transition.setToX(1);
        transition.setToY(1);
    }

    public void animationFinish(Shape shape) {
            ScaleTransition transition = new ScaleTransition(Duration.seconds(3), shape);
            transition.setFromX(1);
            transition.setFromY(1);
            transition.setToX(0);
            transition.setToY(0);
            transition.play();
    }

    public Rectangle setFrontHair(){
        Rectangle frontHair = new Rectangle(0, 0, 69, 18);
        frontHair.setFill(Color.rgb(61, 57, 52));
        return frontHair;
    }

    public Rectangle setLeftHair(){
        Rectangle leftHair = new Rectangle(0, 0, 6, 30);
        leftHair.setFill(Color.rgb(61, 57, 52));
        return leftHair;
    }

    public Rectangle setRightHair(){
        Rectangle rightHair = new Rectangle(65, 0, 6, 30);
        rightHair.setFill(Color.rgb(61, 57, 52));
        return rightHair;
    }

    public Rectangle setHead(){
        Rectangle head = new Rectangle(4, 0, 64, 57);
        head.setFill(Color.rgb(248, 218, 171));
        return head;
    }

    public Rectangle setLeftEye(){
        Rectangle leftEye = new Rectangle(21, 24, 4, 21);
        leftEye.setFill(Color.rgb(61, 57, 52));
        return leftEye;
    }

    public Rectangle setRightEye(){
        Rectangle rightEye = new Rectangle(47, 24, 4, 21);
        rightEye.setFill(Color.rgb(61, 57, 52));
        return rightEye;
    }

    public Rectangle setLeftCheek(){
        Rectangle leftCheek = new Rectangle(4, 43,20, 14);
        leftCheek.setFill(Color.rgb(255, 194, 149));
        return leftCheek;
    }

    public Rectangle setRightCheek(){
        Rectangle rightCheek = new Rectangle(48, 43, 20, 14);
        rightCheek.setFill(Color.rgb(255, 194, 149));
        return rightCheek;
    }

    public Rectangle setNeck(){
        Rectangle neck = new Rectangle(29, 57, 13, 6);
        neck.setFill(Color.rgb(248, 218, 171));
        return neck;
    }

    public Polygon setBody(Color color){
        Polygon body = new Polygon();
        body.getPoints().addAll(25.00, 62.00,
                46.00, 62.00,
                55.00, 95.00,
                54.00, 118.00,
                38.00, 118.00,
                38.00, 115.00,
                34.00, 115.00,
                34.00, 118.00,
                18.00, 118.00,
                17.00, 95.00);
        body.setFill(color);
        return body;
    }

    public Polygon setLeftHand(){
        Polygon leftHand = new Polygon();
        leftHand.getPoints().addAll(25.00, 62.00,
                5.00, 99.00,
                9.00, 102.00,
                17.00, 96.00);
        leftHand.setFill(Color.rgb(248, 218, 171));
        return leftHand;
    }

    public Polygon setRightHand(){
        Polygon rightHand = new Polygon();
        rightHand.getPoints().addAll(46.00, 62.00,
                67.00, 99.00,
                63.00, 102.00,
                55.00, 96.00);
        rightHand.setFill(Color.rgb(248, 218, 171));
        return rightHand;
    }

    public Polygon setLeftLeg(){
        Polygon leftLeg = new Polygon();
        leftLeg.getPoints().addAll(18.00, 118.00,
                20.00, 137.00,
                29.00, 139.00,
                34.00, 118.00);
        leftLeg.setFill(Color.rgb(248, 218, 171));
        return leftLeg;
    }

    public Polygon setRightLeg(){
        Polygon rightLeg = new Polygon();
        rightLeg.getPoints().addAll(38.00, 118.00,
                54.00, 118.00,
                52.00, 137.00,
                42.00, 139.00);
        rightLeg.setFill(Color.rgb(248, 218, 171));
        return rightLeg;
    }

    public Polygon setLeftBoot(){
        Polygon leftBoot = new Polygon();
        leftBoot.getPoints().addAll(20.00, 137.00,
                29.00, 139.00,
                17.00, 139.00);
        leftBoot.setFill(Color.rgb(61, 57, 52));
        return leftBoot;
    }

    public Polygon setRightBoot() {
        Polygon rightBoot = new Polygon();
        rightBoot.getPoints().addAll(52.00, 137.00,
                42.00, 139.00,
                55.00, 139.00);
        rightBoot.setFill(Color.rgb(61, 57, 52));
        return  rightBoot;
    }

    public Polygon setHeroCloak(){
        Polygon heroCloak = new Polygon();
        heroCloak.getPoints().addAll(25.0, 62.0,
                46.0, 62.0,
                65.0, 127.0,
                6.0, 127.0);
        heroCloak.setFill(Color.rgb(238, 59, 6));
        return heroCloak;
    }

    private void setCoordinatesOnCanvas(Shape figure, HumanBeing human, AnchorPane canvasPane){
        figure.translateXProperty().bind(canvasPane.widthProperty().divide(2).add(human.getCoordinates().getX() - 20));
        figure.translateYProperty().bind(canvasPane.heightProperty().divide(2).add(-human.getCoordinates().getY() - 70));
    }
}
