package com.jp.daichi.ex8painttool;

import com.jp.daichi.ex8painttool.tools.Tool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SimpleCanvas implements Canvas {

    private final History history;
    private int width;
    private int height;
    private Color backgroundColor = Color.WHITE;
    private BufferedImage bufferedImage;
    public SimpleCanvas(int width,int height,History history) {
        this.width = width;
        this.height = height;
        this.bufferedImage = getBufferedImage(null,width,height);
        this.history = history;
    }

    private BufferedImage getBufferedImage(BufferedImage previous,int width,int height) {
        BufferedImage newImage = new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR);
        if (previous != null) {
            Graphics g = newImage.getGraphics();
            g.drawImage(previous, 0, 0, null);
            g.dispose();
        }
        return newImage;
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
        this.bufferedImage = getBufferedImage(getBufferedImage(),width,height);
        BufferedImage clone = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = clone.getGraphics();
        g.drawImage(this.bufferedImage,0,0,null);
        g.dispose();
        history.add("resize",clone);
    }

    @Override
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    @Override
    public BufferedImage getBufferedImage() {
        return null;
    }

    @Override
    public History getHistory() {
        return history;
    }

    @Override
    public void to(int id) {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    public void setTool(Tool tool) {

    }
}
