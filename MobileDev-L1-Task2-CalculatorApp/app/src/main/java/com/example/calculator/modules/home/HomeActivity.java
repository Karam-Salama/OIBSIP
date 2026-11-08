//package com.example.calculator.modules.home.views;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.calculator.R;
//import com.google.android.material.button.MaterialButton;
//import org.mozilla.javascript.Context;
//import org.mozilla.javascript.Scriptable;
//
//public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
//    TextView solution_tv,result_tv;
//    MaterialButton button_ac,button_delete_symbol,button_mod,button_divide;
//    MaterialButton digit_1,digit_2,digit_3,digit_4,digit_5,digit_6,digit_7,digit_8,digit_9;
//    MaterialButton button_mult,button_subtract,button_equal;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_home);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        solution_tv = findViewById(R.id.solution_tv);
//        result_tv = findViewById(R.id.result_tv);
//
//        assignId(button_ac,R.id.button_ac);
//        assignId(button_delete_symbol,R.id.button_delete_symbol);
//        assignId(button_mod,R.id.button_mod);
//        assignId(button_divide,R.id.button_divide);
//        assignId(digit_1,R.id.digit_1);
//        assignId(digit_2,R.id.digit_2);
//        assignId(digit_3,R.id.digit_3);
//        assignId(digit_4,R.id.digit_4);
//        assignId(digit_5,R.id.digit_5);
//        assignId(digit_6,R.id.digit_6);
//        assignId(digit_7,R.id.digit_7);
//        assignId(digit_8,R.id.digit_8);
//        assignId(digit_9,R.id.digit_9);
//        assignId(button_mult,R.id.button_mult);
//        assignId(button_subtract,R.id.button_subtract);
//        assignId(button_equal,R.id.button_equal);
//    }
//    void assignId(MaterialButton btn,int id){
//        btn = findViewById(id);
//        btn.setOnClickListener(this);
//    }
//    @Override
//    public void onClick(View view) {
//        MaterialButton button = (MaterialButton) view;
//        String buttonText = button.getText().toString();
//        String dataToCalculate =solution_tv.getText().toString();
//
//
//        if(buttonText.equals("AC")){
//            solution_tv.setText("");
//            result_tv.setText("0");
//            return;
//        }
//        if(buttonText.equals("=")){
//            solution_tv.setText( result_tv.getText());
//            return;
//        }
//
//        if(buttonText.equals("⌫")){
//            dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
//            return;
//        } else {
//            dataToCalculate = dataToCalculate + buttonText;
//        }
//
//        solution_tv.setText(dataToCalculate);
//        String  finalResult = getResult(dataToCalculate);
//        if(!finalResult.equals("Error")){
//            result_tv.setText(finalResult);
//        }
//    }
//
//    String getResult(String data){
//        try {
//            Context context = Context.enter();
//            context.setOptimizationLevel(-1);
//            Scriptable scriptable = context.initStandardObjects();
//            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
//            if(finalResult.endsWith(".0")){
//                finalResult = finalResult.replace(".0", "");
//            }
//            return finalResult;
//        } catch (Exception e){
//            return "Error";
//        }
//    }
//}


