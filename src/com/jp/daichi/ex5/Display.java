package com.jp.daichi.ex5;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * 描画用のパネル
 */
public class Display extends JPanel {

    private final Game game;//描画するゲーム
    private double ratio = 0;
    public Display(Game game) {
        this.game = game;
        setDoubleBuffered(true);//ダブルバッファリング有効化
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    private long lastTime;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        long current = System.currentTimeMillis();
        System.out.println(current-lastTime);
        lastTime = current;

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,0, game.getWidth(), game.getHeight());

        if (g instanceof Graphics2D g2d) {
            //アンチエイリアシング有効化
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            AffineTransform transform = g2d.getTransform();
            transform.scale(ratio,ratio);
            g2d.setTransform(transform);
            game.drawEntity(g2d);


            BufferedImage bI = new BufferedImage(game.getWidth(),game.getHeight(),BufferedImage.TYPE_INT_ARGB);
            Graphics2D imageG = bI.createGraphics();
            imageG.setTransform(transform);
            imageG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            imageG.setComposite(AdditiveComposite.getInstance());
            game.drawParticle(imageG);
            g2d.setTransform(new AffineTransform());
            g2d.drawImage(bI,0,0,null);
            imageG.dispose();

        }
    }

    /**
     * 内部で使用することしか想定していないため、制約が厳しい
     * 背景(もととなる画像)はARGBのみ
     */
    public static class AdditiveComposite implements Composite {

        private static final AdditiveComposite instance = new AdditiveComposite();

        public static AdditiveComposite getInstance() {
            return instance;
        }

        private AdditiveComposite() {}

        public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
            return new AdditiveCompositeContext();
        }
    }

    private static class AdditiveCompositeContext implements CompositeContext {
        public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {

            final int[] pxSrc = new int[4];
            final int[] pxDst = new int[4];

            for (int x = 0; x < dstOut.getWidth(); x++) {
                for (int y = 0; y < dstOut.getHeight(); y++) {
                    src.getPixel(x, y, pxSrc);
                    dstIn.getPixel(x, y, pxDst);

                    int alpha = Math.min(pxSrc[3]+pxDst[3],255);

                    for (int i = 0; i < 3; i++) {
                        pxDst[i] = (int) Math.min(255,pxSrc[i]*alpha/255.0+pxDst[i]);
                    }
                    //System.out.println(Arrays.toString(pxDst));
                    pxDst[3] = alpha;
                    dstOut.setPixel(x, y, pxDst);
                }
            }


        }

        @Override public void dispose() { }
    }

}
