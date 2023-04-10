package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Locale;

public class PrimaryController {

    @FXML
    private TextField inputTextField;
    @FXML
    private Text currentText;
    @FXML
    private VBox vbox;
    private String input = "";
    private Boolean shiftPressed = false;


    @FXML
    private void handleCalculateButtonAction(ActionEvent event) {

        try {
            Double result = eval(inputTextField.getText());
            currentText.setText(inputTextField.getText()+ " = ");
            inputTextField.setText(String.valueOf(result).substring(0, Math.min(String.valueOf(result).length(), 21)));
            System.out.println("DEBUG: handleCalculateButtonAction() Calculation result: " + result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            currentText.setText(input);
            input = "";
            inputTextField.setText("ERROR");
            System.out.println("DEBUG: handleCalculateButtonAction() Exception");
        }
        input = "";
        cursorToEnd();
    }

    public double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length())
                {
                    if ((char)ch != ',')
                        throw new RuntimeException("Unexpected: " + (char)ch);
                }
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else if (eat('^')) x = Functions.power(x, parseFactor()); // exponentiation
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                }
                else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Functions.squareRoot(x);
                    else if (func.equals("log")) x = Functions.log(x);
                    else if (func.equals("ln")) x = Functions.ln(x);
                    else if (func.equals("sinh")) x = Functions.sinh(x*Functions.DEGREES_TO_RADIANS);
                    else if (func.equals("arccos")) x = Functions.arccosine(x);
                    else if (func.equals("abs")) x = Functions.absVal(x);
                    else if (func.equals("stddev")) {
                        int index = input.indexOf("stddev(") + 7;
                        int endIndex = input.indexOf(")", index);
                        String sub = input.substring(index,endIndex);
                        String[] inputValues = sub.split(",");
                        double[] doubleValues = new double[inputValues.length];
                        for (int i = 0; i < inputValues.length; i++) {
                            doubleValues[i] = Double.parseDouble(inputValues[i]);
                        }
                        x = Functions.standardDeviation(doubleValues);
                        System.out.println("DEBUG: eval() parseFactor() stdev result: " + x);
                        return x;
                    }
                    else if (func.equals("mad")){
                        int index = input.indexOf("mad(") + 4;
                        int endIndex = input.indexOf(")", index);
                        String sub = input.substring(index,endIndex);
                        String[] inputValues = sub.split(",");
                        double[] doubleValues = new double[inputValues.length];
                        for (int i = 0; i < inputValues.length; i++){
                            doubleValues[i] = Double.parseDouble(inputValues[i]);
                        }
                        x = Functions.MAD(doubleValues);
                        System.out.println("DEBUG: eval() parseFactor() MAD result: " + x);
                        return x;
                    }
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
                return x;
            }
        }.parse();
    }

    private void buildOperation(String operation) {
        appendNumber(operation);
    }

    public void appendNumber(String number) {
        input += number;
        inputTextField.setText(input);
        cursorToEnd();
    }

    @FXML
    void handleExponentButtonAction(ActionEvent event) {
        buildOperation("^");
    }

    @FXML
    void handleStdDevButtonAction(ActionEvent event) {
        buildOperation("stddev(");
    }

    @FXML
    void handleMadButtonAction(ActionEvent event) {
        buildOperation("mad(");
    }

    @FXML
    void handleArccosButtonAction(ActionEvent event) {
        buildOperation("arccos(");
    }

    @FXML
    void handleSinhxButtonAction(ActionEvent event) {
        buildOperation("sinh(");
    }

    @FXML
    void handleMultButtonAction(ActionEvent event) {
        buildOperation("*");
    }

    @FXML
    void handleDivisionButtonAction(ActionEvent event) {
        buildOperation("/");
    }

    @FXML
    void handleAddButtonAction(ActionEvent event) {
        buildOperation("+");
    }

    @FXML
    void handleSubButtonAction(ActionEvent event) {
        buildOperation("-");
    }

    @FXML
    void handlePeriodButton(ActionEvent event) {
        appendNumber(".");
    }

    @FXML
    void handleNegationButton(ActionEvent event) {
        input = "-"+input;
    }
    @FXML
    void handleButton0Action(ActionEvent event) {
        appendNumber("0");
    }

    @FXML
    void handleButton1Action(ActionEvent event) {
        appendNumber("1");
    }

    @FXML
    void handleButton2Action(ActionEvent event) {
        appendNumber("2");
    }

    @FXML
    void handleButton3Action(ActionEvent event) {
        appendNumber("3");
    }

    @FXML
    void handleButton4Action(ActionEvent event) {
        appendNumber("4");
    }

    @FXML
    void handleButton5Action(ActionEvent event) {
        appendNumber("5");
    }

    @FXML
    void handleButton6Action(ActionEvent event) {
        appendNumber("6");
    }

    @FXML
    void handleButton7Action(ActionEvent event) {
        appendNumber("7");
    }

    @FXML
    void handleButton8Action(ActionEvent event) {
        appendNumber("8");
    }

    @FXML
    void handleButton9Action(ActionEvent event) {
        appendNumber("9");
    }

    @FXML
    void handleClearButtonAction(ActionEvent event) {
        reset();
    }

    @FXML
    void handleBackspaceButtonAction(ActionEvent event) {
        String temp = inputTextField.getText();
        if (temp.length() != 0){
            inputTextField.setText(temp.substring(0, temp.length() - 1));
            input = temp.substring(0, temp.length() - 1);
            System.out.println(input);
        }
    }

    void updateInputTextBox(){

    }

    void reset(){
        input = "";
        inputTextField.setText("");
    }

    public void cursorToEnd(){
        vbox.requestFocus();
        inputTextField.positionCaret(inputTextField.getLength());
    }

    public void checkKeyPresses(KeyCode code){
        System.out.println("check" + code.getName());
        switch (code) {
            case DIGIT0:
                if(shiftPressed)
                    appendNumber(")");
                else
                    handleButton0Action(null);
                break;
            case DIGIT1:
                handleButton1Action(null);
                break;
            case DIGIT2:
                handleButton2Action(null);
                break;
            case DIGIT3:
                handleButton3Action(null);
                break;
            case DIGIT4:
                handleButton4Action(null);
                break;
            case DIGIT5:
                handleButton5Action(null);
                break;
            case DIGIT6:
                if (shiftPressed)
                    handleExponentButtonAction(null);
                else
                    handleButton6Action(null);
                break;
            case DIGIT7:
                handleButton7Action(null);
                break;
            case DIGIT8:
                if(shiftPressed)
                    appendNumber("*");
                else
                    handleButton8Action(null);
                break;
            case DIGIT9:
                if (shiftPressed)
                    appendNumber("(");
                else
                    handleButton9Action(null);
                break;
            case S:
                if (!shiftPressed)
                    handleSinhxButtonAction(null);
                else
                    handleStdDevButtonAction(null);
                break;
            case L:
                appendNumber("log(");
                break;
            case A:
                handleArccosButtonAction(null);
                break;
            case PERIOD:
                handlePeriodButton(null);
                break;
            case EQUALS:
                if (shiftPressed == true)
                    handleAddButtonAction(null);
                break;
            case MINUS:
                handleSubButtonAction(null);
                break;
            case MULTIPLY:
                handleMultButtonAction(null);
                break;
            case SLASH:
                handleDivisionButtonAction(null);
                break;
            case SHIFT:
                setShiftPressed(true);
                break;
            case COMMA:
                appendNumber(",");
                break;
            case ESCAPE:
                reset();
                break;
            case ENTER:
                handleCalculateButtonAction(null);
                break;
            case BACK_SPACE:
                handleBackspaceButtonAction(null);
                break;
            default:
                break;
        };
    }

    void setShiftPressed(Boolean value){
        shiftPressed = value;
    }


}
