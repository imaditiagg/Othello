package com.example.aditi.othello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    private int rowClicked;
    private int colClicked;
    int[] x = {1,-1,1,-1,1,-1,0,0};
    int[] y = {0,0,1,-1,-1,1,1,-1};

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
                button.setRow(i);
                button.setCol(j);
                button.setEnabled(false);




            }
        }
        //initial board setup
        board[3][4].setPlayerValue(PLAYER_B,0);
        board[3][3].setPlayerValue(PLAYER_W,1);
        board[4][4].setPlayerValue(PLAYER_W,1);
        board[4][3].setPlayerValue(PLAYER_B,0);
        setValidMoves();

    }


    @Override
    public void onClick(View view) {
        if(currentStatus==INCOMPLETE) {
            clearprevValidMoves();
            OthelloButton button = (OthelloButton) view;
            if(currentPlayer == PLAYER_B) {
                button.setPlayerValue(currentPlayer, 0);
            }
            else {
                button.setPlayerValue(currentPlayer, 1);
            }
            rowClicked =button.getRow();
            colClicked=button.getCol();
            updateBoard(rowClicked,colClicked);

            togglePlayer();
            setValidMoves();
            checkGameStatus();


        }
    }
    public void clearprevValidMoves()
    {
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++){
                if(board[i][j].is_valid_move==true)
                {
                    board[i][j].is_valid_move=false;
                    board[i][j].setBackground(getResources().getDrawable(R.drawable.bg));
                }
            }
        }
    }

    public void updateBoard(int row,int col){

        for(int i=0;i<8;i++){
            int a = row+x[i];
            int b=col+y[i];

            if(a>=0&& b>=0 && a<size && b<size && board[a][b].reveal==true){
                int c=a;
                int d=b;
                if(currentPlayer==PLAYER_B) {
                    while (c >= 0 && d >= 0 && c < size && d < size && board[c][d].reveal == true && board[c][d].getValue() == 1) {
                        //change the value i.e flip it
                       // board[c][d].setPlayerValue(currentPlayer, 0);

                            c = a + x[i];
                            d = b + y[i];

                            }

                }
                if(currentPlayer==PLAYER_W) {
                    while (c >= 0 && d >= 0 && c < size && d < size &&board[c][d].reveal == true && board[c][d].getValue() == 0) {
                        //change the value i.e flip it
                        board[c][d].setPlayerValue(currentPlayer, 1);

                            c = a + x[i];
                            d = b + y[i];



                    }

                }
            }
        }

    }
    public void setValidMoves()
    {

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                OthelloButton btn = board[i][j];
                if(currentPlayer==PLAYER_B) {
                    if (btn.reveal == true && btn.getValue()==1) { //if the button is revealed and it's white

                        for (int k = 0; k < 8; k++) {
                            int flag1=0;
                            int a = i + x[k];
                            int b = j + y[k];
                            if (a >= 0 && a < size && b >= 0 && b < size && !board[a][b].reveal) {
                                OthelloButton adj = board[a][b];

                                    for (int l = 0; l < 8; l++) {
                                        if(flag1==0) {
                                        int c = a + x[l];
                                        int d = b + y[l];
                                        int flag2=0;
                                        while (c >= 0 && d >= 0 && c < size && d < size && board[c][d].reveal == true && board[c][d].getValue()==1) {

                                            c += x[l];
                                            d += y[l];
                                            flag2=1;

                                        }
                                        if (c >= 0 && d >= 0 && c < size && d < size && flag2==1 && board[c][d].reveal==true && board[c][d].getValue() == 0) {
                                            flag1 = 1;
                                        }

                                        if (flag1 == 1) {
                                            adj.is_valid_move = true;
                                            adj.setEnabled(true);
                                            adj.setBackground(getResources().getDrawable(R.drawable.green_dark_valid));
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

                if(currentPlayer==PLAYER_W) {
                    if (btn.reveal == true && btn.getValue()==0) { //if the button is revealed and it's black

                        for (int k = 0; k < 8; k++) {
                            int flag1=0;
                            int a = i + x[k];
                            int b = j + y[k];
                            if (a >= 0 && a < size && b >= 0 && b < size && !board[a][b].reveal) {
                                OthelloButton adj = board[a][b];

                                for (int l = 0; l < 8; l++) {
                                    if(flag1==0) {
                                        int c = a + x[l];
                                        int d = b + y[l];
                                        int flag2=0;
                                        while (c >= 0 && d >= 0 && c < size && d < size && board[c][d].reveal == true && board[c][d].getValue()==0) {

                                            c += x[l];
                                            d += y[l];
                                            flag2=1;

                                        }
                                        if (c >= 0 && d >= 0 && c < size && d <size && flag2==1 && board[c][d].reveal==true && board[c][d].getValue() == 1) {
                                            flag1 = 1;
                                        }

                                        if (flag1 == 1) {
                                            adj.is_valid_move = true;
                                            adj.setEnabled(true);
                                            adj.setBackground(getResources().getDrawable(R.drawable.green_dark_valid));
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
    public void togglePlayer(){
        if(currentPlayer == PLAYER_B)
            currentPlayer =PLAYER_W;
        else
            currentPlayer =PLAYER_B;
    }
    public void checkGameStatus(){
        int count_B=0,count_W=0;
        for(int i=0;i<size;i++) {
            for (int j = 0; j < size; j++) {
                    if(board[i][j].reveal==false)
                        return;
            }
        }

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){

                if(board[i][j].getValue()==0)
                    count_B++;
                else
                    count_W++;


            }
        }
        if(count_B>count_W){
            currentStatus=PLAYER_B_WON;
            Toast.makeText(this,"PLAYER_Black Won",Toast.LENGTH_LONG).show();
            return;
        }
        else if(count_W>count_B){
            currentStatus=PLAYER_W_WON;
            Toast.makeText(this,"PLAYER_White Won",Toast.LENGTH_LONG).show();
            return;
        }
        else if(count_B==count_W){
            currentStatus=DRAW;
            Toast.makeText(this,"DRAW",Toast.LENGTH_LONG).show();
            return;

        }

    }

}
