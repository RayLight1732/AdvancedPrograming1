package com.jp.daichi.ex5.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static final Map<String, Image> loadedImages = new HashMap<>();
    public static Image getImage(String s) {
        return loadedImages.get(s);
    }

    public static void loadImage(String s,ImageConverter converter) {
        URL url = ClassLoader.getSystemResource("resources/"+s);
        try {
            BufferedImage bi = ImageIO.read(url);
            loadedImages.put(s,converter.convert(bi));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface ImageConverter {
        Image convert(BufferedImage bf);
    }
}
