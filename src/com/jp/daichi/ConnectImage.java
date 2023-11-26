package com.jp.daichi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ConnectImage {
    public static void main(String[] args) throws IOException {
        File folder = new File("C:\\Users\\arusu\\Downloads\\image2");
        File[] files = folder.listFiles(((dir, name) -> name.endsWith("png")));
        Objects.requireNonNull(files);
        BufferedImage image0 = ImageIO.read(files[0]);
        int width = image0.getWidth();
        int height = image0.getHeight();
        BufferedImage image = new BufferedImage(width,files.length*height,BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = image.createGraphics();
        for (int i = 0;i < files.length;i++) {
            Image image2 = ImageIO.read(files[i]);
            g.drawImage(image2,0,height*i,null);
        }
        ImageIO.write(image,"png",new File("beam.png"));
    }
}
