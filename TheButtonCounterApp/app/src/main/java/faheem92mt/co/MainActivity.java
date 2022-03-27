package faheem92mt.co;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // our variables which refer to the widgets
    private EditText userInput;
    private TextView textView;

    private static final String TAG = "MainActivity";
    private final String TEXT_CONTENTS = "TextContents";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: in");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // here we are connecting our previously created variables (lines 13-15) with the id's defined earlier in the "Design" (xml)
        userInput = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        // this is to make sure the text view is emptied out before the compiler starts showing output there
        textView.setText("");
        userInput.setText("");
        // this is to add scrolling capabilities to the text view
        textView.setMovementMethod(new ScrollingMovementMethod());

        // we are creating an object of the class "View.OnClickListener"
        // this object will tell the compiler what needs to be done when the button is clicked
        // well, actually, it's more like the method inside this object will tell what needs to be done
        View.OnClickListener ourOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String result = userInput.getText().toString();
                result = result + "\n";
                textView.append(result);
                userInput.setText("");
            }
        };

        // this statement tells the compiler what to do when the button is clicked
        // when clicked, the button will execute the method "onClick(View view)" [line35] which is situated inside the "ourOnClickListener" object [line 33]
        button.setOnClickListener(ourOnClickListener);
        Log.d(TAG, "onCreate: out");



    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onstart: in");
        super.onStart();
        Log.d(TAG, "onstart: out");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: in");
        super.onRestoreInstanceState(savedInstanceState);
        String savedString = savedInstanceState.getString(TEXT_CONTENTS);
        textView.setText(savedString);
        Log.d(TAG, "onRestoreInstanceState: out");
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: in");
        super.onRestart();
        Log.d(TAG, "onRestart: out");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: in");
        super.onResume();
        Log.d(TAG, "onResume: out");
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: in");
        super.onPause();
        Log.d(TAG, "onPause: out");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: in");
        outState.putString(TEXT_CONTENTS, textView.getText().toString());
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: out");
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: in");
        super.onStop();
        Log.d(TAG, "onStop: out");
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }


}