package com.jp.daichi.ex8;

import com.jp.daichi.ex8.canvasobject.CanvasObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class SimpleCanvas implements Canvas{

    private static final Color transparent1 = new Color(219,219,219);
    private static final Color transparent2 = Color.WHITE;

    @Serial
    private static final long serialVersionUID = 8217484315767663802L;

    private final History history;
    private transient BufferedImage bufferedImage = null;
    private int width;
    private int height;
    private Color backgroundColor = Color.WHITE;
    private Color color = Color.BLACK;
    private int thickness = 10;
    private transient CanvasObject preview = null;
    private transient List<ColorChangeListener> colorChangeListeners = new ArrayList<>();

    public SimpleCanvas(int width, int height, History history) {
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
        setSize(new Dimension(width, getHeight()));
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        setSize(new Dimension(getWidth(), height));
    }

    @Override
    public Dimension getSize() {
        return new Dimension(width, height);
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
        Color oldColor = this.color;
        this.color = color;
        for (ColorChangeListener listener:colorChangeListeners) {
            listener.onChange(oldColor,color,false);
        }
    }

    @Override
    public void setBackgroundColor(Color color) {
        Color oldColor = this.backgroundColor;
        this.backgroundColor = color;
        for (ColorChangeListener listener:colorChangeListeners) {
            listener.onChange(oldColor,color,true);
        }
    }

    @Override
    public History getHistory() {
        return history;
    }

    @Override
    public void draw(Graphics2D g) {
        bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        doDrawing(g2d);
        //alpha値保管用にalpha=0の時だけ背景色描画
        int rgb = getBackgroundColor().getRGB();
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                if (bufferedImage.getRGB(x, y) >> 24 == 0) {//alpha=0
                    bufferedImage.setRGB(x, y, rgb);
                }
            }
        }
        g2d.dispose();
        Point2D point0 = g.getTransform().transform(new Point(0, 0), null);
        Point2D size = g.getTransform().transform(new Point(getWidth(), getHeight()), null);
        double newWidth = size.getX() - point0.getX();
        double newHeight = size.getY() - point0.getY();
        BufferedImage tmp = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D tmpG2d = tmp.createGraphics();
        tmpG2d.setTransform(AffineTransform.getScaleInstance(newWidth / getWidth(), newHeight / getHeight()));
        tmpG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        doDrawing(tmpG2d);
        //背景描画
        tmpG2d.setComposite(AlphaComposite.DstOver);
        if (getBackgroundColor().getAlpha()==0) {
            tmpG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
            int step = 10;
            for (int row = 0;row < getHeight()/step+1;row++) {
                for (int col = 0;col < getWidth()/step+1;col++) {
                    if ((row+col)%2==0) {
                        tmpG2d.setColor(transparent1);
                    } else {
                        tmpG2d.setColor(transparent2);
                    }
                    tmpG2d.fillRect(col*step,row*step,step,step);
                }
            }
        } else {
            tmpG2d.setColor(getBackgroundColor());
            tmpG2d.fillRect(0, 0, getWidth(), getHeight());
        }
        AffineTransform oldTransform = g.getTransform();
        g.setTransform(AffineTransform.getTranslateInstance(point0.getX(), point0.getY()));
        g.drawImage(tmp, 0, 0, null);
        g.setTransform(oldTransform);
        tmpG2d.dispose();

    }

    private void doDrawing(Graphics2D g2d) {
        for (HistoryStaff staff : history.painted()) {
            staff.canvasObject().draw(g2d);
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

    @Override
    public void addColorChangeListener(ColorChangeListener listener) {
        colorChangeListeners.add(listener);
    }

    @Override
    public void removeColorChangeListener(ColorChangeListener listener) {
        colorChangeListeners.remove(listener);
    }

    @Override
    public void onDeserialized() {
        colorChangeListeners = new ArrayList<>();
        history.onDeserialized();
    }
}
