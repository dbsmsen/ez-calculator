package com.example.ez_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, expression_Tv;



    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        expression_Tv = findViewById(R.id.expression_tv);

        assignId(buttonC, R.id.button_c);
        assignId(buttonBrackOpen, R.id.button_open_bracket);
        assignId(buttonBrackClose, R.id.button_close_bracket);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonMultiply, R.id.button_multiply);
        assignId(buttonPlus, R.id.button_plus);
        assignId(buttonMinus, R.id.button_minus);
        assignId(buttonEquals, R.id.button_equals);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonAC, R.id.button_ac);
        assignId(buttonDot, R.id.button_dot);


    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = expression_Tv.getText().toString();

        if (buttonText.equals("AC")) {
            expression_Tv.setText("");
            resultTv.setText("0");
            return;
        }

        if (buttonText.equals("=")) {
            String finalResult = getResult(dataToCalculate);

            if (!finalResult.equals("Error")) {
                resultTv.setText(finalResult);
            }

            return;
        }

        if (buttonText.equals("C")) {
            if(dataToCalculate.length() == 0) // dont crash on empty string
                return;
            else {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
                expression_Tv.setText(dataToCalculate);
                return;
            }
        }

        dataToCalculate = dataToCalculate + buttonText;
        expression_Tv.setText(dataToCalculate);

//        String finalResult = getResult(dataToCalculate);

//        if (!finalResult.equals("Err")) {
//            resultTv.setText(finalResult);
//        }

    }

    String getResult(String expression) {

//        Rhino - https://javadoc.io/doc/org.mozilla/rhino/latest/index.html
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, expression, "Expression", 1, null).toString();

//            0.21439721597421 - 0.21439
//            0.21 - 0.21

            int decimalIndex = finalResult.indexOf('.');

            if( decimalIndex != -1) {
                // if more than 5 decimal numbers after the point, truncate
                if(decimalIndex + 5 < finalResult.length()) {
                    String beforeDecimal = finalResult.substring(0, decimalIndex + 1);
                    String decimals = finalResult.substring(decimalIndex + 1, decimalIndex + 6);

                    finalResult = beforeDecimal + decimals;
                    System.out.println(finalResult);
                }
            }

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Error";
        }
    }
}
