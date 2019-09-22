package com.example.tictacdoh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                String buttonID = "button_" + i + j;
                int resID= getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);


            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (((Button) v).getText() == "") {

            if (player1Turn) {
                ((Button) v).setText("x");
                ((Button) v).setBackgroundResource(R.drawable.amazon);
            } else {
                ((Button) v).setText("o");
                ((Button) v).setBackgroundResource(R.drawable.netflix);
            }

            roundCount++;
            if (checkForWin()) {
                if (player1Turn) {
                    player1Wins();
                } else {
                    player2Wins();
                }
            } else if (roundCount == 9) {
                draw();
            } else {
                player1Turn = !player1Turn;

            }
        }
    }
    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }
    private void player1Wins(){
        player1Points++;
        Toast.makeText(this, "Amazon Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();


    }
    private void player2Wins(){
        player2Points++;
        Toast.makeText(this, "Netflix Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }
    private void draw(){
        Toast.makeText(this,"Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void updatePointsText(){
        textViewPlayer1.setText("Amazon: " + player1Points);
        textViewPlayer2.setText("Netflix: " + player2Points);

    }
    private void resetBoard(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearImages();
            }
        }, 2000);

    }
    private void clearImages(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setBackgroundResource(R.drawable.button);

            }
        }
    }
    public void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }
    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);

        roundCount = saveInstanceState.getInt("roundCount");
        player1Points = saveInstanceState.getInt("player1Points");
        player2Points = saveInstanceState.getInt("player2Points");
        player1Turn = saveInstanceState.getBoolean("player1Turn");
    }
}
