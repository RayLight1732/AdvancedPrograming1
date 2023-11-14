package com.jp.daichi.ex5;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
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
        //System.out.println(current-lastTime);
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

            double maxHPBarLength = game.getWidth()*0.7;
            double hpBarHeight = 20;
            g2d.setTransform(transform);
            LivingEntity player = game.getPlayer();
            if (player != null) {
                double barLength = player.getHP()/player.getMaxHP()*maxHPBarLength;
                RoundRectangle2D r = new RoundRectangle2D.Double((game.getWidth()-barLength)/2,(game.getHeight()-hpBarHeight*2),barLength,hpBarHeight,hpBarHeight,hpBarHeight);
                g2d.setColor(Color.CYAN);
                g2d.fill(r);
            }
            g2d.setColor(Color.WHITE);
            RoundRectangle2D r2 = new RoundRectangle2D.Double((game.getWidth()-maxHPBarLength)/2,(game.getHeight()-hpBarHeight*2),maxHPBarLength,hpBarHeight,hpBarHeight,hpBarHeight);
            g2d.draw(r2);

            if (game.getState() == GameState.GAME_OVER) {
                g2d.setColor(new Color(0,0,0,100));
                g2d.fillRect(0,0, game.getWidth(), game.getHeight());
                g2d.setColor(Color.WHITE);
                Font font = new Font(g2d.getFont().getFontName(),Font.PLAIN,200);
                g2d.setFont(font);
                drawStringCenter(g2d,"GameOver", game.getWidth()/2,game.getHeight()/2 );

            }
        }
    }

    private void drawStringCenter(Graphics2D g,String text,int x,int y) {
        FontMetrics fontMetrics = g.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(text);
        int stringHeight = fontMetrics.getAscent(); // Ascent を使用することで、ベースラインより上の高さを取得

        g.drawString("GameOver",x-stringWidth/2,y-stringHeight/2+fontMetrics.getAscent());
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
