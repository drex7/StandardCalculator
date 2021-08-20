import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class StandardCalculator {
    private JPanel framePanel;
    private JTextField displayField;
    private JRadioButton onRadioButton;
    private JRadioButton offRadioButton;
    private JButton DELButton;
    private JButton ACButton;
    private JButton _5Button;
    private JButton _6Button;
    private JButton XButton;
    private JButton plusButton;
    private JButton _7Button;
    private JButton _4Button;
    private JButton _3Button;
    private JButton minusButton;
    private JButton divisionButton;
    private JButton _9Button;
    private JButton _2Button;
    private JButton _1Button;
    private JButton _0Button;
    private JButton periodButton;
    private JButton equalButton;
    private JLabel titleLabel;
    private JButton _8Button;
    private JPanel buttonsPanel;

    private String enteredText;


    public StandardCalculator() {

        // Get all buttons
        Component[] components = framePanel.getComponents();

        // listener display text in displayField as user enters them
        ActionListener displayTextListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = displayField.getText();
                String cmd = e.getActionCommand();          // Get the ActionCommand value of the button
                displayField.setText(text.concat(cmd));      // Display "result + cmd"
            }

        };

        JButton operationBtn;
        for (Component comp : components) {
            if ( comp.getClass().isInstance(_0Button) ) {
                operationBtn = (JButton)comp;
                String text = ((JButton)comp).getActionCommand();
                if ( !text.matches("AC|DEL|=") ) {
                    operationBtn.addActionListener(displayTextListener);
                }
            }
        }

        // Equal button calls method evaluateExp
        equalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Evaluate the expression
                double result = evalutateExp(displayField.getText());
                displayField.setText(String.valueOf(result));
            }
        });

        ActionListener switchListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean turnOn = e.getSource() == onRadioButton;    // turnOn is true if onRadioButton is clicked

                displayField.setText("");
                displayField.setEnabled(turnOn);
                for (Component comp : components) {
                    if ( !comp.getClass().isInstance(onRadioButton) ) {
                        comp.setEnabled(turnOn);
                    }
                }
            }
        };  // End switchListener

        // Register switchListener with on/offRadioButton
        onRadioButton.addActionListener(switchListener);
        offRadioButton.addActionListener(switchListener);

        DELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txt = displayField.getText();
                String subString;
                if (txt.length() > 0) {
                    subString = txt.substring(0, txt.length() - 1);
                    displayField.setText(subString);
                }
            }
        });

        ACButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayField.setText("");
            }
        });
    }  // End of constructor

    // Method to parse math exp into individual components
    private double evalutateExp (String exp) {
        // Using regex to get operators & operands in the exp
        String[] operands, operators;
        double result, leftOperand;
//        displayField.setHorizontalAlignment();
        operands = exp.split("[\\W&&[^.]]");
        operators = exp.split("\\d+\\.?\\d*");

        // Performs calculations
        leftOperand = Double.parseDouble(operands[0]);
        for (int i = 1; i < operands.length; i++) {
            switch (operators[i]) {
                case "+":
                    leftOperand += Double.parseDouble(operands[i]);
                    break;
                case "*":
                    leftOperand *= Double.parseDouble(operands[i]);
                    break;
                case "-":
                    leftOperand -= Double.parseDouble(operands[i]);
                    break;
                case "/":
                    leftOperand /= Double.parseDouble(operands[i]);
                    break;
            }
         }
        return leftOperand;
    }



    public static void main(String[] args) {
        JFrame window = new JFrame("Standard Calculator");
        window.setContentPane( new StandardCalculator().framePanel);
        ImageIcon img = new ImageIcon("src/icon.png");
        window.setIconImage(img.getImage());
        window.setLocationRelativeTo(null);
//        window.setBounds();0
        window.setSize(600,450);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }


}  // End of Class
