/**
 *  Samuel Ng
 *  112330868
 *  CSE 390
 *  Part 1: High Low Game
 *  Emulator: Pixel 2 API 29
 *  Extra Features:
 *      Added number of tries it takes to correctly guess the number
 *      Limited number of guesses user has
 *      Option to play again
 */

package com.example.highlowgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int max, random_num, num_tries, max_tries, tries_left;
    private TextView numTriesTextView, triesLeftTextView, feedback;
    private Resources res;
    String numTriesString, triesLeftString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGuessButton();

        max = 100;
        max_tries = 10;
        tries_left = max_tries;

        TextView instructions = findViewById(R.id.instructions);
        String instructionsString = getString(R.string.instructions, max);
        instructions.setText(instructionsString);

        numTriesTextView = findViewById(R.id.numTries);
        triesLeftTextView = findViewById(R.id.tries_left);
        res = getResources();
        triesLeftString = res.getQuantityString(R.plurals.tries_left, tries_left, tries_left);
        triesLeftTextView.setText(triesLeftString);

        feedback = findViewById(R.id.guessFeedback);
        random_num = generateRandomNumber(max+1);
    }

    private void initGuessButton() {
        Button guessButton = findViewById(R.id.guessButton);
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button guessButtonRef = findViewById(R.id.guessButton);
                if (guessButtonRef.getText().toString().toUpperCase().equals("PLAY AGAIN")) {
                    reset_state();
                    return;
                }
                EditText userGuess = findViewById(R.id.userGuess);
                String guess = userGuess.getText().toString().trim();
                String feedbackString = getString(R.string.invalid_guess, max);
                feedback.setTextColor(0xFFFF0000);
                if (guess.equals("")) {
                    feedback.setText(feedbackString);
                    return;
                }
                if (Integer.parseInt(guess) > max) {
                    feedback.setText(feedbackString);
                    return;
                }
                num_tries++;
                tries_left--;

                numTriesString = getString(R.string.num_tries, num_tries);
                numTriesTextView.setText(numTriesString);

                triesLeftString = res.getQuantityString(R.plurals.tries_left, tries_left, tries_left);
                triesLeftTextView.setText(triesLeftString);
                if (Integer.parseInt(guess) == random_num) {
                    feedback.setTextColor(0xFF0000FF);
                    feedback.setText(R.string.correct_guess);
                    guessButtonRef.setText(R.string.play_again_btn);
                }
                else if (tries_left == 0) {
                    feedback.setText(R.string.loss);
                    guessButtonRef.setText(R.string.play_again_btn);
                }
                else if (Integer.parseInt(guess) > random_num) {
                    feedback.setText(R.string.high_guess);
                }
                else {
                    feedback.setText(R.string.low_guess);
                }
            }
        });
    }

    /**
     * Resets state for playing again
     */
    private void reset_state() {
        tries_left = max_tries;
        num_tries = 0;
        Button guessButton = findViewById(R.id.guessButton);
        guessButton.setText(R.string.guess_btn);

        numTriesString = getString(R.string.num_tries, num_tries);
        numTriesTextView.setText(numTriesString);

        triesLeftTextView = findViewById(R.id.tries_left);
        res = getResources();
        triesLeftString = res.getQuantityString(R.plurals.tries_left, tries_left, tries_left);
        triesLeftTextView.setText(triesLeftString);

        feedback.setText("");
        random_num = generateRandomNumber(max+1);
    }

    /**
     * Helper method to generate random number
     * @param max max number
     * @return random number between 0 and max
     */
    public int generateRandomNumber(int max) {
        Random rand = new Random();
        return rand.nextInt(max);
    }
}