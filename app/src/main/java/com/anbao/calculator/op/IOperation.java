package com.anbao.calculator.op;

import com.anbao.calculator.Calculator;

import java.math.BigDecimal;

public interface IOperation {
    BigDecimal eval(Calculator calculator);
}
