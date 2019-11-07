package com.anurut.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.anurut.tictactoe.R.string.resetButtonClear;
import static com.anurut.tictactoe.R.string.resetButtonDefault;

public class MainActivity extends AppCompatActivity {

    // Player option
    // 0 - x
    // 1 - O

    int activePlayer = 0;
    boolean gameActive = true;
    int count = 0;
    public int x_win_count = 0;
    public int o_win_count = 0;
    int reset = 0;


    // Game State
    // 0 - x
    // 1 - O
    // 2 - Null

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningCombination = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playerTap(View view) {

        ImageView tappedImage = (ImageView) view;
        TextView currentPlayer = findViewById(R.id.statusBar);
        int tappedSection = Integer.parseInt(tappedImage.getTag().toString());

        tappedImage.setTranslationY(-1000f);


        if(!gameActive){
            gameReset();
        }else {
            setResetButton("reset");

            if (gameState[tappedSection] == 2) {
                gameState[tappedSection] = activePlayer;
                if (activePlayer == 0) {
                    tappedImage.setImageResource(R.drawable.x);
                    activePlayer = 1;
                    count++;
                    currentPlayer.setText(R.string.o_turn);
                } else {
                    tappedImage.setImageResource(R.drawable.o);
                    activePlayer = 0;
                    currentPlayer.setText(R.string.x_turn);
                    count++;
                }
            }
        }

        tappedImage.animate().translationYBy(1000f);

        // Check if any player has won
        String winningPlayer = checkIfPlayerWon();
        if(!winningPlayer.isEmpty()){
            currentPlayer.setText(winningPlayer);
        }
    }

    public String checkIfPlayerWon() {

        TextView xScore = findViewById(R.id.x_score);
        TextView oScore = findViewById(R.id.o_score);
        String winner = "";
        for (int[] winPosition : winningCombination)
            if (gameState[winPosition[0]] == gameState[winPosition[1]]
                    && gameState[winPosition[1]] == gameState[winPosition[2]]
                    && gameState[winPosition[0]] != 2) {
                // Somebody has won. Find put who!
                if (gameState[winPosition[0]] == 0) {
                    winner = "X has won";
                    x_win_count++;
                    xScore.setText("X's Score : " + x_win_count);
                    gameActive = false;
                    break;
                } else {
                    winner = "O has won";
                    o_win_count++;
                    oScore.setText("O's Score : " + o_win_count);
                    gameActive = false;
                    break;
                }
            }

        if(winner.isEmpty() && count == 9){
            gameActive = false;
            return "It's a tie";
        }
        return winner;
    }

    public void resetButtonClick(View view){

        TextView xScore = findViewById(R.id.x_score);
        TextView oScore = findViewById(R.id.o_score);
        if(reset == 0){
            gameReset();
            setResetButton("Clear");
        } else {
            gameReset();
            x_win_count = 0;
            o_win_count = 0;
            xScore.setText(R.string.x_score_default);
            oScore.setText(R.string.o_score_default);
            setResetButton("Reset");
        }
    }

    public void setResetButton(String value){
        Button resetButton = findViewById(R.id.resetButton);
        switch (value.toLowerCase())
        {
            case "clear":
                reset = 1;
                resetButton.setText(resetButtonClear);
                resetButton.setBackgroundColor(getResources().getColor(R.color.resetButtonClear));
                break;
            case "reset":
                reset = 0;
                resetButton.setText(resetButtonDefault);
                resetButton.setBackgroundColor(getResources().getColor(R.color.resetButtonDefault));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value.toLowerCase());
        }
    }

    public void gameReset(){

        gameActive = true;
        activePlayer = 0;
        count = 0;

        for(int i=0; i<gameState.length; i++){
            gameState[i] = 2;
        }

        ((ImageView)findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);

        TextView currentPlayer = findViewById(R.id.statusBar);
        currentPlayer.setText(R.string.x_turn);
    }
}