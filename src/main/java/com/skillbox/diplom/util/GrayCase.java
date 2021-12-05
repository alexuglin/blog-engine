package com.skillbox.diplom.util;

import com.github.cage.Cage;
import com.github.cage.image.ConstantColorGenerator;
import com.github.cage.image.Painter;
import com.github.cage.token.RandomTokenGenerator;

import java.awt.*;

public class GrayCase extends Cage {
    private static final int HEIGHT = 35;
    private static final int WIDTH = 100;
    private static final int TOKEN_LEN_MIN = 4;
    private static final int TOKEN_LEN_DELTA = 2;

    public GrayCase() {
        super(new Painter(WIDTH, HEIGHT, null, null, null, null),
                null, new ConstantColorGenerator(Color.DARK_GRAY), null,
                Cage.DEFAULT_COMPRESS_RATIO, new RandomTokenGenerator(null, null,
                        TOKEN_LEN_MIN, TOKEN_LEN_DELTA), null);
    }
}
