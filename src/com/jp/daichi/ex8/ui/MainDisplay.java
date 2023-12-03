package com.jp.daichi.ex8.ui;

import com.jp.daichi.ex8.*;
import com.jp.daichi.ex8.Canvas;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MainDisplay extends JPanel {
    public MainDisplay(JFrame frame) {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        ToolManager toolManager = new ToolManager();
        History history = new SimpleHistory();
        Canvas canvas = new SimpleCanvas(600,600,history);
        canvas.setBackgroundColor(new Color(0,0,0,0));
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("メニュー");
        menuBar.add(menu1);

        menu1.add(createBackgroundColorChanger(canvas));
        menu1.add(new JMenuItem("新規作成"));
        menu1.add(createOpenMenu());
        menu1.add(new JMenuItem("上書き保存"));
        menu1.add(new JMenuItem("名前を付けて保存"));
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
            if (all.size() > 0 && history.getCurrentHistoryID() == -1) {//明示的に履歴0のとき
                history.to(all.get(0).id());
                repaint();
            } else if (index != -1 && index+1 < all.size()) {
                history.to(all.get(index+1).id());
                repaint();
            }
        });
        frame.setJMenuBar(menuBar);


        JColorChooser colorChooser = new JColorChooser();
        ActionListener okListener = e -> canvas.setColor(colorChooser.getColor());//okが押されたらキャンバスの色を変更するリスナー
        Ribbon ribbon = new Ribbon(toolManager,colorChooser,okListener);
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

    private JMenuItem createBackgroundColorChanger(Canvas canvas) {
        JMenuItem menu = new JMenu("背景色変更");
        JMenuItem choose = new JMenuItem("選択");
        choose.addActionListener(e->{
            Color color = JColorChooser.showDialog(MainDisplay.this,"背景色変更",null);
            if (color != null) {
                canvas.setBackgroundColor(color);
                repaint();
            }
        });

        JMenuItem foreground = new JMenuItem("描画色");
        foreground.addActionListener(e->{
            canvas.setBackgroundColor(canvas.getColor());
            repaint();
        });
        JMenuItem transparent = new JMenuItem("透明");
        transparent.addActionListener(e->{
            canvas.setBackgroundColor(new Color(0,0,0,0));
            repaint();
        });
        menu.add(choose);
        menu.add(foreground);
        menu.add(transparent);
        return menu;
    }

    private JMenuItem createOpenMenu() {
        JMenuItem menu = new JMenuItem("開く");
        menu.addActionListener(e->{
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("EditFile(*.edt)","edt");
            fileChooser.setAcceptAllFileFilterUsed(false);
            int result = fileChooser.showOpenDialog(MainDisplay.this);

        });
        return menu;
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
}
