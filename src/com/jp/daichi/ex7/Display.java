package com.jp.daichi.ex7;

import com.jp.daichi.ex7.formula.Absolute;
import com.jp.daichi.ex7.formula.Formula;
import com.jp.daichi.ex7.formula.Reciprocal;
import com.jp.daichi.ex7.formula.Square;
import com.jp.daichi.ex7.operator.Operator;
import com.jp.daichi.ex7.operator.Operators;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Display extends JPanel {
    /*
   |  topDisplay  |
   |bottomDisplay |
   |              |
     MR  M+  M-  MS
     a/b MC CA  del
    1/x |x| mod /
     x^2 (   )  ^
     7   8   9  x
     4   5   6  -
     1   2   3  +
     +/- 0   .  =


     */
    private final Calculator calculator = new Calculator();
    private final KeyListener keyListener;
    private final ResultDisplay resultDisplay;

    /**
     * 新しい表示用のクラスを作成する
     */
    public Display() {
        setBackground(Color.GRAY);
        this.resultDisplay = new ResultDisplay();
        KeyPad keyPad = new KeyPad();
        keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Operator operator = Operators.fromChar(e.getKeyChar());
                if (operator != null) {
                    calculator.getOperator().input(operator);
                } else if (e.getKeyChar() == '='){
                    calculator.getOperator().inputEqual();
                } else {
                    calculator.getOperator().input(e.getKeyChar());
                }
                resultDisplay.update();
            }
        };


        addKeyListener(keyListener);
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1.weightx = 1;
        constraints1.weighty = 1;
        constraints1.fill = GridBagConstraints.BOTH;
        constraints1.gridx = 0;
        constraints1.gridy = 0;
        constraints1.gridheight = 3;
        gridBagLayout.setConstraints(resultDisplay,constraints1);

        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.fill = GridBagConstraints.BOTH;
        constraints2.weightx = 1;
        constraints2.weighty = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 3;
        constraints2.gridheight = 10;
        gridBagLayout.setConstraints(keyPad,constraints2);

        add(resultDisplay);
        add(keyPad);

    }


    private static class LeftEllipsisUI extends BasicLabelUI {

        private String reverse(String text) {
            return new StringBuilder(text).reverse().toString();
        }
        @Override
        protected String layoutCL(JLabel label, FontMetrics fontMetrics, String text, Icon icon, Rectangle viewR, Rectangle iconR, Rectangle textR) {
            return reverse(super.layoutCL(label, fontMetrics, reverse(text), icon, viewR, iconR, textR));//反転した状態で右側に...をつける　その後反転 ...は左に
        }
    }
    private static final Font font = new Font(Font.SANS_SERIF,Font.PLAIN,20);
    public static final Font font2 = new Font(Font.SANS_SERIF,Font.PLAIN,40);
    private class ResultDisplay extends JPanel {

        private final JLabel topDisplay;
        private final JLabel bottomDisplay;

        private ResultDisplay() {
            SpringLayout layout = new SpringLayout();
            setLayout(layout);
            Border border = BorderFactory.createEmptyBorder(0,0,0,20);
            this.topDisplay = new JLabel();
            topDisplay.setForeground(Color.GRAY);
            topDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
            topDisplay.setFont(font);
            topDisplay.setBorder(border);
            topDisplay.setUI(new LeftEllipsisUI());
            this.bottomDisplay = new JLabel();
            bottomDisplay.setForeground(Color.BLACK);
            bottomDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
            bottomDisplay.setFont(font2);
            bottomDisplay.setBorder(border);

            Spring panelH = layout.getConstraint(SpringLayout.HEIGHT,this);
            SpringLayout.Constraints constraints1 = layout.getConstraints(topDisplay);
            constraints1.setY(Spring.constant(0));
            //constraints1.setHeight(Spring.constant(topDisplay.getFontMetrics(topDisplay.getFont()).getHeight(),topDisplay.getFontMetrics(topDisplay.getFont()).getHeight(),Integer.MAX_VALUE));
            constraints1.setHeight(Spring.scale(panelH,0.2f));
            SpringLayout.Constraints constraints2 = layout.getConstraints(bottomDisplay);
            constraints2.setHeight(Spring.scale(panelH,0.8f));

            layout.putConstraint(SpringLayout.NORTH,topDisplay,0,SpringLayout.NORTH,this);
            layout.putConstraint(SpringLayout.WEST,topDisplay,0,SpringLayout.WEST,this);
            layout.putConstraint(SpringLayout.EAST,topDisplay,0,SpringLayout.EAST,this);
            //layout.putConstraint(SpringLayout.NORTH,bottomDisplay,0,SpringLayout.SOUTH,topDisplay);
            layout.putConstraint(SpringLayout.WEST,bottomDisplay,0,SpringLayout.WEST,this);
            layout.putConstraint(SpringLayout.EAST,bottomDisplay,0,SpringLayout.EAST,this);
            layout.putConstraint(SpringLayout.SOUTH,bottomDisplay,0,SpringLayout.SOUTH,this);

            add(topDisplay);
            add(bottomDisplay);
            validate();
            System.out.println("preferred size:"+getPreferredSize());
        }

        public void update() {
            topDisplay.setText(calculator.getTopDisplay());
            bottomDisplay.setText(calculator.getBottomDisplay());
        }
    }
    private class KeyPad extends JPanel {
        private KeyPad() {
            setBackground(Color.GRAY);
            GridLayout layout = new GridLayout();
            setLayout(layout);
            List<Component> components = new ArrayList<>();
            /*
     MR  M+  M-  MS
     a/b MC CA  del
    1/x |x| mod /
     x^2 (   )  ^
     7   8   9  x
     4   5   6  -
     1   2   3  +
     +/- 0   .  =

             */
            components.add(createButton("MR",e->calculator.loadMemory()));
            components.add(createButton("M+",e->calculator.addToMemory()));
            components.add(createButton("M-",e->calculator.subtractFromMemory()));
            components.add(createButton(c->c.doClearAll() ? "CA":"C", e->calculator.clear()));
            components.add(createButton(c->c.displayAsFraction() ? "a.b":"a/b",e->calculator.setDisplayAsFraction(!calculator.displayAsFraction())));
            components.add(createButton("MC",e->calculator.clearMemory()));
            components.add(createButton("MS",e->calculator.saveMemory()));
            components.add(createButton("del",e->calculator.getOperator().delete()));
            components.add(createButton("1/x",e->calculator.getOperator().inputSpecialFormula(Reciprocal::new)));
            components.add(createButton("|x|",e->calculator.getOperator().inputSpecialFormula(Absolute::new)));
            components.add(createOperatorButton(Operators.MOD));
            components.add(createButton("^",e->{}));//TODO
            components.add(createButton("x^2",e->calculator.getOperator().inputSpecialFormula(Square::new)));
            components.add(createInputButton('('));
            components.add(createButton(c ->{
                Formula formula = c.getOperator().getTopBracketHolder();
                if (formula != null) {
                    return formula.getCloseBracketSymbol();
                } else {
                    return "";
                }
            } ,e->calculator.getOperator().input(')')));
            components.add(createOperatorButton(Operators.DIVIDE));
            components.add(createInputButton('7'));
            components.add(createInputButton('8'));
            components.add(createInputButton('9'));
            components.add(createOperatorButton(Operators.MULTIPLY));
            components.add(createInputButton('4'));
            components.add(createInputButton('5'));
            components.add(createInputButton('6'));
            components.add(createOperatorButton(Operators.SUBTRACT));
            components.add(createInputButton('1'));
            components.add(createInputButton('2'));
            components.add(createInputButton('3'));
            components.add(createOperatorButton(Operators.ADD));
            components.add(createButton("+/-",e->calculator.getOperator().flipSignum()));
            components.add(createInputButton('0'));
            components.add(createInputButton('.'));
            components.add(createButton("=",e->calculator.getOperator().inputEqual()));
            int col = 4;
            layout.setColumns(col);
            layout.setRows(components.size()/col);
            for (Component component : components) {
                add(component);
            }
        }

        private final Map<JButton,TextUpdateHandler> textUpdateHandlers = new HashMap<>();
        private JButton createButton(String text, ActionListener listener) {
            JButton button = new JButton();
            button.setFocusPainted(false);
            button.setFont(font);
            button.setBackground(Color.WHITE);
            button.setText(text);
            button.addActionListener(e->{
                listener.actionPerformed(e);
                resultDisplay.update();
                for (Map.Entry<JButton,TextUpdateHandler> entry:textUpdateHandlers.entrySet()) {
                    entry.getKey().setText(entry.getValue().update(calculator));
                }
            });
            button.addKeyListener(keyListener);
            return button;
        }

        private JButton createButton(TextUpdateHandler handler,ActionListener listener) {
            JButton button = createButton(handler.update(calculator),listener);
            textUpdateHandlers.put(button,handler);
            return button;
        }

        private JButton createInputButton(char number) {
            return createButton(Character.toString(number),e->calculator.getOperator().input(number));
        }

        private JButton createOperatorButton(Operator operator) {
            return createButton(operator.toString(), e->calculator.getOperator().input(operator));
        }
    }

    private interface TextUpdateHandler {
        String update(Calculator calculator);
    }
}
