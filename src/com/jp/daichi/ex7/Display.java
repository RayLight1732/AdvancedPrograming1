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
import java.awt.event.*;
import java.util.*;
import java.util.List;

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
    private final CalculatorPanel calculatorPanel;
    private final HistoryPanel historyPanel;
    private final KeyListener keyListener = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            Operator operator = Operators.fromChar(e.getKeyChar());
            if (operator != null) {
                calculator.getOperator().input(operator);
            } else if (e.getKeyChar() == '=' || e.getKeyCode()==KeyEvent.VK_ENTER){
                calculator.getOperator().inputEqual();
            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                calculator.getOperator().delete();
            } else {
                calculator.getOperator().input(e.getKeyChar());
            }
            updatePanel();
        }
    };

    /**
     * 新しい表示用のクラスを作成する
     */
    public Display() {
        this.calculatorPanel = new CalculatorPanel();
        this.historyPanel = new HistoryPanel();
        historyPanel.setBorder(BorderFactory.createMatteBorder(0,2,0,0,Color.BLACK));
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        float ratio = 0.5f;
        SpringLayout.Constraints constraints0 = layout.getConstraints(this);
        double calculatorPanelWidth = calculatorPanel.getMinimumSize().getWidth();
        double historyPanelWidth = historyPanel.getMinimumSize().getWidth();
        int width = (int) Math.max(calculatorPanelWidth/ratio,historyPanelWidth/(1-ratio));
        //w:cW = 1:ratio
        //w = cW/ratio
        //w:hW = 1:(1-ratio)
        //w = hW/(1-ratio)
        constraints0.setWidth(Spring.constant(width));
        Spring panelW = layout.getConstraint(SpringLayout.WIDTH,this);
        SpringLayout.Constraints constraints1 = layout.getConstraints(calculatorPanel);
        constraints1.setWidth(Spring.scale(panelW,ratio));
        layout.putConstraint(SpringLayout.NORTH,calculatorPanel,0,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.WEST,calculatorPanel,0,SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.SOUTH,calculatorPanel,0,SpringLayout.SOUTH,this);

        layout.putConstraint(SpringLayout.NORTH,historyPanel,0,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.WEST,historyPanel,0,SpringLayout.EAST,calculatorPanel);
        layout.putConstraint(SpringLayout.EAST,historyPanel,0,SpringLayout.EAST,this);
        layout.putConstraint(SpringLayout.SOUTH,historyPanel,0,SpringLayout.SOUTH,this);

        add(calculatorPanel);
        add(historyPanel);


    }

    private void updatePanel() {
        calculatorPanel.update();
        historyPanel.update();
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

    private class CalculatorPanel extends JPanel {

        private final ResultDisplay resultDisplay;
        private final KeyPad keyPad;
        private CalculatorPanel() {
            this.resultDisplay = new ResultDisplay(()-> new String[]{calculator.getTopDisplay(),calculator.getBottomDisplay()});
            this.keyPad = new KeyPad();

            addKeyListener(keyListener);

            SpringLayout layout = new SpringLayout();
            setLayout(layout);
            float ratio = 0.3f;//resultDisplayが占める割合
            SpringLayout.Constraints constraints0 = layout.getConstraints(this);
            double resultDisplayHeight = resultDisplay.getMinimumSize().getHeight();
            double keypadHeight = keyPad.getMinimumSize().getHeight();
            int height = (int) Math.max(resultDisplayHeight/ratio,keypadHeight/(1-ratio));
            //h:dH = 1:ratio
            //h = dH/ratio
            //h:kH = 1:(1-ratio)
            //h = kh/(1-ratio)
            int width = (int) Math.max(resultDisplay.getPreferredSize().getWidth(),keyPad.getPreferredSize().getWidth());
            constraints0.setHeight(Spring.constant(height,height,Integer.MAX_VALUE));
            constraints0.setWidth(Spring.constant(width));
            Spring panelH = layout.getConstraint(SpringLayout.HEIGHT,this);
            SpringLayout.Constraints constraints1 = layout.getConstraints(resultDisplay);
            constraints1.setHeight(Spring.scale(panelH,ratio));
            constraints1.setY(Spring.constant(0));
            layout.putConstraint(SpringLayout.NORTH,resultDisplay,0,SpringLayout.NORTH,this);
            layout.putConstraint(SpringLayout.WEST,resultDisplay,0,SpringLayout.WEST,this);
            layout.putConstraint(SpringLayout.EAST,resultDisplay,0,SpringLayout.EAST,this);

            layout.putConstraint(SpringLayout.NORTH,keyPad,0,SpringLayout.SOUTH,resultDisplay);
            layout.putConstraint(SpringLayout.WEST,keyPad,0,SpringLayout.WEST,this);
            layout.putConstraint(SpringLayout.EAST,keyPad,0,SpringLayout.EAST,this);
            layout.putConstraint(SpringLayout.SOUTH,keyPad,0,SpringLayout.SOUTH,this);

            add(resultDisplay);
            add(keyPad);
        }

        public void update() {
            resultDisplay.update();
            keyPad.update();
        }
    }

    private static class ResultDisplay extends JPanel {
        private final DisplayTextGetter textGetter;

        private final JLabel topDisplay;
        private final JLabel bottomDisplay;

        private ResultDisplay(DisplayTextGetter textGetter) {
            this.textGetter = textGetter;
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

            SpringLayout.Constraints constraints = layout.getConstraints(this);
            float ratio = 0.3f;
            int height = (int) Math.max(topDisplay.getFontMetrics(font).getHeight()/ratio,bottomDisplay.getFontMetrics(font2).getHeight()/(1-ratio));
            //h:dH = 1:ratio
            //h = dH/ratio
            //h:kH = 1:(1-ratio)
            //h = kh/(1-ratio)
            constraints.setHeight(Spring.constant(height));
            Spring panelH = layout.getConstraint(SpringLayout.HEIGHT,this);
            SpringLayout.Constraints constraints1 = layout.getConstraints(topDisplay);
            constraints1.setHeight(Spring.scale(panelH,ratio));

            layout.putConstraint(SpringLayout.NORTH,topDisplay,0,SpringLayout.NORTH,this);
            layout.putConstraint(SpringLayout.WEST,topDisplay,0,SpringLayout.WEST,this);
            layout.putConstraint(SpringLayout.EAST,topDisplay,0,SpringLayout.EAST,this);

            layout.putConstraint(SpringLayout.NORTH,bottomDisplay,0,SpringLayout.SOUTH,topDisplay);
            layout.putConstraint(SpringLayout.WEST,bottomDisplay,0,SpringLayout.WEST,this);
            layout.putConstraint(SpringLayout.EAST,bottomDisplay,0,SpringLayout.EAST,this);
            layout.putConstraint(SpringLayout.SOUTH,bottomDisplay,0,SpringLayout.SOUTH,this);


            add(topDisplay);
            add(bottomDisplay);
            update();
        }

        public void update() {
            String[] text = textGetter.getText();
            topDisplay.setText(text[0]);
            bottomDisplay.setText(text[1]);
        }
    }

    private class HistoryPanel extends JScrollPane {
        private final JPanel panel;
        private final Color hoveredColor = new Color(192,192,192);

        private HistoryPanel() {
            this(new JPanel());
        }

        private HistoryPanel(JPanel panel) {
            super(panel);
            this.panel = panel;
            panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        }

        public void update() {
            panel.removeAll();
            for (int i = 0;i < calculator.getHistory().getFormulas().size();i++) {
                int f_i = i;
                ResultDisplay resultDisplay = new ResultDisplay(()-> calculator.getHistoryDisplay(f_i));
                Dimension dim = resultDisplay.getPreferredSize();
                dim.width = Integer.MAX_VALUE;
                resultDisplay.setSize(dim);
                resultDisplay.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.BLACK));
                final Color defaultBackground = resultDisplay.getBackground();
                resultDisplay.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        calculator.loadHistory(f_i);
                        Display.this.updatePanel();
                    }
                    /*
                    private boolean enter = false;
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (!enter) {
                            resultDisplay.setBackground(hoveredColor);
                            enter = true;
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (enter) {
                            resultDisplay.setBackground(defaultBackground);
                            enter = false;
                        }
                    }*/
                });
                panel.add(resultDisplay);
            }
        }
    }

    private class KeyPad extends JPanel {
        private KeyPad() {
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
                Display.this.updatePanel();
            });
            button.addKeyListener(keyListener);
            return button;
        }

        private void update() {
            for (Map.Entry<JButton,TextUpdateHandler> entry:textUpdateHandlers.entrySet()) {
                entry.getKey().setText(entry.getValue().update(calculator));
            }
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

    /**
     * DisplayPanel用のテキストを取得するインターフェース
     */
    private interface DisplayTextGetter {
        /**
         * DisplayPanelに表示するテキストを取得
         * @return result[0]:topDisplay result[1]:bottomDisplay
         */
        String[] getText();
    }
}
