package ru.itmo.client.app.utility;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Animation {

    public Collection<? extends Node> setFigure(Color color) {

        //волосы
        Rectangle frontHair = new Rectangle(0, 0, 69, 18);
        Rectangle leftHair = new Rectangle(0, 0, 6, 30);
        Rectangle rightHair = new Rectangle(64, 0, 6, 30);
        frontHair.setFill(Color.rgb(61, 57, 52));
        leftHair.setFill(Color.rgb(61, 57, 52));
        rightHair.setFill(Color.rgb(61, 57, 52));

        //голова
        Rectangle head = new Rectangle(4, 0, 64, 57);
        head.setFill(Color.rgb(248, 218, 171));

        //глаза
        Rectangle leftEye = new Rectangle(21, 24, 4, 21);
        Rectangle rightEye = new Rectangle(47, 24, 4, 21);
        leftEye.setFill(Color.rgb(61, 57, 52));
        rightEye.setFill(Color.rgb(61, 57, 52));

        //щеки
        Rectangle leftCheek = new Rectangle(4, 43,20, 14);
        Rectangle rightCheek = new Rectangle(48, 43, 20, 14);
        leftCheek.setFill(Color.rgb(255, 194, 149));
        rightCheek.setFill(Color.rgb(255, 194, 149));

        //шея
        Rectangle neck = new Rectangle(29, 57, 13, 6);
        neck.setFill(Color.rgb(248, 218, 171));

        //туловище
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

        //руки
        Polygon leftHand = new Polygon();
        leftHand.getPoints().addAll(25.00, 62.00,
                5.00, 99.00,
                9.00, 102.00,
                17.00, 96.00);
        Polygon rightHand = new Polygon();
        rightHand.getPoints().addAll(46.00, 62.00,
                67.00, 99.00,
                63.00, 102.00,
                55.00, 96.00);
        leftHand.setFill(Color.rgb(248, 218, 171));
        rightHand.setFill(Color.rgb(248, 218, 171));

        //ноги
        Polygon leftLeg = new Polygon();
        leftLeg.getPoints().addAll(18.00, 118.00,
                20.00, 137.00,
                29.00, 139.00,
                34.00, 118.00);
        Polygon rightLeg = new Polygon();
        rightLeg.getPoints().addAll(38.00, 118.00,
                54.00, 118.00,
                52.00, 137.00,
                42.00, 139.00);
        leftLeg.setFill(Color.rgb(248, 218, 171));
        rightLeg.setFill(Color.rgb(248, 218, 171));

        //ботинки
        Polygon leftBoot = new Polygon();
        leftBoot.getPoints().addAll(20.00, 137.00,
                29.00, 139.00,
                17.00, 139.00);
        Polygon rightBoot = new Polygon();
        rightBoot.getPoints().addAll(52.00, 137.00,
                42.00, 139.00,
                55.00, 139.00);
        leftBoot.setFill(Color.rgb(61, 57, 52));
        rightBoot.setFill(Color.rgb(61, 57, 52));

        //добавляются элементы к коллекции
        return  Arrays.asList(head, frontHair, leftHair, rightHair, leftCheek, rightCheek, leftEye, rightEye, neck,
                leftHand, rightHand, leftLeg, rightLeg, leftBoot, rightBoot, body);
    }
}
