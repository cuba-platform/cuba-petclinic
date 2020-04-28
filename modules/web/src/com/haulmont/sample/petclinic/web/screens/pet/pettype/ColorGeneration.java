package com.haulmont.sample.petclinic.web.screens.pet.pettype;

import java.awt.*;
import java.util.Random;

public class ColorGeneration {

    public static String randomColor() {
        Random ra = new Random();
        int r, g, b;
        r=ra.nextInt(255);
        g=ra.nextInt(255);
        b=ra.nextInt(255);
        Color color = new Color(r,g,b);
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6) {
            hex = "0" + hex;
        }
        return hex;
    }

}
