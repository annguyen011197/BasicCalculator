package com.anbao.calculator.op;

import com.anbao.calculator.Calculator;

import java.math.BigDecimal;

public class Minus implements IOperation {

    @Override
    public BigDecimal eval(Calculator calculator) {
        BigDecimal first = calculator.getFirstNumber();
        BigDecimal second = calculator.getSecondNumber();
        return first.subtract(second);
    }
}
