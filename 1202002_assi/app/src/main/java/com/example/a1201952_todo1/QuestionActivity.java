package com.example.a1201952_todo1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    private List<Question> multipleChoiceQuestions; // List of Multiple Choice Questions
    private List<Question> trueFalseQuestions; // List of True False Questions
    private int currentQuestionIndex = 0; // current question to be displayed from the list

    // views
    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button finishButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Retrieve the quiz mode from the intent extra
        String quizMode = getIntent().getStringExtra("quiz_mode");

        // get the page title text view
        TextView quizModeTxtView = (TextView) findViewById(R.id.mode_text_view);

        // Initialize the list of questions based on the quiz mode, and set the title
        if ("multiple_choice".equals(quizMode)) {
            initializeMultipleChoiceQuestionsList();
            quizModeTxtView.setText("Multiple Choice Mode");
        } else {
            initializeTrueFalseQuestionsList();
            quizModeTxtView.setText("True False Mode");
        }

        // Initialize views
        questionTextView = (TextView) findViewById(R.id.question_text_view);
        optionsRadioGroup = (RadioGroup) findViewById(R.id.options_group);
        finishButton = (Button) findViewById(R.id.finish_button);
        nextButton = (Button) findViewById(R.id.next_button);

        // display the first question and disable finish and next buttons
        finishButton.setEnabled(false);
        nextButton.setEnabled(false);
        displayQuestion(currentQuestionIndex, quizMode);



        // Set a checked change listener to the RadioGroup, in order to open Next button
        optionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check if any radio button is currently checked
                boolean isChecked = checkedId != -1;

                // Enable or disable the Next Button based on the checked state and question No.
                if(currentQuestionIndex != 3){
                    nextButton.setEnabled(isChecked);
                } else {
                    finishButton.setEnabled(true);
                }
            }
        });

        // Set click listener for next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = ""; // default answer

                // Get user answer
                int selectedRadioButtonId = optionsRadioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    userAnswer = getUserChoice(selectedRadioButtonId);
                } 

                // set answer and score
                setAnswerAndScore(userAnswer, quizMode);

                // Move to next question
                currentQuestionIndex++;

                if (currentQuestionIndex < 4) {
                    displayQuestion(currentQuestionIndex, quizMode);
                    nextButton.setEnabled(false); // disable the button for the next question
                }

            }
        });


        // Set click listener for finish button
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get the result of last question
                String userAnswer = ""; // default answer
                int selectedRadioButtonId = optionsRadioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    userAnswer = getUserChoice(selectedRadioButtonId);
                }

                // set answer and score
                setAnswerAndScore(userAnswer, quizMode);

                // Handle finishing the quiz (show feedback)
                int[] scores = getAllScores(quizMode);

                // Create intent for feedback activity
                Intent intent = new Intent(QuestionActivity.this, FeedbackActivity.class);

                // Put the scores array as an extra in the intent
                intent.putExtra("scores", scores);

                // Start feedback activity
                startActivity(intent);
            }
        });

    }



    // method to initialize the mcq list
    private void initializeMultipleChoiceQuestionsList(){
        multipleChoiceQuestions = new ArrayList<>();
        // Add multiple-choice questions
        multipleChoiceQuestions.add(new MultipleChoiceQuestion(
                "What is Android?:",
                new String[]{"A mobile operating system.", "A type of mobile phone", "A programming language"},
                0 // Index of correct answer (A mobile operating system)
        ));

        multipleChoiceQuestions.add(new MultipleChoiceQuestion(
                "Which company developed Android?:",
                new String[]{" Apple", "Microsoft", "Google"},
                2 // Index of correct answer (c. google)
        ));

        multipleChoiceQuestions.add(new MultipleChoiceQuestion(
                "Which tool is used to develop Android applications?:",
                new String[]{"Android Studio", "Eclipse", " Xcode"},
                0 // Index of correct answer (a. Android studio)
        ));

        multipleChoiceQuestions.add(new MultipleChoiceQuestion(
                "What does APK stand for?:",
                new String[]{"Android Phone Kit", "Android Package Kit", "Application Programming Kit"},
                1 // Index of correct answer (b. EXP NO.1 and 2.)
        ));
    }

    // method to initialize t/f questions list
    private void initializeTrueFalseQuestionsList(){
        trueFalseQuestions = new ArrayList<>();

        // Add true/false questions
        trueFalseQuestions.add(new TrueFalseQuestion(
                "Android apps are primarily written in Kotlin.?",
                true // Correct answer is True
        ));

        trueFalseQuestions.add(new TrueFalseQuestion(
                "Android Studio is a light software?",
                false // Correct answer is False
        ));

        trueFalseQuestions.add(new TrueFalseQuestion(
                "Android is exclusively used for smartphones?",
                false // Correct answer is False
        ));

        trueFalseQuestions.add(new TrueFalseQuestion(
                "Android Studio is the official IDE for Android app development.?",
                true // Correct answer is True
        ));
    }

    // method to display the question and its options
    private void displayQuestion(int questionIndex, String quizMode) {
        // get question from the list according to quizMode
        Question question;
        if ("multiple_choice".equals(quizMode)) {
            question = multipleChoiceQuestions.get(questionIndex);
        } else {
            question = trueFalseQuestions.get(questionIndex);
        }

        // Set the question
        questionTextView.setText("Q"+ (questionIndex+1) +": "+question.getqText());

        optionsRadioGroup.removeAllViews(); // Clear previous options

        // Add options to RadioGroup
        String[] options = question.getqOptions();
        for (int i = 0; i < options.length; i++) {
            // create radiobutton with specific style (text color and size, margins, and so on)
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(View.generateViewId()); // Generate unique ID for each radio button
            radioButton.setText(options[i]);
            radioButton.setTextAppearance(R.style.MyRadioButtonStyle);
            radioButton.setBackgroundColor(getResources().getColor(R.color.yellow_50));
            radioButton.setWidth(600);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width
                    LinearLayout.LayoutParams.WRAP_CONTENT  // Height
            ); // set margins
            layoutParams.setMarginStart(100);
            layoutParams.setMargins(100, 0,0,30);
            radioButton.setLayoutParams(layoutParams);
            optionsRadioGroup.addView(radioButton);
        }

    }

    // get the user answer
    private String getUserChoice(int selectedRBtn) {
        RadioButton selectedRadioButton = findViewById(selectedRBtn);
        return selectedRadioButton.getText().toString().trim();
    }

    // set the user answer and score for the question
    private void setAnswerAndScore(String userAnswer, String quizMode){
        if ("multiple_choice".equals(quizMode)) {
            multipleChoiceQuestions.get(currentQuestionIndex).setUserAnswer(userAnswer);
            multipleChoiceQuestions.get(currentQuestionIndex).setUserScore();
        } else {
            trueFalseQuestions.get(currentQuestionIndex).setUserAnswer(userAnswer);
            trueFalseQuestions.get(currentQuestionIndex).setUserScore();
        }
    }

    // method to get all scores of the questions
    private int[] getAllScores(String quizMode) {
        int[] scores = new int[4]; // Assuming there are 4 questions only

        List<Question> questions;
        if ("multiple_choice".equals(quizMode)) {
            questions = multipleChoiceQuestions;
        } else {
            questions = trueFalseQuestions;
        }

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            scores[i] = question.getUserScore();
        }

        return scores;
    }


}