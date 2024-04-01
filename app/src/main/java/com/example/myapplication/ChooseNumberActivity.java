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

public class ChooseNumberActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_number);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.choose_number), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void goBackToMain(View v){
        startActivity(new Intent(ChooseNumberActivity.this, MainActivity.class));
        Log.d("success","moved to main activity");
    }

    public void chooseNumber(View v){
        Button button = (Button) v;
        String text = String.valueOf(button.getText());
        Log.d("button pressed", text);
        Intent intent = new Intent(ChooseNumberActivity.this, ExerciseActivity.class);
        intent.putExtra(getString(R.string.chosen_number), text);
        intent.putExtra(getString(R.string.action), getIntent().getStringExtra(getString(R.string.action)));
        startActivity(intent);
    }


}