// =====================================================================================
//
//package com.example.calculator.modules.home.views;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.calculator.R;
//import com.google.android.material.button.MaterialButton;
//
//import org.mozilla.javascript.Context;
//import org.mozilla.javascript.Scriptable;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//
//public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
//
//    private static final String DIV_ZERO = "DIV_ZERO";
//    private static final String OPERATORS = "+-−×÷%*/";
//
//    private TextView solutionTv, resultTv;
//    private boolean resultIsError = false;
//
//    // Every button on the screen. Check these ids against your activity_home.xml.
//    private static final int[] BUTTON_IDS = {
//            R.id.button_ac, R.id.button_delete_symbol, R.id.button_mod, R.id.button_divide,
//            R.id.digit_7, R.id.digit_8, R.id.digit_9, R.id.button_mult,
//            R.id.digit_4, R.id.digit_5, R.id.digit_6, R.id.button_subtract,
//            R.id.digit_1, R.id.digit_2, R.id.digit_3, R.id.button_add,
//            R.id.button_negate, R.id.digit_0, R.id.button_decimal, R.id.button_equal
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_home);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        solutionTv = findViewById(R.id.solution_tv);
//        resultTv = findViewById(R.id.result_tv);
//
//        for (int id : BUTTON_IDS) {
//            MaterialButton button = findViewById(id);
//            button.setOnClickListener(this);
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        int id = view.getId();
//        String expr = solutionTv.getText().toString();
//
//        if (id == R.id.button_ac) {
//            solutionTv.setText("");
//            resultTv.setText("0");
//            resultIsError = false;
//            return;
//        }
//
//        if (id == R.id.button_equal) {
//            if (!expr.isEmpty() && !resultIsError) {
//                solutionTv.setText(resultTv.getText());
//            }
//            return;
//        }
//
//        if (id == R.id.button_delete_symbol) {
//            if (!expr.isEmpty()) {
//                expr = expr.substring(0, expr.length() - 1);
//            }
//            update(expr);
//            return;
//        }
//
//        if (id == R.id.button_negate) {
//            negate(expr);
//            return;
//        }
//
//        String t = ((MaterialButton) view).getText().toString();
//
//        if (isOperator(t)) {
//            if (expr.isEmpty()) {
//                if (!isMinus(t.charAt(0))) return;
//            } else if (isOperator(expr.substring(expr.length() - 1))) {
//                expr = expr.substring(0, expr.length() - 1);
//            }
//        } else if (t.equals(".")) {
//            int start = lastOperatorIndex(expr) + 1;
//            if (expr.substring(start).contains(".")) return;
//            if (expr.length() == start) expr += "0";
//        }
//
//        update(expr + t);
//    }
//
//    private void negate(String expr) {
//        int end = expr.length();
//        int start = end;
//        while (start > 0 && (Character.isDigit(expr.charAt(start - 1))
//                || expr.charAt(start - 1) == '.')) {
//            start--;
//        }
//        if (start == end) return;   // nothing to negate (empty, or ends with an operator)
//
//        String head = expr.substring(0, start);   // everything before the number
//        String number = expr.substring(start);
//
//        if (head.isEmpty()) {
//            head = "−";
//        } else {
//            char op = head.charAt(head.length() - 1);
//            String beforeOp = head.substring(0, head.length() - 1);
//            boolean isSign = isMinus(op)
//                    && (beforeOp.isEmpty() || isOperator(beforeOp.substring(beforeOp.length() - 1)));
//
//            if (isSign) {
//                head = beforeOp;                  // remove the sign: −5 becomes 5
//            } else if (isMinus(op)) {
//                head = beforeOp + "+";            // 5−3 becomes 5+3
//            } else if (op == '+') {
//                head = beforeOp + "−";            // 5+3 becomes 5−3
//            } else {
//                head = head + "−";                // 5×3 becomes 5×−3
//            }
//        }
//        update(head + number);
//    }
//
//    private void update(String expr) {
//        solutionTv.setText(expr);
//        if (expr.isEmpty()) {
//            resultTv.setText("0");
//            resultIsError = false;
//            return;
//        }
//
//        String result = evaluate(expr);
//        if (result == null) return;               // incomplete expression, keep last result
//        if (result.equals(DIV_ZERO)) {
//            resultTv.setText(getString(R.string.error_divide_by_zero));
//            resultIsError = true;
//        } else {
//            resultTv.setText(result);
//            resultIsError = false;
//        }
//    }
//
//    private boolean isMinus(char c) {
//        return c == '-' || c == '−';
//    }
//
//    private boolean isOperator(String s) {
//        return s.length() == 1 && OPERATORS.contains(s);
//    }
//
//    private int lastOperatorIndex(String expr) {
//        for (int i = expr.length() - 1; i >= 0; i--) {
//            if (OPERATORS.indexOf(expr.charAt(i)) >= 0) return i;
//        }
//        return -1;
//    }
//
//    private String evaluate(String expr) {
//        String js = expr.replace('×', '*').replace('÷', '/').replace('−', '-');
//        // JavaScript treats 08 / 010 as octal, so drop leading zeros: 007 becomes 7.
//        js = js.replaceAll("(?<![0-9.])0+(?=[0-9])", "");
//
//        Context rhino = Context.enter();
//        try {
//            rhino.setOptimizationLevel(-1);   // required on Android
//            Scriptable scope = rhino.initStandardObjects();
//            Object value = rhino.evaluateString(scope, js, "calc", 1, null);
//            double d = Context.toNumber(value);
//
//            if (Double.isNaN(d) || Double.isInfinite(d)) return DIV_ZERO;
//
//            // Max 3 digits after the decimal point: 10 ÷ 3 becomes 3.333, 15000.0 becomes 15000.
//            return new BigDecimal(d)
//                    .setScale(3, RoundingMode.HALF_UP)
//                    .stripTrailingZeros()
//                    .toPlainString();
//        } catch (Exception e) {
//            return null;
//        } finally {
//            Context.exit();   // always release the Rhino context
//        }
//    }
//}

//=======================================================


