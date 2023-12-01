package com.jp.daichi.ex8painttool;

import com.jp.daichi.ex5shooting.Display;
import com.jp.daichi.ex8painttool.canvasobject.CanvasObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class SimpleCanvas implements Canvas {

    private final History history;
    private BufferedImage bufferedImage = null;
    private int width;
    private int height;
    private Color backgroundColor = Color.WHITE;
    private Color color = Color.BLACK;
    private int thickness = 1;
    private CanvasObject preview = null;
    public SimpleCanvas(int width,int height,History history) {
        this.width = width;
        this.height = height;
        this.history = history;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        setSize(new Dimension(width,getHeight()));
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        setSize(new Dimension(getWidth(),height));
    }

    @Override
    public Dimension getSize() {
        return new Dimension(width,height);
    }

    @Override
    public void setSize(Dimension dimension) {
        this.width = dimension.width;
        this.height = dimension.height;
    }

    @Override
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    @Override
    public History getHistory() {
        return history;
    }

    @Override
    public void draw(Graphics2D g) {

        bufferedImage = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        doDrawing(g2d);
        //alpha値保管用にalpha=0の時だけ背景色描画
        int rgb = getBackgroundColor().getRGB();
        for (int x = 0;x < bufferedImage.getWidth();x++) {
            for (int y = 0;y < bufferedImage.getHeight();y++) {
                if (bufferedImage.getRGB(x,y)>>24==0) {//alpha=0
                    bufferedImage.setRGB(x,y,rgb);
                }
            }
        }
        g2d.dispose();


        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Shape oldClip = g.getClip();
        g.clipRect(0,0,getWidth(),getHeight());
        g.setColor(getBackgroundColor());
        g.fillRect(0,0,getWidth(),getHeight());
        doDrawing(g);
        g.setClip(oldClip);

    }

    private void doDrawing(Graphics2D g2d) {
        for (CanvasObject c: history.getObjects()) {
            c.draw(g2d);
        }
        if (preview != null) {
            preview.draw(g2d);
        }
    }

    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public int getThickNess() {
        return thickness;
    }

    @Override
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    @Override
    public void setPreview(CanvasObject canvasObject) {
        this.preview = canvasObject;
    }

}
