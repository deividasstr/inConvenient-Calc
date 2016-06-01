package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<BigDecimal> mOperandContainer;
    private List<Character> mOperatorContainer;
    private String mBigText;
    private String mSmallText;
    private boolean mChecker;

    @FXML
    private Label bigLabel;
    @FXML
    private Label smallLabel;

    public Controller() {
        mBigText = "0";
        mSmallText = "";
        mOperandContainer = new ArrayList<>();
        mOperatorContainer = new ArrayList<>();
    }

    public void showTextBig() {
        bigLabel.setText(mBigText);
    }

    public void showTextSmall() {
        if (mSmallText.length() > 35) {
            mSmallText = mSmallText.substring(5);
        }
        smallLabel.setText(mSmallText);
    }

    public void handleKeys(KeyEvent key) {
        String code = String.valueOf(key.getCode());
        char chr = 'e';
        switch (code) {
            case "ADD":
                chr = '+';
                break;
            case "SUBTRACT":
                chr = '-';
                break;
            case "MULTIPLY":
                chr = '*';
                break;
            case "DIVIDE":
                chr = '/';
                break;
            case "EQUALS":
                chr = '=';
                break;
            case "ENTER":
                chr = '=';
                break;
            case "DECIMAL":
                chr = '.';
                break;
            case "PERIOD":
                chr = '.';
                break;
            case "C":
                chr = 'c';
                break;
            default:
                if (String.valueOf(code.charAt(code.length() - 1)).matches("\\d")) {
                    chr = code.charAt(code.length() - 1);
                }
        }
        handleSymbols(chr);
        key.consume();
    }

    public void handleBtns(ActionEvent buttonPressed) {
        char chr = buttonPressed.getSource().toString().toLowerCase().charAt(buttonPressed.getSource().toString().length() - 2);
        handleSymbols(chr);
        buttonPressed.consume();
    }

    public void handleSymbols(char chr) {

        if (Character.toString(chr).matches("[-\\+\\*/=]")) {
            calculate(chr);
            return;
        }

        if (chr == 'c') {
            mBigText = "0";
            showTextBig();
            mOperatorContainer.clear();
            mOperandContainer.clear();
            mSmallText = "";
            showTextSmall();
            mChecker = false;
            return;
        }
        if (Character.isDigit(chr) && mChecker) {
            mBigText = "0";
            mBigText = String.valueOf(new BigDecimal(mBigText += chr));
            showTextBig();
            mChecker = false;
            return;
        }

        if ((Character.isDigit(chr) && (mBigText.length() <= 9))) {
            mBigText = String.valueOf(new BigDecimal(mBigText += chr));
            showTextBig();
            return;
        }

        if (chr == '.' && !mBigText.contains(".") && mBigText.length() <= 8) {
            mBigText = String.valueOf(new BigDecimal(mBigText)) + chr;
            showTextBig();
        }
    }

    private void calculate(char operator) {
        //Changes operator
        if (operator != '=' && mSmallText.length() != 0 && Character.toString(mSmallText.charAt(mSmallText.length() - 1)).matches("[-\\+\\*/]") && mChecker) {
            mSmallText = mSmallText.substring(0, mSmallText.length() - 1) + operator;
            showTextSmall();
            mOperatorContainer.add(operator);
            return;
        }

        BigDecimal result = BigDecimal.valueOf(0);
        mOperandContainer.add(new BigDecimal(mBigText));

        if (operator != '=' && (mOperatorContainer.size() < 1)) {
            mOperatorContainer.add(operator);
            showTextBig();
            mSmallText = mBigText + " " + operator;
            showTextSmall();
            mChecker = true;
            return;
        }

        if (mOperatorContainer.size() >= 1) {

            mOperatorContainer.add(operator);
            char previousOperator = mOperatorContainer.get(mOperatorContainer.size() - 2);
           //Does not allow division by 0
            if (mBigText.equals("0") && previousOperator == '/') {
                mBigText = "D'OH!";
                showTextBig();
                mBigText = "0";
                mSmallText = "";
                showTextSmall();
                return;
            }

            switch (previousOperator) {
                case '+':
                    result = mOperandContainer.get(mOperandContainer.size() - 2).add(mOperandContainer.get(mOperandContainer.size() - 1));
                    break;
                case '-':
                    result = mOperandContainer.get(mOperandContainer.size() - 2).subtract(mOperandContainer.get(mOperandContainer.size() - 1));
                    break;
                case '*':
                    result = mOperandContainer.get(mOperandContainer.size() - 2).multiply(mOperandContainer.get(mOperandContainer.size() - 1));
                    break;
                case '/':
                    result = mOperandContainer.get(mOperandContainer.size() - 2).divide(mOperandContainer.get(mOperandContainer.size() - 1), 8, RoundingMode.HALF_UP).stripTrailingZeros();
                    break;
            }
            if (operator == '=') {
                mSmallText = "";
                showTextSmall();
                mBigText = result.stripTrailingZeros().toPlainString();
                showTextBig();
                mOperatorContainer.clear();
                mOperandContainer.clear();
                return;
            }

            mOperandContainer.add(result);
            mSmallText = mSmallText + " " + mBigText + " " + operator;
            showTextSmall();
            mBigText = result.stripTrailingZeros().toPlainString();
            showTextBig();
            mChecker = true;
        }
    }
}