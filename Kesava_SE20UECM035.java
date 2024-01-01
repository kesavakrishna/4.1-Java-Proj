import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.Math;

public class Kesava_SE20UECM035 {
    private JFrame frame;
    private JTextField textField;
    private String currentInput = "";
    private double result = 0.0;
    private String operator = "";
    private Stack<Double> stack = new Stack<>();
    private HashMap<String, Double> memory = new HashMap<>();
    private List<String> history = new ArrayList<>();
    private Map<String, String> userDefinedFunctions = new HashMap<>();
    // private JPanel buttonPanel; 
    public Kesava_SE20UECM035() {
        frame = new JFrame("Programmable Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 50));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);        

        JPanel buttonPanel = new JPanel(new GridLayout(11, 6));
                    
        String[] buttonLabels = {
            "7", "8", "9", "/", "sin", "cosec",
            "4", "5", "6", "*", "cos", "sec",
            "1", "2", "3", "-", "tan", "cot",
            "0", ".", "=", "+", "ln", "AC",
            "π", "e", "x^2", "x^y", "log10", "sqrt",
            "sinh","cosh","tanh", "cosech", "sech", "coth",
            "asin", "acos", "atan", "acosec", "asec", "acot",
            "d/dx", "∫[a,b]", "e^x", "|x|", "x!", "Stack", 
            "MR", "MS", "M+", "M-", "MC", "MS1",
            "MS2", "MS3", "History", "Define", "(", ")",
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        JButton lengthConversionButton = new JButton("Convert Length");
        lengthConversionButton.setFont(new Font("Arial", Font.PLAIN, 20));
        lengthConversionButton.addActionListener(new ConversionClickListener("Length"));
        buttonPanel.add(lengthConversionButton);

        JButton temperatureConversionButton = new JButton("Convert Temperature");
        temperatureConversionButton.setFont(new Font("Arial", Font.PLAIN, 20));
        temperatureConversionButton.addActionListener(new ConversionClickListener("Temperature"));
        buttonPanel.add(temperatureConversionButton);

        frame.add(textField, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
   
    private class ConversionClickListener implements ActionListener {
        private String conversionType;

        public ConversionClickListener(String conversionType) {
            this.conversionType = conversionType;
        }

        public void actionPerformed(ActionEvent e) {
            if (conversionType.equals("Length")) {
                performLengthConversion();
            } else if (conversionType.equals("Temperature")) {
                performTemperatureConversion();
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.matches("[0-9]|\\.")) {
                currentInput += command;
                textField.setText(currentInput);
            } else if (command.matches("[\\+\\-\\*/]|x\\^y")) {
                if (!currentInput.isEmpty()) {
                    result = Double.parseDouble(currentInput);
                    currentInput = "";
                }
                operator = command;
            } else if (command.equals("=")) {
                if (!currentInput.isEmpty()) {
                    double secondOperand = Double.parseDouble(currentInput);
                    switch (operator) {
                        case "+":
                            result += secondOperand;
                            break;
                        case "-":
                            result -= secondOperand;
                            break;
                        case "*":
                            result *= secondOperand;
                            break;
                        case "/":
                            if (secondOperand != 0) {
                                result /= secondOperand;
                            } else {
                                textField.setText("Error");
                                return;
                            }
                            break;
                        case "x^y":
                            result = Math.pow(result, secondOperand);
                            break;
                    }
                    history.add(result + "");
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("sqrt")) {
                if (!currentInput.isEmpty()) {
                    result = Math.sqrt(Double.parseDouble(currentInput));
                    history.add("sqrt(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("sin")) { //for trigonometric function(example: have to type as 90 and then sin)
                if (!currentInput.isEmpty()) {
                    result = Math.sin(Math.toRadians(Double.parseDouble(currentInput)));
                    history.add("sin(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("cos")) {
                if (!currentInput.isEmpty()) {
                    result = Math.cos(Math.toRadians(Double.parseDouble(currentInput)));
                    history.add("cos(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("tan")) {
                if (!currentInput.isEmpty()) {
                    result = Math.tan(Math.toRadians(Double.parseDouble(currentInput)));
                    history.add("tan(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("ln")) {
                if (!currentInput.isEmpty()) {
                    result = Math.log(Double.parseDouble(currentInput));
                    history.add("ln(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("log10")) {
                if (!currentInput.isEmpty()) {
                    result = Math.log10(Double.parseDouble(currentInput));
                    history.add("log10(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("(")) {
                stack.push(result);
                currentInput = "";
            } else if (command.equals(")")) {
                if (!stack.isEmpty()) {
                    double value = stack.pop();
                    result = value;
                    textField.setText(String.valueOf(result));
                }
            } else if (command.equals("AC")) {
                currentInput = "";
                result = 0.0;
                operator = "";
                textField.setText("");
            } else if (command.equals("π")) {
                result = Math.PI;
                textField.setText(String.valueOf(result));
            } else if (command.equals("e")) {
                result = Math.E;
                textField.setText(String.valueOf(result));
            } else if (command.equals("x^2")) {
                if (!currentInput.isEmpty()) {
                    result = Math.pow(Double.parseDouble(currentInput), 2);
                    history.add("(" + currentInput + ")^2 = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("d/dx")) {
                if (!currentInput.isEmpty()) {
                    double value = Double.parseDouble(currentInput);
                    double derivative = differentiate(value);
                    history.add("d/dx(" + currentInput + ") = " + derivative);
                    textField.setText(String.valueOf(derivative));
                }
            } else if (command.equals("∫[a,b]")) {
                // Input for definite integral (a to b)
                String input = JOptionPane.showInputDialog(frame, "Enter a value for 'a' (lower limit):", "Definite Integral", JOptionPane.PLAIN_MESSAGE);
                double a;
                try {
                    a = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    textField.setText("Invalid input");
                    return;
                }

                input = JOptionPane.showInputDialog(frame, "Enter a value for 'b' (upper limit):", "Definite Integral", JOptionPane.PLAIN_MESSAGE);
                double b;
                try {
                    b = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    textField.setText("Invalid input");
                    return;
                }

                double integral = integrate(a, b);
                history.add("∫[" + a + "," + b + "] = " + integral);
                textField.setText("∫[" + a + "," + b + "] = " + integral);
            } else if (command.equals("e^x")) {
                if (!currentInput.isEmpty()) {
                    result = Math.exp(Double.parseDouble(currentInput));
                    history.add("e^(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("|x|")) {
                if (!currentInput.isEmpty()) {
                    result = Math.abs(Double.parseDouble(currentInput));
                    history.add("|" + currentInput + "| = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("x!")) {
                if (!currentInput.isEmpty()) {
                    int value = Integer.parseInt(currentInput);
                    result = factorial(value);
                    history.add("(" + currentInput + ")! = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("sec")) {
                if (!currentInput.isEmpty()) {
                    result = 1.0 / Math.cos(Math.toRadians(Double.parseDouble(currentInput)));
                    history.add("sec(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("cosec")) {
                if (!currentInput.isEmpty()) {
                    result = 1.0 / Math.sin(Math.toRadians(Double.parseDouble(currentInput)));
                    history.add("cosec(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("cot")) {
                if (!currentInput.isEmpty()) {
                    result = 1.0 / Math.tan(Math.toRadians(Double.parseDouble(currentInput)));
                    history.add("cot(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("sinh")) {
                if (!currentInput.isEmpty()) {
                    result = Math.sinh(Double.parseDouble(currentInput));
                    history.add("sinh(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("cosh")) {
                if (!currentInput.isEmpty()) {
                    result = Math.cosh(Double.parseDouble(currentInput));
                    history.add("cosh(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("tanh")) {
                if (!currentInput.isEmpty()) {
                    result = Math.tanh(Double.parseDouble(currentInput));
                    history.add("tanh(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            }
            else if (command.equals("cosech")) {
                if (!currentInput.isEmpty()) {
                    result = 1.0 / Math.sinh(Double.parseDouble(currentInput));
                    history.add("cosech(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("sech")) {
                if (!currentInput.isEmpty()) {
                    result = 1.0 / Math.cosh(Double.parseDouble(currentInput));
                    history.add("sech(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("coth")) {
                if (!currentInput.isEmpty()) {
                    result = 1.0 / Math.tanh(Double.parseDouble(currentInput));
                    history.add("coth(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("asin")) {
                if (!currentInput.isEmpty()) {
                    result = Math.toDegrees(Math.asin(Double.parseDouble(currentInput)));
                    history.add("asin(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("acos")) {
                if (!currentInput.isEmpty()) {
                    result = Math.toDegrees(Math.acos(Double.parseDouble(currentInput)));
                    history.add("acos(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("atan")) {
                if (!currentInput.isEmpty()) {
                    result = Math.toDegrees(Math.atan(Double.parseDouble(currentInput)));
                    history.add("atan(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("acosec")) {
                if (!currentInput.isEmpty()) {
                    result = Math.toDegrees(Math.asin(1.0 / Double.parseDouble(currentInput)));
                    history.add("acosec(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("asec")) {
                if (!currentInput.isEmpty()) {
                    result = Math.toDegrees(Math.acos(1.0 / Double.parseDouble(currentInput)));
                    history.add("asec(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            } else if (command.equals("acot")) {
                if (!currentInput.isEmpty()) {
                    result = Math.toDegrees(Math.atan(1.0 / Double.parseDouble(currentInput)));
                    history.add("acot(" + currentInput + ") = " + result);
                    textField.setText(String.valueOf(result));
                    currentInput = "";
                }
            }    
            else if (command.equals("MR")) {
                if (memory.containsKey("Memory")) {
                    currentInput = memory.get("Memory").toString();
                    textField.setText(currentInput);
                } else {
                    textField.setText("Memory is empty");
                }
            } else if (command.equals("MS")) {
                memory.put("Memory", Double.parseDouble(currentInput));
                currentInput = "";
            } else if (command.equals("M+")) {
                if (!currentInput.isEmpty()) {
                    double memoryValue = memory.getOrDefault("Memory", 0.0);
                    memoryValue += Double.parseDouble(currentInput);
                    memory.put("Memory", memoryValue);
                    currentInput = "";
                }
            } else if (command.equals("M-")) {
                if (!currentInput.isEmpty()) {
                    double memoryValue = memory.getOrDefault("Memory", 0.0);
                    memoryValue -= Double.parseDouble(currentInput);
                    memory.put("Memory", memoryValue);
                    currentInput = "";
                }
            } else if (command.equals("MC")) {
                memory.remove("Memory");
                currentInput = "";
            } else if (command.startsWith("MS")) {
                // Example: MS1, MS2, MS3
                String slot = command.substring(2);
                memory.put("Memory" + slot, Double.parseDouble(currentInput));
                currentInput = "";
            } else if (command.equals("History")) {
                displayHistory();
            }
            else if (command.equals("Define")) {
                defineUserFunction();
            }
        }
    }

    private double differentiate(double x) {
        // Basic differentiation (example: d/dx(x^2) = 2x)
        return 2 * x;
    }

    private double integrate(double a, double b) {
        // Basic integration (example: ∫[a, b] x^2 dx = (1/3) * (b^3 - a^3))
        return (1.0 / 3.0) * (Math.pow(b, 3) - Math.pow(a, 3));
    }

    private double factorial(int n) {
        if (n == 0) {
            return 1;
        }
        double result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private void performLengthConversion() {
        String input = JOptionPane.showInputDialog(frame, "Enter length value:");
        double value;
        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException ex) {
            textField.setText("Invalid input");
            return;
        }

        String[] options = {"Inches to Centimeters", "Centimeters to Inches", "Meters to Feet", "Feet to Meters"};
        int choice = JOptionPane.showOptionDialog(
            frame,
            "Select conversion:",
            "Length Conversion",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );

        double result = 0.0;
        switch (choice) {
            case 0:
                result = value * 2.54; // Inches to Centimeters
                break;
            case 1:
                result = value / 2.54; // Centimeters to Inches
                break;
            case 2:
                result = value * 3.28084; // Meters to Feet
                break;
            case 3:
                result = value / 3.28084; // Feet to Meters
                break;
        }

        textField.setText(value + " to " + options[choice] + ": " + result);
    }

    private void performTemperatureConversion() {
        String input = JOptionPane.showInputDialog(frame, "Enter temperature value:");
        double value;
        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException ex) {
            textField.setText("Invalid input");
            return;
        }

        String[] options = {"Celsius to Fahrenheit", "Fahrenheit to Celsius"};
        int choice = JOptionPane.showOptionDialog(
            frame,
            "Select conversion:",
            "Temperature Conversion",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );

        double result = 0.0;
        switch (choice) {
            case 0:
                result = (value * 9/5) + 32; // Celsius to Fahrenheit
                break;
            case 1:
                result = (value - 32) * 5/9; // Fahrenheit to Celsius
                break;
        }

        textField.setText(value + " to " + options[choice] + ": " + result);
    }

    private void displayHistory() {
        StringBuilder historyText = new StringBuilder("Calculation History:\n");
        for (String entry : history) {
            historyText.append(entry).append("\n");
        }
        JOptionPane.showMessageDialog(frame, historyText.toString(), "Calculation History", JOptionPane.INFORMATION_MESSAGE);
    }
    private void defineUserFunction() {
        String functionName = JOptionPane.showInputDialog(frame, "Enter function name:");
        if (functionName != null && !functionName.isEmpty()) {
            String functionExpression = JOptionPane.showInputDialog(frame, "Enter function expression (e.g., x^2 + 2*x - 3):");
            if (functionExpression != null && !functionExpression.isEmpty()) {
                userDefinedFunctions.put(functionName, functionExpression);
                JOptionPane.showMessageDialog(frame, "Function '" + functionName + "' defined successfully.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Kesava_SE20UECM035());
    }
}
