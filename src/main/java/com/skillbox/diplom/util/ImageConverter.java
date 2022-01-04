package com.skillbox.diplom.util;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageConverter {

    public static BufferedImage imageResize(byte[] bytes, int weight, int height) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(byteArrayInputStream);
        weight = Math.min(image.getWidth(), weight);
        height = Math.min(image.getHeight(), height);
        image = Scalr.resize(image, weight, height);
        return image;
    }

    public static byte[] bufferedImageToBytes(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, format, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
