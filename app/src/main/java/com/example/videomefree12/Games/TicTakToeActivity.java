package com.example.videomefree12.Games;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.videomefree12.R;


public class TicTakToeActivity extends AppCompatActivity {


    //0:cross,1:circle:2:empty
                    // 0  1  2  3  4  5  6  7  8
    int[] gamestate = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningpositions = {
            {0, 1, 2}, //
            {3, 4, 5}, //
            {6, 7, 8}, //
            {0, 4, 8}, //
            {2, 4, 6}, //
            {0, 3, 6}, //
            {1, 4, 7}, //
            {2, 5, 8}//
    };
    int activeplayer = 0;
    boolean gameactive = true;


    public void dropin(View view) {

        ImageView counter = (ImageView) view;

        int tappedcounter = Integer.parseInt(counter.getTag().toString());
        if (gamestate[tappedcounter] == 2 && gameactive) {


            gamestate[tappedcounter] = activeplayer;
            counter.setTranslationY(-1500);

            if (activeplayer == 0) {
                counter.setImageResource(R.drawable.cross);
                activeplayer = 1;
            } else {
                counter.setImageResource(R.drawable.circle);
                activeplayer = 0;

            }

            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);
            for (int[] winningposition : winningpositions) {

                if (
                        gamestate[winningposition[0]] == gamestate[winningposition[1]] &&
                                gamestate[winningposition[1]] == gamestate[winningposition[2]] &&
                                gamestate[winningposition[0]] != 2) {
                    gameactive = false;

                    String winner = gamestate[winningposition[0]]==0?"cross":"circle";
                    textMessageView.setText(winner + " has won :)");

                    playAgainButton.setVisibility(View.VISIBLE);
                    textMessageView.setVisibility(View.VISIBLE);
                }




            }
            if(gameactive){
                int filledPlaces = 0;
                for(int val : gamestate){
                    if(val != 2){
                       filledPlaces++;
                    }
                }
                if(filledPlaces == gamestate.length){
                    //draw condition
                        textMessageView.setText("game is tie :(" );
                    gameactive=false;
                    playAgainButton.setVisibility(View.VISIBLE);
                    textMessageView.setVisibility(View.VISIBLE);
                    return;
                }
            }
        }
    }
    public void playagain(View view){

        Button button =  findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView);
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        GridLayout gridLayout =  findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
        for (int i = 0; i < gamestate.length; i++) {
            gamestate[i] = 2;
        }

        activeplayer = 0;
        gameactive = true;
    }
    Button playAgainButton;
    TextView textMessageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictaktoe);
        playAgainButton = findViewById(R.id.button);
         textMessageView =  findViewById(R.id.textView);

    }
}