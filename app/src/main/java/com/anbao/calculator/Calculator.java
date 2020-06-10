package com.anbao.calculator;

import com.anbao.calculator.op.Operation;

import java.math.BigDecimal;

public class Calculator {
    private String currentStrValue;
    private BigDecimal firstNumber;
    private BigDecimal secondNumber;
    private Operation currOp;

    public Calculator() {
        this.currentStrValue = "0";
        this.firstNumber = new BigDecimal(0);
        this.secondNumber = new BigDecimal(0);
    }

    public String getCurrentStrValue() {
        return currentStrValue.toString();
    }

    public void setCurrentStrValue(String currentStrValue) {
        this.currentStrValue = currentStrValue;
    }

    public void setCurrentStrValue(BigDecimal currentStrValue) {
        this.currentStrValue = currentStrValue.toString();
        this.secondNumber = currentStrValue;
    }

    public BigDecimal getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(BigDecimal firstNumber) {
        this.firstNumber = firstNumber;
    }

    public BigDecimal getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(BigDecimal secondNumber) {
        this.secondNumber = secondNumber;
    }

    public void reset() {
        this.currentStrValue = "0";
        this.firstNumber = new BigDecimal(0);
        this.secondNumber = new BigDecimal(0);
        this.currOp = null;
    }

    public Operation getCurrOp() {
        return currOp;
    }

    public void setCurrOp(Operation currOp) {
        this.currOp = currOp;
    }

    public void updateCurr() {
        if (this.currentStrValue.length() == 0) {
            this.secondNumber = new BigDecimal(0);
            this.setCurrentStrValue("0");
        } else {
            this.secondNumber = new BigDecimal(this.currentStrValue);
            this.setCurrentStrValue(this.secondNumber.toString());
        }
    }

    public void moveToFirst() {
        this.firstNumber = this.firstNumber.add(this.secondNumber);
        this.secondNumber = new BigDecimal(0);
        this.setCurrentStrValue("0");
    }
}
