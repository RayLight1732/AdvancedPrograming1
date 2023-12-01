package com.jp.daichi.ex5;

import com.jp.daichi.ex5.render.Render;
import com.jp.daichi.ex5.utils.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

/**
 * 描画用のパネル
 */
public class Display extends JPanel {

    public boolean draw = true;

    private final Game game;//描画するゲーム
    private double ratio = 0;
    public Display(Game game) {
        this.game = game;
        setDoubleBuffered(true);//ダブルバッファリング有効化
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    private long lastFrameMeasureTime;
    private int flame = 0;
    private int flame_tmp = 0;

    private Image image;
    private Image getBackGroundImage() {
        if (image == null) {
            image = ResourceManager.getImage("background.png");
        }
        return image;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        long current = System.currentTimeMillis();
        if (current-lastFrameMeasureTime > 1000) {
            flame = flame_tmp;
            flame_tmp = 0;
            lastFrameMeasureTime = current;
        }
        flame_tmp++;
        //g.setColor(Color.DARK_GRAY);
        //g.fillRect(0,0, game.getWidth(), game.getHeight());

        if (g instanceof Graphics2D g2d) {
            //アンチエイリアシング有効化
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            AffineTransform transform = g2d.getTransform();
            transform.scale(ratio,ratio);
            g2d.setTransform(transform);
            g.drawImage(getBackGroundImage(),0,0,null);

            List<GameEntity> inParticleTick = drawEntity(g2d);

            /*
            BufferedImage bI = new BufferedImage(game.getWidth(),game.getHeight(),BufferedImage.TYPE_INT_ARGB);
            Graphics2D imageG = bI.createGraphics();
            imageG.setTransform(transform);
            imageG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            imageG.setComposite(AdditiveComposite.getInstance());
            drawParticle(imageG);
            g2d.setTransform(new AffineTransform());
            g2d.drawImage(bI,0,0,null);
            imageG.dispose();
            g2d.setTransform(transform);
             */
            Composite old = g2d.getComposite();
            g2d.setComposite(AdditiveComposite.getInstance());
            drawParticle(g2d,inParticleTick);
            g2d.setComposite(old);


            drawHPBar(g2d);

            if (game.getState() == GameState.GAME_OVER) {
                g2d.setColor(new Color(0,0,0,100));
                g2d.fillRect(0,0, game.getWidth(), game.getHeight());
                g2d.setColor(Color.WHITE);
                Font font = new Font(g2d.getFont().getFontName(),Font.PLAIN,200);
                g2d.setFont(font);
                drawAlignedString(g2d,CENTER,"GameOver", game.getWidth()/2,game.getHeight()/2 );
            }

            g2d.setColor(Color.WHITE);
            Font font = new Font(g2d.getFont().getFontName(),Font.PLAIN,20);
            g2d.setFont(font);
            drawAlignedString(g2d,UPPER_LEFT,"fps:"+flame,0,0);

            font = new Font(g2d.getFont().getFontName(),Font.PLAIN,30);
            g2d.setFont(font);
            drawAlignedString(g2d,UPPER_RIGHT,"Score:"+game.getScore(), game.getWidth(), 0);
        }

    }

    private List<GameEntity> drawEntity(Graphics2D g) {
        List<GameEntity> result = new ArrayList<>();
        for (GameEntity entity:game.getEntities()) {
            Render<GameObject> render = RenderManager.getRender(entity.getClass());
            if(render.renderInParticlePhase(entity)) {
                result.add(entity);
            } else {
                render.render(g,entity,calculateStep(entity));
            }
        }
        return result;
    }

    private void drawParticle(Graphics2D g,List<GameEntity> entities) {
        game.getParticles().forEach(particle-> RenderManager.getRender(particle.getClass()).render(g, particle,calculateStep(particle)));
        entities.forEach(e->RenderManager.getRender(e.getClass()).render(g,e,calculateStep(e)));
    }
    private double calculateStep(GameObject object) {
        return Math.min( (System.currentTimeMillis()-object.lastTickMs())/object.lastTickDelta()/1000,1);
    }

    private void drawHPBar(Graphics2D g) {
        double maxHPBarLength = game.getWidth()*0.7;
        double hpBarHeight = 20;
        LivingEntity player = game.getPlayer();
        if (player != null) {
            double barLength = player.getHP()/player.getMaxHP()*maxHPBarLength;
            RoundRectangle2D r = new RoundRectangle2D.Double((game.getWidth()-barLength)/2,(game.getHeight()-hpBarHeight*2),barLength,hpBarHeight,hpBarHeight,hpBarHeight);
            g.setColor(Color.CYAN);
            g.fill(r);
        }
        g.setColor(Color.WHITE);
        RoundRectangle2D r2 = new RoundRectangle2D.Double((game.getWidth()-maxHPBarLength)/2,(game.getHeight()-hpBarHeight*2),maxHPBarLength,hpBarHeight,hpBarHeight,hpBarHeight);
        g.draw(r2);
    }

    private static final int CENTER = 0;
    private static final int UPPER_LEFT = 1;
    private static final int UPPER_RIGHT = 2;
    private static final int LOWER_LEFT = 3;
    private static final int LOWER_RIGHT = 4;

    private void drawAlignedString(Graphics2D g,int alignID,String text,int x,int y) {
        FontMetrics fontMetrics = g.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(text);
        int stringHeight = fontMetrics.getAscent(); // Ascent を使用することで、ベースラインより上の高さを取得

        switch (alignID) {
            case CENTER -> {
                x = x-stringWidth/2;
                y = y+stringHeight/2;
            }
            case UPPER_LEFT -> {
                y = y+stringHeight;
            }
            case UPPER_RIGHT -> {
                x = x-stringWidth;
                y = y+stringHeight;
            }
            case LOWER_LEFT -> {
                y = y-fontMetrics.getDescent();
            }
            case LOWER_RIGHT -> {
                x = x-stringWidth;
                y = y-fontMetrics.getDescent();
            }
        }
        g.drawString(text,x,y);
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
