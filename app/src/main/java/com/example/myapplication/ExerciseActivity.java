package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.color.MaterialColors;

import java.util.Random;

public class ExerciseActivity extends AppCompatActivity {


    private int multiplier;
    private int multiplicationRes;
    Random random = new Random();
    private String currentAction;
    private Drawable background;
    private int color;


    private boolean isSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multiply);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.multiply), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView textView = findViewById(R.id.textView);
        String action = getIntent().getStringExtra(getString(R.string.action));
        String userChosenNumber = getIntent().getStringExtra(getString(R.string.chosen_number));
        Log.d("exercise", "user chose number: " + userChosenNumber);
        Log.d("exercise", "action is: " + action);
        if(!userChosenNumber.equals("?")){
            String prefix = getString(R.string.number_prefix);
            textView.setText(prefix + userChosenNumber);
        }
        else{
            textView.setText(getString(R.string.random_number_text));
        }

        TextView exercise = findViewById(R.id.exercise_textView_excercise);
        exercise.setText(getExercise(userChosenNumber, action));
    }


    @Override
    protected void onResume(){ //todo: maybe delete
        super.onResume();

        if(isSuccess) {
            String chosenNumber = getIntent().getStringExtra(getString(R.string.chosen_number));
            String originalAction = getIntent().getStringExtra(getString(R.string.action));
            Log.d("exercise", "chosen_number: " + chosenNumber + " action is: " + originalAction);
            TextView exercise = findViewById(R.id.exercise_textView_excercise);
            exercise.setText(getExercise(chosenNumber, originalAction));
            isSuccess = false;
        }
        TextView result = findViewById(R.id.exercise_textView_result);
        result.setText("?");
    }

    private void newExercise(){
        if(isSuccess) {
            String chosenNumber = getIntent().getStringExtra(getString(R.string.chosen_number));
            String originalAction = getIntent().getStringExtra(getString(R.string.action));
            Log.d("exercise", "chosen_number: " + chosenNumber + " action is: " + originalAction);
            TextView exercise = findViewById(R.id.exercise_textView_excercise);
            exercise.setText(getExercise(chosenNumber, originalAction));
            isSuccess = false;
        }
        TextView result = findViewById(R.id.exercise_textView_result);
        result.setText("?");
    }


    public String getExercise(String userNumber, String action){
        //handle number
        if(userNumber.equals("?")){
            userNumber = String.valueOf(random.nextInt(9) + 1);
            Log.d("randomized", "random number is: " + userNumber);
        }
        //handle action
        if(action.equals(getString(R.string.randomAction))){
            boolean random_boolean = random.nextBoolean();
            currentAction = random_boolean ? getString(R.string.multiplyAction) : getString(R.string.divideAction);
        }else{
            currentAction = action;
        }
        multiplier = random.nextInt(9) + 1;
        multiplicationRes = multiplier*Integer.parseInt(userNumber);
        if(currentAction.equals(getString(R.string.multiplyAction))){
            Log.d("multiplication", "multiplier: " + multiplier);
            Log.d("multiplication", "userNumber: " + userNumber);
            Log.d("multiplication", "res" + multiplicationRes);
            return userNumber + " X " + multiplier + " = ";
        }else{
            Log.d("division", "divisor: " + multiplier);
            Log.d("division", "userNumber: " + userNumber);
            Log.d("multiplication", "dividend" + multiplicationRes);
            return multiplicationRes + " / " + userNumber + " = ";
        }


    }

    public void numberPressed(View v){
        Button button  = (Button) v;
        CharSequence buttonText = button.getText();
        Log.d("success", "number " + buttonText + " was pressed");
        TextView currentResult = findViewById(R.id.exercise_textView_result);
        CharSequence resultText = currentResult.getText();
        char lastChar = resultText.charAt(resultText.length() - 1);
        if(lastChar == '?'){
            Log.d("success", "firstDigit");
            currentResult.setText(buttonText);
        }else{
            Log.d("success", "next digits");
            String newText = resultText + buttonText.toString();
            currentResult.setText(newText);
        }

    }

    public void submit(View v){
        TextView submittedAnswer = findViewById(R.id.exercise_textView_result);
        String answer = submittedAnswer.getText().toString();
        String tag = "";
        int expectedAnswer;

        if(currentAction.equals(getString(R.string.multiplyAction))){
            tag = "multiplication";
            expectedAnswer = multiplicationRes;
        }else {
            tag = "division";
            expectedAnswer = multiplier;
        }
        int numberAnswer;
        try{
            numberAnswer = Integer.parseInt(answer);
        }catch (NumberFormatException numberFormatException){
            Log.d("excercise", "number format error");
            return;
        }
            if(numberAnswer == expectedAnswer) {
            Log.d(tag, "correct");
            isSuccess = true;
            LottieAnimationView lottieAnimationView = findViewById(R.id.animationView);
            lottieAnimationView.setVisibility(View.VISIBLE);
            v.postDelayed(new Runnable() {
                public void run() {
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    newExercise();
                }
            }, 2500);

        }else {
            Log.d(tag, "incorrect");
            LottieAnimationView errorAnimation = findViewById(R.id.errorAnimationView);
            errorAnimation.setVisibility(View.VISIBLE);
            v.postDelayed(new Runnable() {
                public void run() {
                   errorAnimation.setVisibility(View.INVISIBLE);
                    newExercise();
                }
            }, 2500);

        }
    }


    public void reveal(View v){
        Button button = (Button) v;
        button.setEnabled(false);
        if(currentAction.equals(getString(R.string.multiplyAction))){
            button.setText(String.valueOf(multiplicationRes));
        }else{
            button.setText(String.valueOf(multiplier));
        }

    v.postDelayed(new Runnable(){
        public void run(){
            button.setText(getString(R.string.reveal));
            button.setEnabled(true);
        }
    }, 2500);
    }


    public void erase(View v){
        Log.d("success", "erase was pressed");
        TextView exercise = findViewById(R.id.exercise_textView_result);
        CharSequence exerciseText = exercise.getText();
        if(exerciseText.length() != 0){
            String newText = "";
            if(exerciseText.length() == 1){
                newText = "?";
            }else{
                newText = exerciseText.subSequence(0, exerciseText.length()-1).toString();
            }
            exercise.setText(newText);
        }

    }

    public void goBackToMain(View v){
        startActivity(new Intent(ExerciseActivity.this, MainActivity.class));
        Log.d("success","moved to main activity");
    }

    public void lottieAnimation(View v){
        LottieAnimationView lottieAnimationView = (LottieAnimationView) v;
        lottieAnimationView.playAnimation();
    }

}
