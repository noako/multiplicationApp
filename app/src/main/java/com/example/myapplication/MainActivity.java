package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void toggle (View v){
        v.setEnabled(false);
    }

    public void goToChooseNumber(View v){
        CharSequence buttonText = ((Button) v).getText();
        String action;
        if(buttonText.equals(getString(R.string.multiply))){
            action = getString(R.string.multiplyAction);
        }else if(buttonText.equals(getString(R.string.division))){
            action = getString(R.string.divideAction);
        }else{
            action = getString(R.string.randomAction);
        }
        Intent intent = new Intent(MainActivity.this, ChooseNumberActivity.class);
        intent.putExtra(getString(R.string.action), action);
        startActivity(intent);
        Log.d("success","moved to choose number activity with action: " + action);
    }

    public void testOnClick(View v){
        v.setEnabled(false);
        Button b = (Button) v;
        b.setText("changed text");
        Button randomButtonView = findViewById(R.id.button_random);
        randomButtonView.setText("changed");

    }
}