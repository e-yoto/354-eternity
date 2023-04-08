package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PrimaryController {

    @FXML
    private TextField inputTextField;
    @FXML
    private Text currentText;
    @FXML
    private VBox vbox;

    // x + y, 'x' is firstValue, 'y' is secondValue, '+' is operation
    private String firstValue = "";
    private String secondValue = "";
    private String operation;

    private Boolean shiftPressed = false;


    @FXML
    private void handleCalculateButtonAction(ActionEvent event) {

        try {
            switch (operation) {
                case "^":
                    double result = Functions.power(Double.parseDouble(firstValue), Double.parseDouble(secondValue));
                    currentText.setText(firstValue + " ^ " + secondValue + " = ");
                    inputTextField.setText(String.valueOf(result));
                    break;
                case "+":
                    calculateAddition();
                    break;
                case "-":
                    calculateSubtraction();
                    break;
                case "*":
                    calculateMultiplication();
                    break;
                case "/":
                    calculateDivision();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            firstValue = "";
            secondValue = "";
            operation = "";
            currentText.setText("");
            inputTextField.setText("ERROR");
            System.out.println("DEBUG: handleCalculateButtonAction Exception");
        }
        cursorToEnd();
    }

    private void buildOperation(String operation) {
        this.operation = operation;
        firstValue = secondValue;
        secondValue = "";
        currentText.setText(firstValue + " " + operation);
    }

    public void appendNumber(String number) {
        secondValue += number;
        inputTextField.setText(secondValue);
    }

    @FXML
    void handleExponentButtonAction(ActionEvent event) {
        buildOperation("^");
        cursorToEnd();
    }

    @FXML
    void handleMultButtonAction(ActionEvent event) {
        buildOperation("*");
        cursorToEnd();
    }

    @FXML
    void handleDivisionButtonAction(ActionEvent event) {
        buildOperation("/");
        cursorToEnd();
    }

    @FXML
    void handleAddButtonAction(ActionEvent event) {
        buildOperation("+");
        cursorToEnd();
    }

    @FXML
    void handleSubButtonAction(ActionEvent event) {
        buildOperation("-");
        cursorToEnd();
    }

    private void calculateAddition() {
        double result = Double.parseDouble(firstValue) + Double.parseDouble(secondValue);
        currentText.setText(firstValue + " + " + secondValue + " = " + result);
        inputTextField.setText(String.valueOf(result));
    }

    private void calculateSubtraction() {
        double result = Double.parseDouble(firstValue) - Double.parseDouble(secondValue);
        currentText.setText(firstValue + " - " + secondValue + " = " + result);
        inputTextField.setText(String.valueOf(result));
    }

    private void calculateMultiplication() {
        double result = Double.parseDouble(firstValue) * Double.parseDouble(secondValue);
        currentText.setText(firstValue + " * " + secondValue + " = " + result);
        inputTextField.setText(String.valueOf(result));
    }

    private void calculateDivision() {
        double result = Double.parseDouble(firstValue) / Double.parseDouble(secondValue);
        currentText.setText(firstValue + " / " + secondValue + " = " + result);
        inputTextField.setText(String.valueOf(result));
    }

    @FXML
    void handleButton0Action(ActionEvent event) {
        appendNumber("0");
        cursorToEnd();
    }

    @FXML
    void handleButton1Action(ActionEvent event) {
        appendNumber("1");
        cursorToEnd();
    }

    @FXML
    void handleButton2Action(ActionEvent event) {
        appendNumber("2");
        cursorToEnd();
    }

    @FXML
    void handleButton3Action(ActionEvent event) {
        appendNumber("3");
        cursorToEnd();
    }

    @FXML
    void handleButton4Action(ActionEvent event) {
        appendNumber("4");
        cursorToEnd();
    }

    @FXML
    void handleButton5Action(ActionEvent event) {
        appendNumber("5");
        cursorToEnd();
    }

    @FXML
    void handleButton6Action(ActionEvent event) {
        appendNumber("6");
        vbox.requestFocus();
        cursorToEnd();
    }

    @FXML
    void handleButton7Action(ActionEvent event) {
        appendNumber("7");
        cursorToEnd();
    }

    @FXML
    void handleButton8Action(ActionEvent event) {
        appendNumber("8");
        cursorToEnd();
    }

    @FXML
    void handleButton9Action(ActionEvent event) {
        appendNumber("9");
        cursorToEnd();
    }

    void reset(){
        firstValue = "";
        secondValue = "";
        operation = "";
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
                handleButton8Action(null);
                break;
            case DIGIT9:
                handleButton9Action(null);
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
            case ESCAPE:
                reset();
                break;
            case ENTER:
                handleCalculateButtonAction(null);
                break;
            default:
                break;
        };
    }

    void setShiftPressed(Boolean value){
        shiftPressed = value;
    }


}
