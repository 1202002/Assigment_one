package com.example.a1201952_todo1;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get t/f and mcq mode buttons
        Button multipleChoiceModeBtn = (Button) findViewById(R.id.multiple_choice_button);
        Button trueFalseModeBtn = (Button) findViewById(R.id.true_false_button);

        // add event listener for each button
        multipleChoiceModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz("multiple_choice");
            }
        });

        trueFalseModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz("true_false");
            }
        });


    }


    // Method to start the QuestionActivity and pass the quiz mode
    private void startQuiz(String quizMode) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("quiz_mode", quizMode);
        startActivity(intent);
    }


}