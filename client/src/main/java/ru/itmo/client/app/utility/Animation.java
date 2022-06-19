package ru.itmo.client.app.utility;

import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import ru.itmo.common.model.HumanBeing;


/**
 * Класс для отрисовки человечка
 */
public class Animation {

    public void animationLeft(Shape leftEye, AnchorPane canvasPane, HumanBeing human) {
        Rectangle closedLeftEye = new Rectangle(16, 16, 10, 4);

        closedLeftEye.translateXProperty().bind(canvasPane.widthProperty().divide(2).add(human.getCoordinates().getX()-20));
        closedLeftEye.translateYProperty().bind(canvasPane.heightProperty().divide(2).add(-human.getCoordinates().getY()-70));

        AnimationTimer animationStart = new AnimationTimer() {
            @Override
            public void handle(long now) {
                leftEye.setVisible(false);
                closedLeftEye.setVisible(true);
            }
        };

        AnimationTimer animationFinish = new AnimationTimer() {
            @Override
            public void handle(long now) {
                closedLeftEye.setVisible(false);
                leftEye.setVisible(true);
            }
        };

        animationStart.start();
        animationFinish.start();

    }

    public void animationRight(Shape rightEye, AnchorPane canvasPane, HumanBeing human) {
        Rectangle closedRightEye = new Rectangle(45, 16, 10, 4);

        closedRightEye.translateXProperty().bind(canvasPane.widthProperty().divide(2).add(human.getCoordinates().getX()-20));
        closedRightEye.translateYProperty().bind(canvasPane.heightProperty().divide(2).add(-human.getCoordinates().getY()-70));

        AnimationTimer animationStart = new AnimationTimer() {
            @Override
            public void handle(long now) {
                rightEye.setVisible(false);
                closedRightEye.setVisible(true);
            }
        };

        AnimationTimer animationFinish = new AnimationTimer() {
            @Override
            public void handle(long now) {
                closedRightEye.setVisible(false);
                rightEye.setVisible(true);
            }
        };

        animationStart.start();
        animationFinish.start();

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
}
