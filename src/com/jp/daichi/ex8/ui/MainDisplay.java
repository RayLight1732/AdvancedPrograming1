package com.jp.daichi.ex8.ui;

import com.jp.daichi.ex8.*;
import com.jp.daichi.ex8.Canvas;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class MainDisplay extends JPanel {

    private final MainFrame mainFrame;

    private File saveFile;
    private final Canvas canvas;

    public MainDisplay(MainFrame frame,Canvas canvas) {
        this(frame,canvas,null);
    }
    public MainDisplay(MainFrame frame, Canvas canvas, File saveFile) {
        this.mainFrame = frame;
        this.saveFile = saveFile;
        this.canvas = canvas;
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        ToolManager toolManager = new ToolManager();
        canvas.setBackgroundColor(new Color(0,0,0,0));
        JMenuBar menuBar = new OriginalMenubar(this);
        frame.setJMenuBar(menuBar);

        JColorChooser colorChooser = new JColorChooser();
        ActionListener okListener = e -> canvas.setColor(colorChooser.getColor());//okが押されたらキャンバスの色を変更するリスナー
        Ribbon ribbon = new Ribbon(this,canvas,toolManager,colorChooser,okListener);
        canvas.addColorChangeListener(ribbon);
        add(ribbon);
        layout.putConstraint(SpringLayout.NORTH,ribbon,0,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.SOUTH,ribbon,50,SpringLayout.NORTH,ribbon);
        layout.putConstraint(SpringLayout.WEST,ribbon,0,SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.EAST,ribbon,0,SpringLayout.EAST,this);

        JPanel viewPort = new JPanel();//ScrollPaneのViewPort用
        JScrollPane scrollPane = new JScrollPane(viewPort);//スクロール
        scrollPane.setBorder(null);//ボーダーなし
        CanvasPanel canvasPanel = new CanvasPanel(canvas);//キャンバス描画用
        viewPort.setLayout(new OriginalLayout(canvasPanel));//ビューポートのレイアウト設定
        viewPort.add(canvasPanel);//ビューポートに追加
        toolManager.addListener(canvasPanel::setTool);//ツールが変更されたときCanvasPanelに通知

        add(scrollPane);
        layout.putConstraint(SpringLayout.NORTH,scrollPane,0,SpringLayout.SOUTH,ribbon);
        layout.putConstraint(SpringLayout.WEST,scrollPane,0,SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.EAST,scrollPane,0,SpringLayout.EAST,this);

        BottomPanel.ValueConverter converter = value -> value/100.0;
        BottomPanel bottomPanel = new BottomPanel(0,200,100,converter);
        add(bottomPanel);
        bottomPanel.getSlider().addChangeListener(e-> {
            int value = ((JSlider)e.getSource()).getValue();
            canvasPanel.setScale(converter.convert(value));
            viewPort.revalidate();
            scrollPane.revalidate();
        });


        layout.putConstraint(SpringLayout.SOUTH,scrollPane,0,SpringLayout.NORTH,bottomPanel);

        layout.putConstraint(SpringLayout.NORTH,bottomPanel,-50,SpringLayout.SOUTH,bottomPanel);
        layout.putConstraint(SpringLayout.SOUTH,bottomPanel,0,SpringLayout.SOUTH,this);
        layout.putConstraint(SpringLayout.WEST,bottomPanel,0,SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.EAST,bottomPanel,0,SpringLayout.EAST,this);
    }

    public Canvas getCanvas() {
        return canvas;
    }


    public void save() throws IOException {
        this.save(getDefaultSaveFile());
    }
    public void save(File file) throws IOException {
        ObjectOutputStream oos =new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(canvas);
        oos.close();
    }

    public File getDefaultSaveFile() {
        return saveFile;
    }

    public void setDefaultSaveFile(File file) {
        this.saveFile = file;
    }


    private static class OriginalLayout extends FlowLayout {
        @Serial
        private static final long serialVersionUID = 2550683786871576685L;
        private final CanvasPanel panel;
        private OriginalLayout(CanvasPanel panel) {
            this.panel = panel;
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            int canvasWidth = panel.getWidth();
            int canvasHeight = panel.getHeight();
            return new Dimension(canvasWidth,canvasHeight);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            int canvasWidth = panel.getWidth();
            int canvasHeight = panel.getHeight();
            return new Dimension(canvasWidth,canvasHeight);
        }

        @Override
        public void layoutContainer(Container target) {
            int x = 0;
            int y = 0;
            int canvasWidth = panel.getScaledCanvasWidth();
            int canvasHeight = panel.getScaledCanvasHeight();
            if (canvasWidth < target.getWidth()) {
                x = (target.getWidth()-canvasWidth)/2;//中央
            }
            if (canvasHeight < target.getHeight()) {
                y = (target.getHeight()-canvasHeight)/2;//中央
            }
            panel.setSize(canvasWidth,canvasHeight);
            panel.setLocation(x,y);
        }
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
}
