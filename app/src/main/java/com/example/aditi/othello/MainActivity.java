package com.example.aditi.othello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int PLAYER_W = 1;
    public static final int PLAYER_B = 0;
    public static final int NO_PLAYER = -1;

    public static final int INCOMPLETE=1;
    public static final int PLAYER_W_WON=2;
    public static final int PLAYER_B_WON=3;
    public static final int DRAW=4;
    public int currentStatus;

    LinearLayout rootLayout;
    private int size=8;
    private ArrayList<LinearLayout> rows ;
    private OthelloButton[][] board;
    public int currentPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout =findViewById(R.id.linearLayout);

        setUpBoard();
    }
    public void setUpBoard()
    {
        currentStatus=INCOMPLETE;
        currentPlayer =PLAYER_B;
        rows = new ArrayList<>();
        board =new OthelloButton[size][size];
        rootLayout.removeAllViews();
        for(int i=0;i<size;i++)
        {

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL); //though by default it's horizontal
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);

            linearLayout.setLayoutParams(layoutParams);
            rows.add(linearLayout);
            rootLayout.addView(linearLayout);
        }

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                OthelloButton button = new OthelloButton(this);
                LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
                button.setLayoutParams(layoutParams);
                button.setOnClickListener(this);
                button.setBackground(getResources().getDrawable(R.drawable.bg));
                LinearLayout row = rows.get(i);
                row.addView(button); //add button in ith row
                board[i][j]= button;




            }
        }

        board[3][3].setPlayer(PLAYER_B);
        board[3][4].setPlayer(PLAYER_W);
        board[4][3].setPlayer(PLAYER_W);
        board[4][4].setPlayer(PLAYER_B);

    }


    @Override
    public void onClick(View view) {
        if(currentStatus==INCOMPLETE) {
            OthelloButton button = (OthelloButton) view;
            button.setPlayer(currentPlayer);
            updateBoard();
            checkGameStatus();
            togglePlayer();
        }
    }

    public void updateBoard(){

    }
    public void togglePlayer(){
        if(currentPlayer == PLAYER_B)
            currentPlayer =PLAYER_W;
        else
            currentPlayer =PLAYER_B;
    }
    public void checkGameStatus(){

    }
}
