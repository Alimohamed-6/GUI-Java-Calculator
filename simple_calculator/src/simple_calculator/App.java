package simple_calculator;
import java.awt.*;
import javax.swing.*;

public class App {

    static class calculatorFrame extends JFrame{
        JPanel buttonpanel = new JPanel();
        JButton[] buttons = new JButton[20];
        JTextField display = new JTextField(20);
        JMenuBar menubar = new JMenuBar();
        JMenu m1 = new JMenu("Theme");
        JMenuItem light = new JMenuItem("Light");
        JMenuItem dark = new JMenuItem("Dark");
        JMenuItem green = new JMenuItem("Green");
        JMenu m2 = new JMenu("FontSize");
        JMenuItem small = new JMenuItem("Small");
        JMenuItem medium = new JMenuItem("Medium");
        JMenuItem large = new JMenuItem("Large");
        boolean isResult = false;
        
        calculatorFrame(){
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setTitle("Calculator");
            this.setResizable(false);
            this.setLayout(new BorderLayout());

            display.setBackground(Color.black);
            display.setForeground(Color.white);
            buttonpanel.setBackground(Color.black);
            
            m1.add(light);
            light.addActionListener(e -> { 
                display.setBackground(Color.WHITE);
                display.setForeground(Color.BLACK);
                buttonpanel.setBackground(Color.WHITE);
                for(int i = 0; i < buttons.length; i++) {
                    if(buttons[i] != null) {
                        buttons[i].setBackground(Color.WHITE);
                        buttons[i].setForeground(Color.BLACK);
                    }
                }
            });
            
            m1.add(dark);
            dark.addActionListener(e -> { 
                
                display.setBackground(Color.BLACK);
                display.setForeground(Color.WHITE);
                buttonpanel.setBackground(Color.BLACK);
                for(int i = 0; i < buttons.length; i++) {
                    if(buttons[i] != null) {
                        buttons[i].setBackground(Color.DARK_GRAY);
                        buttons[i].setForeground(Color.WHITE);
                    }
                }
            });
            
            m1.add(green);
            green.addActionListener(e -> {
                display.setBackground(new Color(0, 100, 0));
                display.setForeground(Color.WHITE);
                buttonpanel.setBackground(new Color(0, 100, 0));
                for(int i = 0; i < buttons.length; i++) {
                    if(buttons[i] != null) {
                        buttons[i].setBackground(new Color(0, 150, 0));
                        buttons[i].setForeground(Color.WHITE);
                    }
                }
            });

            m2.add(small);
            small.addActionListener(e -> {
                display.setFont(new Font("Arial", Font.BOLD, 12));
                for(int i = 0; i < buttons.length; i++) {
                    if(buttons[i] != null) {
                        buttons[i].setFont(new Font("Arial", Font.BOLD, 12));
                    }
                }
            });

            m2.add(medium);
            medium.addActionListener(e -> {
                display.setFont(new Font("Arial", Font.BOLD, 18));
                for(int i = 0; i < buttons.length; i++) {
                    if(buttons[i] != null) {
                        buttons[i].setFont(new Font("Arial", Font.BOLD, 18));
                    }
                }
            });
            m2.add(large);
            large.addActionListener(e -> {
                display.setFont(new Font("Arial", Font.BOLD, 24));
                for(int i = 0; i < buttons.length; i++) {
                    if(buttons[i] != null) {
                        buttons[i].setFont(new Font("Arial", Font.BOLD, 24));
                    }
                }
            });
            
            menubar.add(m1);menubar.add(m2);
            setJMenuBar(menubar);

            display.setEditable(false);
            display.setFont(new Font("Arial", Font.BOLD, 20));
            display.setForeground(Color.white);
            this.add(display, BorderLayout.NORTH);
            
            buttonpanel.setLayout(new GridLayout(5, 4, 5, 5));
            buttonpanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 30, 15));
            
            String[] labels = {"7", "8", "9", "C", 
                             "4", "5", "6", "-",
                             "1", "2", "3", "+",
                             "0", ".", "*", "/",
                             "(", ")", "Del", "="};
            
