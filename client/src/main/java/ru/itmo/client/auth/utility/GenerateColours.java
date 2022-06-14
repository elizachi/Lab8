package ru.itmo.client.auth.utility;

import javafx.scene.paint.Color;

import java.util.Random;

public class GenerateColours {
    private static final Random random = new Random();

    public static Color generateColor() {

        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        float o = 1.0F;
        Color newColor = new Color(r, g, b, o);

        return newColor;
    }
}