package com.example.calculator.modules.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.calculator.R;
import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView solution_tv, result_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        solution_tv = findViewById(R.id.solution_tv);
        result_tv = findViewById(R.id.result_tv);

        int[] ids = {
                R.id.button_ac, R.id.button_delete_symbol, R.id.button_mod, R.id.button_divide,
                R.id.digit_7, R.id.digit_8, R.id.digit_9, R.id.button_mult,
                R.id.digit_4, R.id.digit_5, R.id.digit_6, R.id.button_subtract,
                R.id.digit_1, R.id.digit_2, R.id.digit_3, R.id.button_add,
                R.id.button_negate, R.id.digit_0, R.id.button_decimal, R.id.button_equal
        };
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String data = solution_tv.getText().toString();

        // AC: clear everything
        if (buttonText.equals("AC")) {
            solution_tv.setText("");
            result_tv.setText("0");
            return;
        }

        // = : move the result up (unless it is the error message)
        if (buttonText.equals("=")) {
            String result = result_tv.getText().toString();
            if (!data.isEmpty() && !result.equals(getString(R.string.error_divide_by_zero))) {
                solution_tv.setText(result.replace("-", "−"));
            }
            return;
        }

        if (buttonText.equals("⌫")) {
            // delete the last character (only if there is one)
            if (!data.isEmpty()) {
                data = data.substring(0, data.length() - 1);
            }

        } else if (buttonText.equals("±")) {
            // flip the sign of the last number
            int start = numberStart(data);
            if (start == data.length()) return;          // no number to flip
            String before = data.substring(0, start);
            String number = data.substring(start);

            if (before.isEmpty()) {
                before = "−";                                                  // 5 -> −5
            } else if (before.endsWith("−") && (before.length() == 1
                    || isOperator(before.charAt(before.length() - 2)))) {
                before = before.substring(0, before.length() - 1);             // −5 -> 5
            } else if (before.endsWith("−")) {
                before = before.substring(0, before.length() - 1) + "+";       // 5−3 -> 5+3
            } else if (before.endsWith("+")) {
                before = before.substring(0, before.length() - 1) + "−";       // 5+3 -> 5−3
            } else {
                before = before + "−";                                         // 5×3 -> 5×−3
            }
            data = before + number;

        } else if (buttonText.length() == 1 && isOperator(buttonText.charAt(0))) {
            // can't start with an operator
            if (data.isEmpty() || data.equals("−")) return;
            // two operators in a row: replace the old one
            if (isOperator(data.charAt(data.length() - 1))) {
                data = data.substring(0, data.length() - 1);
            }
            data = data + buttonText;

        } else if (buttonText.equals(".")) {
            // only one dot in each number
            String currentNumber = data.substring(numberStart(data));
            if (currentNumber.contains(".")) return;
            if (currentNumber.isEmpty()) data = data + "0";   // .5 -> 0.5
            data = data + ".";

        } else {
            // digits
            data = data + buttonText;
        }

        solution_tv.setText(data);
        if (data.isEmpty()) {
            result_tv.setText("0");
            return;
        }
        String finalResult = getResult(data);
        if (!finalResult.equals("Error")) {
            result_tv.setText(finalResult);
        }
    }

    boolean isOperator(char c) {
        return "+−×÷%".indexOf(c) != -1;
    }

    // index where the last number (digits and dot) starts
    int numberStart(String data) {
        int i = data.length();
        while (i > 0 && (Character.isDigit(data.charAt(i - 1)) || data.charAt(i - 1) == '.')) {
            i--;
        }
        return i;
    }

    String getResult(String data) {
        // JavaScript doesn't know × ÷ −
        String js = data.replace("×", "*").replace("÷", "/").replace("−", "-");
        // JavaScript reads 08 / 010 as octal, so remove leading zeros (007 -> 7)
        js = js.replaceAll("(?<![0-9.])0+(?=[0-9])", "");

        Context context = Context.enter();
        try {
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            Object value = context.evaluateString(scriptable, js, "Javascript", 1, null);
            double number = Context.toNumber(value);

            // 5 ÷ 0 gives Infinity, 0 ÷ 0 gives NaN
            if (Double.isNaN(number) || Double.isInfinite(number)) {
                return getString(R.string.error_divide_by_zero);
            }
            // max 3 digits after the dot: 10 ÷ 3 -> 3.333, 15000.0 -> 15000
            return new BigDecimal(number).setScale(3, RoundingMode.HALF_UP)
                    .stripTrailingZeros().toPlainString();
        } catch (Exception e) {
            return "Error";
        } finally {
            Context.exit();
        }
    }
}