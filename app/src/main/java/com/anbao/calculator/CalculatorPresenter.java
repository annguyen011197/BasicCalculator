package com.anbao.calculator;

import com.anbao.calculator.op.IOperation;
import com.anbao.calculator.op.Operation;
import com.anbao.calculator.op.OperationBuilder;

import java.math.BigDecimal;

public class CalculatorPresenter {
    private Calculator model;
    private Observer observer;
    private boolean isDotPress = false;;

    public CalculatorPresenter() {
        this.model = new Calculator();
    }

    public void appendNewDigi(int newDigi) {
        this.model.setCurrentStrValue(this.model.getCurrentStrValue() + newDigi);
        this.model.updateCurr();
        this.isDotPress = false;
        notifyChange();
    }

    public void dotPress() {
        if (isDotPress) { return; }
        this.model.setCurrentStrValue(this.model.getCurrentStrValue() + ".");
        this.isDotPress = true;
        notifyChange();
    }

    public void clearPress() {
        this.model.setCurrentStrValue("0");
        this.model.updateCurr();
        notifyChange();
    }

    public void equalPress() {
        this.dotCheck();
        IOperation operation = OperationBuilder.build(this.model.getCurrOp());
        if (operation != null) {
            BigDecimal result = operation.eval(this.model);
            this.model.reset();
            this.model.setCurrentStrValue(result);
        }

        notifyChange();
    }

    public void operationPress(Operation operation) {
        this.dotCheck();
        this.model.moveToFirst();
        this.model.setCurrOp(operation);
        notifyChange();
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    private void dotCheck() {
        if (!isDotPress) { return; }
        this.model.setCurrentStrValue(this.model.getCurrentStrValue() + 0);
        this.model.updateCurr();
        isDotPress = false;
    }

    private void notifyChange() {
        if (this.observer == null) {
            return;
        }

        observer.onChangeStr(this.model.getCurrentStrValue());
        observer.disableOp(this.model.getCurrOp());
    }

    public interface Observer {
        void onChangeStr(String currentValue);
        void disableOp(Operation operation);
    }

}
