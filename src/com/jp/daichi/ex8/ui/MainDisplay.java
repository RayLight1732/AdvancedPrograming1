package com.jp.daichi.ex8.ui;

import com.jp.daichi.ex8.*;
import com.jp.daichi.ex8.Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainDisplay extends JPanel {
    public MainDisplay(JFrame frame) {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        ToolManager toolManager = new ToolManager();
        History history = new SimpleHistory();
        Canvas canvas = new SimpleCanvas(600,600,history);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("menu");
        menuBar.add(menu1);
        menu1.add(new JMenuItem("item"));
        JButton undo = new JButton("undo");
        menuBar.add(undo);
        undo.addActionListener(e ->{
            List<HistoryStaff> staffList = history.painted();
            if (staffList.size() == 1) {
                history.to(-1);
                repaint();
            } else if (staffList.size() >= 2) {
                history.to(staffList.get(staffList.size()-2).id());
                repaint();
            }
        });
        JButton redo = new JButton("redo");
        menuBar.add(redo);
        redo.addActionListener(e->{
            int index = history.getIndex(history.getCurrentHistoryID());
            List<HistoryStaff> all = history.getAll();
            if (index != -1 && index+1 < all.size()) {
                history.to(all.get(index+1).id());
                repaint();
            }
        });
        frame.setJMenuBar(menuBar);

        Ribbon ribbon = new Ribbon(toolManager);
        add(ribbon);
        layout.putConstraint(SpringLayout.NORTH,ribbon,0,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.SOUTH,ribbon,50,SpringLayout.NORTH,ribbon);
        layout.putConstraint(SpringLayout.WEST,ribbon,0,SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.EAST,ribbon,0,SpringLayout.EAST,this);
        JColorChooser colorChooser = new JColorChooser();
        ActionListener okListener = e -> canvas.setColor(colorChooser.getColor());//okが押されたらキャンバスの色を変更するリスナー
        JDialog dialog = JColorChooser.createDialog(this,"color picker",true,colorChooser,okListener,null);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialog.setVisible(true);
            }
        });
        JPanel viewPort = new JPanel();//ScrollPaneのViewPort用
        JScrollPane scrollPane = new JScrollPane(viewPort);//スクロール
        scrollPane.setBorder(null);//ボーダーなし
        CanvasPanel canvasPanel = new CanvasPanel(canvas);//キャンバス描画用
        viewPort.setLayout(new OriginalLayout(canvasPanel));//ビューポートのレイアウト設定
        viewPort.add(canvasPanel);//ビューポートに追加
        toolManager.addListener(canvasPanel::setTool);//ツールが変更されたときCanvasPanelに通知

        add(scrollPane);
        layout.putConstraint(SpringLayout.NORTH,scrollPane,0,SpringLayout.SOUTH,ribbon);
        layout.putConstraint(SpringLayout.SOUTH,scrollPane,0,SpringLayout.SOUTH,this);
        layout.putConstraint(SpringLayout.WEST,scrollPane,0,SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.EAST,scrollPane,0,SpringLayout.EAST,this);
    }


    private static class OriginalLayout extends FlowLayout {
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
            int canvasWidth = panel.getCanvas().getWidth();
            int canvasHeight = panel.getCanvas().getHeight();
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
}