            for(int i = 0; i < buttons.length; i++) {
                if(!labels[i].isEmpty()){
                    buttons[i] = new JButton(labels[i]);
                    buttons[i].setFont(new Font("Arial", Font.BOLD, 18));
                    buttons[i].setPreferredSize(new Dimension(30,40));
                    
                    String buttonText = labels[i];
                    buttons[i].addActionListener(e -> {
                        if(buttonText.equals("C")) {
                            display.setText(""); 
                            isResult = false;
                        } 
                        else if(buttonText.equals("Del")) {
                            String currentText = display.getText();
                            if(currentText.length() > 0) {
                                display.setText(currentText.substring(0, currentText.length() - 1));
                            }
                            isResult = false;
                        } 
                        else if(buttonText.equals("=")) {
                            try {
                                String expression = display.getText();
                                double result = evaluateExpression(expression);
                                display.setText(String.valueOf(result));
                                isResult = true;

                            }
                             catch(Exception ex) {
                                display.setText("Error");
                                isResult = true;
                            }
                        }
                         else if(buttonText.equals("(")) {
                            if(isResult) {
                                display.setText(buttonText);
                                isResult = false;
                            } else {
                                display.setText(display.getText() + buttonText);
                            }
                        }
                         else if(buttonText.equals(")")) {
                            if(isResult) {
                                display.setText(buttonText);
                                isResult = false;
                            } else {
                                display.setText(display.getText() + buttonText);
                            }
                        }
                         else {
                            if(isResult) {
                                display.setText(buttonText);
                                isResult = false;
                            } else {
                                display.setText(display.getText() + buttonText);
                            }
                        }
                    });
                    
                    buttonpanel.add(buttons[i]);
                } else {
                    buttonpanel.add(new JPanel());
                }
            }
            this.add(buttonpanel, BorderLayout.CENTER);
            
            this.pack();
            dark.doClick();
            this.setVisible(true);
        }

        private double evaluateExpression(String expression) {
            while (expression.contains("(")) {
                int start = expression.lastIndexOf("(");
                int end = expression.indexOf(")", start);
                if (end == -1) break;
                
                String subExpr = expression.substring(start + 1, end);
                double subResult = evaluateSimpleExpression(subExpr);
                expression = expression.substring(0, start) + subResult + expression.substring(end + 1);
            }
            
            return evaluateSimpleExpression(expression);
        }
        
        private double evaluateSimpleExpression(String expression) {
            if (expression.startsWith("-")) {
                expression = "0" + expression;
            }
            
            if (expression.contains("+-")) {
                expression = expression.replace("+-", "-");
            }
            
            if (expression.contains("--")) {
                expression = expression.replace("--", "+");
            }
            
            String[] numbers = expression.split("[+\\-*/]");
            String[] operators = expression.split("[0-9.]+");
            
            operators = java.util.Arrays.stream(operators)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
            
            double[] values = new double[numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                values[i] = Double.parseDouble(numbers[i]);
            }
            
            int LastIndex=0;
            
            for (int i = 0; i < operators.length; i++) {
                if (operators[i].equals("*") || operators[i].equals("/")) {
                    
                    if (operators[i].equals("*")) {
                        values[LastIndex] = values[LastIndex] * values[i + 1];
                    } else {
                        if (values[i + 1] == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        values[LastIndex] = values[LastIndex] / values[i + 1];
                    }
                    
                    if(!Double.isNaN(values[i])) {
                        LastIndex=i;
                    }
                    
                    values[i + 1] = Double.NaN;
                    operators[i] = "processed";
                }
                else {
                    LastIndex=i+1;
                }
            }
            
            double result = 0;
            int firstValidIndex = -1;
            
            for (int i = 0; i < values.length; i++) {
                if (!Double.isNaN(values[i])) {
                    result = values[i];
                    firstValidIndex = i;
                    break;
                }
            }
            
            for (int i = firstValidIndex, opIndex = 0; i < values.length - 1 && opIndex < operators.length; i++) {
                if (Double.isNaN(values[i + 1])) {
                    continue;
                }
                
                while (opIndex < operators.length && operators[opIndex].equals("processed")) {
                    opIndex++;
                }
                
                if (opIndex < operators.length) {
                    if (operators[opIndex].equals("+")) {
                        result += values[i + 1];
                    } else if (operators[opIndex].equals("-")) {
                        result -= values[i + 1];
                    }
                    opIndex++;
                }
            }
            
            return result;
        }
    }
    
    public static void main(String[] args) throws Exception {
        calculatorFrame f = new calculatorFrame();
    }
}
