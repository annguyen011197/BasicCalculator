package com.anbao.calculator.op;

public final class OperationBuilder {
    public static IOperation build(Operation op) {
        if (op == null) { return null; }
        switch (op) {
            case ADD:
                return new Add();
            case MINUS:
                return new Minus();
            case MUL:
                return new Multiply();
            case DIV:
                return new Divide();
        }

        return null;
    }
}
