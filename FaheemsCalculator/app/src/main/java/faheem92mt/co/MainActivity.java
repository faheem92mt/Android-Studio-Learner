package faheem92mt.co;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // variables for widgets
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";

    // variables to hold operands and types of operation
    private Double operand1 = null;
    private String pendingOperation = "=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connecting the variables with the id's
        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);

        // buttons variables created and connected simultaneously
        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);

        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);

        Button buttonNeg = (Button) findViewById(R.id.buttonNeg);
        Button buttonReset = (Button) findViewById(R.id.buttonReset);

        View.OnClickListener reseter = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("");
                operand1 = null;
                newNumber.setText("");
            }
        };

        buttonReset.setOnClickListener(reseter);

        View.OnClickListener dot = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                newNumber.append(b.getText().toString());
            }
        };

        // what to do when number or dot buttons are clicked
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creating a button and connecting it with the button being clicked (view)
                Button b = (Button) view;
                String value = b.getText().toString();

                // the widget "new number" shows the text of the buttons being clicked
                newNumber.append(b.getText().toString());


            }
        };

        // number and dot buttons are linked with the object which specifies what needs to be done on being clicked
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(dot);

        // what to do when the operator buttons are clicked
        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creating a button and connecting it with the button being clicked (view)
                Button b = (Button) view;
                // a string variable which holds the text of the button being clicked
                String op = b.getText().toString();
                // a string that stores the value of the text from the widget "new number"
                String value = newNumber.getText().toString();


                try {
                    Double doubleValue = Double.valueOf(value);
                    // the method will be executed only if the widget "new number" is not empty
                    performOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }

//                if(value.length() != 0) {
//                    performOperation(value, op);
//                }

                // variable which holds the value of the operator being clicked
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };


        // operator buttons are linked with the object which specifies what needs to be done on being clicked
        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);

        View.OnClickListener negListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                if(newNumber.getText().toString() == null) {
//                    status = true;
//                }


                // number already present in newNumber


                String value = newNumber.getText().toString();

                try {
                    Double doubleValue = Double.valueOf(value);
                    doubleValue *= -1;
                    newNumber.setText(doubleValue.toString());
                } catch (NumberFormatException e) {
//                        newNumber.setText("Fuck");
                    newNumber.setText("-");
                }


            }
        };

        buttonNeg.setOnClickListener(negListener);


    }

    @Override
    protected void onStart() {
//        Log.d(TAG, "onstart: in");
        super.onStart();
//        Log.d(TAG, "onstart: out");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        Log.d(TAG, "onRestoreInstanceState: in");
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);
//        Log.d(TAG, "onRestoreInstanceState: out");
    }

    @Override
    protected void onRestart() {
//        Log.d(TAG, "onRestart: in");
        super.onRestart();
//        Log.d(TAG, "onRestart: out");
    }

    @Override
    protected void onResume() {
//        Log.d(TAG, "onResume: in");
        super.onResume();
//        Log.d(TAG, "onResume: out");
    }

    @Override
    protected void onPause() {
//        Log.d(TAG, "onPause: in");
        super.onPause();
//        Log.d(TAG, "onPause: out");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        Log.d(TAG, "onSaveInstanceState: in");
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
//        Log.d(TAG, "onSaveInstanceState: out");
    }

    @Override
    protected void onStop() {
//        Log.d(TAG, "onStop: in");
        super.onStop();
//        Log.d(TAG, "onStop: out");
    }

    private void performOperation(Double value, String operation) {

        if (operand1 == null) {
            // i guess this is the situation when we click the + (or any operator) button first and then a number
            operand1 = value;
        } else {

            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }

            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
            }
        }

        result.setText(operand1.toString());
        newNumber.setText("");
    }


}