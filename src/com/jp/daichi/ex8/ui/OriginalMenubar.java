package com.jp.daichi.ex8.ui;

import com.jp.daichi.ex8.Canvas;
import com.jp.daichi.ex8.History;
import com.jp.daichi.ex8.HistoryStaff;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class OriginalMenubar extends JMenuBar {

    private final MainDisplay mainDisplay;

    public OriginalMenubar(MainDisplay mainDisplay) {
        this.mainDisplay = mainDisplay;
        History history = mainDisplay.getCanvas().getHistory();
        JMenu menu1 = new JMenu("メニュー");
        add(menu1);

        menu1.add(createBackgroundColorChanger(mainDisplay,mainDisplay.getCanvas()));
        menu1.add(new JMenuItem("新規作成"));
        menu1.add(createOpenMenu(mainDisplay));
        JMenuItem override = new JMenuItem("上書き保存");
        override.addActionListener(e->{
            if (mainDisplay.getDefaultSaveFile() != null) {
                try {
                    mainDisplay.save();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    showFailedToSaveDialog(mainDisplay);
                }
            } else {
                saveWithNewName(mainDisplay);
            }
        });
        JMenuItem saveWithName = new JMenuItem("名前を付けて保存");
        saveWithName.addActionListener(e-> saveWithNewName(mainDisplay));

        menu1.add(override);
        menu1.add(saveWithName);
        JButton undo = new JButton("undo");
        add(undo);
        undo.addActionListener(e ->{
            List<HistoryStaff> staffList = history.painted();
            if (staffList.size() == 1) {
                history.to(-1);
                mainDisplay.repaint();
            } else if (staffList.size() >= 2) {
                history.to(staffList.get(staffList.size()-2).id());
                mainDisplay.repaint();
            }
        });
        JButton redo = new JButton("redo");
        add(redo);
        redo.addActionListener(e->{
            int index = history.getIndex(history.getCurrentHistoryID());
            List<HistoryStaff> all = history.getAll();
            if (all.size() > 0 && history.getCurrentHistoryID() == -1) {//明示的に履歴0のとき
                history.to(all.get(0).id());
                mainDisplay.repaint();
            } else if (index != -1 && index+1 < all.size()) {
                history.to(all.get(index+1).id());
                mainDisplay.repaint();
            }
        });
    }

    private JMenuItem createBackgroundColorChanger(Component component,Canvas canvas) {
        JMenuItem menu = new JMenu("背景色変更");
        JMenuItem choose = new JMenuItem("選択");
        choose.addActionListener(e->{
            Color color = JColorChooser.showDialog(component,"背景色変更",null);
            if (color != null) {
                canvas.setBackgroundColor(color);
                mainDisplay.repaint();
            }
        });

        JMenuItem foreground = new JMenuItem("描画色");
        foreground.addActionListener(e->{
            canvas.setBackgroundColor(canvas.getColor());
            mainDisplay.repaint();
        });
        JMenuItem transparent = new JMenuItem("透明");
        transparent.addActionListener(e->{
            canvas.setBackgroundColor(new Color(0,0,0,0));
            mainDisplay.repaint();
        });
        menu.add(choose);
        menu.add(foreground);
        menu.add(transparent);
        return menu;
    }

    private JMenuItem createOpenMenu(Component component) {
        JMenuItem menu = new JMenuItem("開く");
        menu.addActionListener(e->{
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("EditFile(*.edt)","edt");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(component);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    mainDisplay.getMainFrame().setCanvas(fileChooser.getSelectedFile());
                } catch (Exception exception) {
                    exception.printStackTrace();
                    showFailedToOpenDialog(component);
                }
            }

        });
        return menu;
    }

    private void saveWithNewName(Component component) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("タイトルなし.edt"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("EditFile(*.edt)","edt");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int result = fileChooser.showSaveDialog(component);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".edt")) {
                file = new File(fileChooser.getSelectedFile().getAbsolutePath()+".edt");
            }
            try {
                if (file.exists()) {
                    int option = JOptionPane.showConfirmDialog(component, "既に存在しているファイルです。上書きしますか","警告", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    if (option != JOptionPane.OK_OPTION) {
                        return;
                    }
                }
                mainDisplay.save(file);
                mainDisplay.setDefaultSaveFile(file);
                JOptionPane.showMessageDialog(component, "保存が完了しました");
            } catch (Exception e) {
                e.printStackTrace();
                showFailedToSaveDialog(component);
            }
        }
    }

    private void showFailedToSaveDialog(Component component) {
        JOptionPane.showMessageDialog(component, "保存に失敗しました");
    }

    private void showFailedToOpenDialog(Component component) {
        JOptionPane.showMessageDialog(component, "ファイルを開けませんでした");
    }

}
