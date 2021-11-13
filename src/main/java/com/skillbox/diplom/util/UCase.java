package com.skillbox.diplom.util;

import com.github.cage.Cage;
import com.github.cage.image.ConstantColorGenerator;
import com.github.cage.image.Painter;
import com.github.cage.token.RandomCharacterGeneratorFactory;
import com.github.cage.token.RandomTokenGenerator;

import java.awt.Color;
import java.util.Random;

public class UCase extends Cage {
    private static final int HEIGHT = 35;
    private static final int WIDTH = 100;
    private static final int TOKEN_LEN_MIN = 5;
    private static final int TOKEN_LEN_DELTA = 2;
    private static final Random random = new Random();

    public UCase() {
        super(new Painter(WIDTH, HEIGHT, null, null, null, null),
                null, new ConstantColorGenerator(Color.DARK_GRAY), null,
                Cage.DEFAULT_COMPRESS_RATIO, new RandomTokenGenerator(random, new RandomCharacterGeneratorFactory(),
                        TOKEN_LEN_MIN, TOKEN_LEN_DELTA), random);
    }
}
