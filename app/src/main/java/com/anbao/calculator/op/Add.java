package com.anbao.calculator.op;

import com.anbao.calculator.Calculator;

import java.math.BigDecimal;

public class Add implements IOperation {
    @Override
    public BigDecimal eval(Calculator calculator) {
        BigDecimal first = calculator.getFirstNumber();
        BigDecimal second = calculator.getSecondNumber();
        return first.add(second);
    }
}
