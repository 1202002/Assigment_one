package com.example.a1201952_todo1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class FeedbackActivity extends AppCompatActivity {

    private TextView qOneScore, qTwoScore, qThreeScore, qFourScore, finalScore;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // get views
        qOneScore = (TextView) findViewById(R.id.qone_score);
        qTwoScore = (TextView) findViewById(R.id.qtwo_score);
        qThreeScore = (TextView) findViewById(R.id.qthree_score);
        qFourScore = (TextView) findViewById(R.id.qfour_score);
        finalScore = (TextView) findViewById(R.id.final_score);
        homeButton = (Button) findViewById(R.id.home_button);

        // define an array of TextViews and an array of score texts
        TextView[] scoreTextViews = {qOneScore, qTwoScore, qThreeScore, qFourScore};
        String[] scoreTexts = new String[4];

        // get scores from intent
        int[] scores = getIntent().getIntArrayExtra("scores");

        // loop through each score and set the text and color dynamically
        for (int i = 0; i < scores.length; i++) {
            scoreTexts[i] = scores[i] + "/1";
            scoreTextViews[i].setText(scoreTexts[i]);

            // Set text color based on score
            if (scores[i] == 1) {
                scoreTextViews[i].setTextColor(Color.GREEN); // Set text color to green for correct answer
            } else {
                scoreTextViews[i].setTextColor(Color.RED); // Set text color to red for incorrect answer
            }
        }


        int sumAllScores =0;
        for (int score : scores) sumAllScores += score;
        finalScore.setText(sumAllScores + "/4");

        // event listener on home button
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}