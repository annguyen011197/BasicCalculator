package com.anbao.calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anbao.calculator.op.Operation;

import java.util.HashMap;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity implements CalculatorPresenter.Observer {

    private int[] digiIds = {R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9};
    private TextView label;
    private Button btnDot, btnClear, btnAdd, btnMinus, btnMul, btnDiv, btnEqual;
    private CalculatorPresenter presenter = new CalculatorPresenter();
    private HashMap<Operation, Button> operationMap  = new HashMap<>();

    private CalOnClickListener pressClear = new CalOnClickListener(
            this.presenter,
            new Function<CalculatorPresenter, Void>() {
                @Override
                public Void apply(CalculatorPresenter presenter) {
                    presenter.clearPress();
                    return null;
                }
            }
    );

    private CalOnClickListener pressDot = new CalOnClickListener(
            this.presenter,
            new Function<CalculatorPresenter, Void>() {
                @Override
                public Void apply(CalculatorPresenter presenter) {
                    presenter.dotPress();
                    return null;
                }
            }
    );

    private CalOnClickListener pressEqual = new CalOnClickListener(
            this.presenter,
            new Function<CalculatorPresenter, Void>() {
                @Override
                public Void apply(CalculatorPresenter presenter) {
                    presenter.equalPress();
                    return null;
                }
            }
    );

    @Override
    protected void onStart() {
        super.onStart();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter.setObserver(this);
        label = findViewById(R.id.label);
        for (int index = 0; index < digiIds.length; index++) {
            Button button = findViewById(digiIds[index]);
            button.setText(this.format(index));

            final int value = index;
            button.setOnClickListener(new CalOnClickListener(
                    this.presenter,
                    new Function<CalculatorPresenter, Void>() {
                        @Override
                        public Void apply(CalculatorPresenter presenter) {
                            presenter.appendNewDigi(value);
                            return null;
                        }
                    }
            ));
        }

        btnDot = findViewById(R.id.btn_dot);
        btnDot.setText(".");
        btnDot.setOnClickListener(pressDot);

        btnClear = findViewById(R.id.btn_c);
        btnClear.setText("C");
        btnClear.setTextColor(Color.RED);
        btnClear.setOnClickListener(pressClear);

        btnAdd = findViewById(R.id.btn_plus);
        btnAdd.setText("+");
        btnAdd.setOnClickListener(
                this.operationClick(Operation.ADD)
        );
        operationMap.put(Operation.ADD, btnAdd);

        btnMinus = findViewById(R.id.btn_minus);
        btnMinus.setText("-");
        btnMinus.setOnClickListener(
                this.operationClick(Operation.MINUS)
        );
        operationMap.put(Operation.MINUS, btnMinus);

        btnMul = findViewById(R.id.btn_mul);
        btnMul.setText("x");
        btnMul.setOnClickListener(
                this.operationClick(Operation.MUL)
        );
        operationMap.put(Operation.MUL, btnMul);

        btnDiv = findViewById(R.id.btn_div);
        btnDiv.setText("/");
        btnDiv.setOnClickListener(
                this.operationClick(Operation.DIV)
        );
        operationMap.put(Operation.DIV, btnDiv);

        btnEqual = findViewById(R.id.btn_equal);
        btnEqual.setOnClickListener(pressEqual);
    }

    private CalOnClickListener operationClick(final Operation operation) {
        return new CalOnClickListener(
                this.presenter,
                new Function<CalculatorPresenter, Void>() {
                    @Override
                    public Void apply(CalculatorPresenter presenter) {
                        presenter.operationPress(operation);
                        return null;
                    }
                }
        );
    }


    private String format(int value) {
        return String.format(Locale.getDefault(), "%d", value);
    }

    @Override
    public void onChangeStr(String currentValue) {
        this.label.setText(currentValue);
    }

    @Override
    public void disableOp(Operation operation) {
        operationMap.values().forEach(new Consumer<Button>() {
            @Override
            public void accept(Button button) {
                button.setEnabled(true);
            }
        });

        Button btn = operationMap.get(operation);
        if (btn != null) {
            btn.setEnabled(false);
        }
    }

    class CalOnClickListener implements View.OnClickListener {
        private CalculatorPresenter presenter;
        private Function<CalculatorPresenter, Void> exec;

        CalOnClickListener(CalculatorPresenter presenter, Function<CalculatorPresenter, Void> exe) {
            this.presenter = presenter;
            this.exec = exe;
        }

        @Override
        public void onClick(View v) {
            this.exec.apply(presenter);
        }
    }
}

