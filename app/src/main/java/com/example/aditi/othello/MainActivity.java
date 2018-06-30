package com.example.aditi.othello;

import android.content.Context;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int PLAYER_W = 1;
    public static final int PLAYER_B = 0;
    public static final int BLACK=0;
    public static final int WHITE=1;

    public static final int INCOMPLETE=1;
    public static final int PLAYER_W_WON=2;
    public static final int PLAYER_B_WON=3;
    public static final int DRAW=4;
    public int currentStatus;
    private int rowClicked;
    private int colClicked;
    int[] x = {1,-1,1,-1,1,-1,0,0};
    int[] y = {0,0,1,-1,-1,1,1,-1};
    LayoutInflater layoutInflater;
    TextView tv1,tv2;

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
        layoutInflater =(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        setUpBoard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.reset){
            setUpBoard();
        }
        return true;
    }

    public void setUpBoard()
    {
        currentStatus=INCOMPLETE;
        currentPlayer =PLAYER_B; //game starts with player_B
        rows = new ArrayList<>();
        board =new OthelloButton[size][size];
        rootLayout.removeAllViews();

        LinearLayout l = new LinearLayout(this);
        LinearLayout  l1 = new LinearLayout(this);
        LinearLayout l2 = new LinearLayout(this);
        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(0,170,1);

        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(0,170,1);

        l1.setBackground(getResources().getDrawable(R.drawable.ll_bg));
        l2.setBackground(getResources().getDrawable(R.drawable.ll_bg));
        l1.setLayoutParams(param1);
        l2.setLayoutParams(param2);

        ImageView img1 = new ImageView(this);
        img1.setImageDrawable(getResources().getDrawable(R.drawable.black));
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0,170,1);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0,140,1);
        img1.setLayoutParams(layoutParams1);
        tv1 = new TextView(this);
        layoutParams2.setMargins(0,20,0,0);
        tv1.setLayoutParams(layoutParams2);
        tv1.setText("2");
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, 100);
        l1.setOrientation(LinearLayout.HORIZONTAL);
        l1.addView(img1);
        l1.addView(tv1);

        ImageView img2 = new ImageView(this);
        img2.setImageDrawable(getResources().getDrawable(R.drawable.white));
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(0,170,1);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(0,140,1);
        img2.setLayoutParams(layoutParams3);
        tv2 = new TextView(this);
        layoutParams4.setMargins(0,20,0,0);
        tv2.setLayoutParams(layoutParams4);
        tv2.setText("2");
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 100);
        l2.setOrientation(LinearLayout.HORIZONTAL);
        l2.addView(img2);
        l2.addView(tv2);
        l.addView(l1);
        l.addView(l2);


        rootLayout.addView(l);


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
                button.setValue(-1);




            }
        }
        //initial board setup
        board[3][4].setPlayerValue(BLACK);
        board[3][3].setPlayerValue(WHITE);
        board[4][4].setPlayerValue(WHITE);
        board[4][3].setPlayerValue(BLACK);
        clearprevValidMoves();
        setValidMoves();

    }


    @Override
    public void onClick(View view) {
        if(currentStatus==INCOMPLETE) {

            OthelloButton button = (OthelloButton) view;
            if(currentPlayer == PLAYER_B) {
                button.setPlayerValue(BLACK);
            }
            else {
                button.setPlayerValue(WHITE);
            }
            rowClicked =button.getRow();
            colClicked=button.getCol();
            updateBoard(rowClicked,colClicked);
            updatecount();
            togglePlayer();
            clearprevValidMoves();
            setValidMoves();
            checkGameStatus();



        }
    }
    public void updatecount(){
        int count_B=0,count_W=0;
        for(int i=0;i<size;i++)
            {
                for(int j=0;j<size;j++){
                    if(board[i][j].getValue()==BLACK){
                        count_B++;
                    }
                    else if(board[i][j].getValue()==WHITE) {
                        count_W++;
                    }

                }
            }

            tv1.setText(String.valueOf(count_B));
            tv2.setText(String.valueOf(count_W));


        }

    public void clearprevValidMoves()
    {
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++){
                    OthelloButton btn = board[i][j];
                    if (btn.is_valid_move && !btn.reveal) //if it was a valid move and not revealed yet
                    {
                        btn.is_valid_move = false; //make it invalid
                        btn.setBackground(getResources().getDrawable(R.drawable.bg));
                        btn.setEnabled(false);
                        for (int k = 0; k < 8; k++) {
                            btn.valid_direction[k] = 0; //re-initialize the valid_direction array
                        }


                }
            }
        }
    }

    public void updateBoard(int row,int col) {
        OthelloButton btn = board[row][col]; //get the clicked button
        //check in it's all the neighbours
        for (int i = 0; i < 8; i++) {

            if (btn.valid_direction[i] == 1) { //if the direction is valid

                int a = row + x[i];
                int b = col + y[i];
                if (a >= 0 && b >= 0 && a < size && b < size && board[a][b].reveal) {
                    int c = a;
                    int d = b;
                    if (currentPlayer == PLAYER_B) {
                        while (c >= 0 && d >= 0 && c < size && d < size && board[c][d].reveal && board[c][d].getValue() == WHITE) {
                            //change the value of only white buttons i.e flip it
                            board[c][d].setPlayerValue(BLACK);

                            c = c + x[i];
                            d = d + y[i];

                        }

                    }
                    if (currentPlayer == PLAYER_W) {
                        while (c >= 0 && d >= 0 && c < size && d < size && board[c][d].reveal && board[c][d].getValue() == BLACK) {
                            //change the value of only black buttons i.e flip it
                            board[c][d].setPlayerValue(WHITE);

                            c = c + x[i];
                            d = d + y[i];


                        }

                    }
                }
            }

        }
    }
    public void setValidMoves()
    {
        int flag1,flag2;

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                OthelloButton btn = board[i][j];
                if(currentPlayer==PLAYER_B) {
                    if (btn.reveal  && btn.getValue()==WHITE) { //if the button is revealed and it's white

                        for (int k = 0; k < 8; k++) {

                            int a = i + x[k];
                            int b = j + y[k];
                            if (a >= 0 && a < size && b >= 0 && b < size && !board[a][b].reveal) { //go to it's blank neighbours
                                OthelloButton adj = board[a][b]; //adjacent blank neighbour

                                    for (int l = 0; l < 8; l++) {
                                        flag1=0;
                                        flag2=0;
                                        int c = a + x[l];
                                        int d = b + y[l];
                                        while (c >= 0 && d >= 0 && c < size && d < size && board[c][d].reveal && board[c][d].getValue()==WHITE) {

                                            c += x[l];
                                            d += y[l];
                                            flag2=1;

                                        }
                                        if (c >= 0 && d >= 0 && c < size && flag2==1 && d < size && board[c][d].reveal && board[c][d].getValue() == BLACK) {
                                            flag1 = 1;
                                        }

                                        if (flag1 == 1) {

                                                adj.is_valid_move = true;
                                                adj.setEnabled(true);
                                                adj.setBackground(getResources().getDrawable(R.drawable.green_dark_valid));
                                                adj.valid_direction[l] = 1;
                                        }

                                }
                            }
                        }

                    }
                }

               else if(currentPlayer==PLAYER_W) {
                    if (btn.reveal  && btn.getValue()==BLACK) { //if the button is revealed and it's black

                        for (int k = 0; k < 8; k++) {
                            int a = i + x[k];
                            int b = j + y[k];
                            if (a >= 0 && a < size && b >= 0 && b < size && !board[a][b].reveal) { //go to it's blank neighbours
                                OthelloButton adj = board[a][b];

                                for (int l = 0; l < 8; l++) {

                                        int c = a + x[l];
                                        int d = b + y[l];
                                        flag1=0;
                                        flag2=0;
                                        while (c >= 0 && d >= 0 && c < size && d < size && board[c][d].reveal && board[c][d].getValue()==BLACK) {

                                            c += x[l];
                                            d += y[l];
                                            flag2=1;

                                        }
                                        if (c >= 0 && d >= 0 && c < size && d <size && flag2==1 &&board[c][d].reveal && board[c][d].getValue() == WHITE) {
                                            flag1 = 1;
                                        }

                                        if (flag1 == 1  ) {

                                                adj.is_valid_move = true;
                                                adj.setEnabled(true);
                                                adj.setBackground(getResources().getDrawable(R.drawable.green_dark_valid));
                                                adj.valid_direction[l] = 1;
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
                    if(!board[i][j].reveal && board[i][j].is_valid_move) //if all the buttons are not revealed and there exists a valid move
                        return;
            }
        }

        //Won
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){

                if(board[i][j].getValue()==BLACK)
                    count_B++;
                else if(board[i][j].getValue()==WHITE)
                    count_W++;


            }
        }


            if (count_B > count_W) {
                currentStatus = PLAYER_B_WON;
            } else if (count_W > count_B) {
                currentStatus = PLAYER_W_WON;

            } else if (count_B == count_W) {
                currentStatus = DRAW;

                }
            final View output = layoutInflater.inflate(R.layout.alert_layout, null, false);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            TextView blk = output.findViewById(R.id.blackScore);
            TextView white = output.findViewById(R.id.whiteScore);
            TextView status = output.findViewById(R.id.status);
            blk.setText(String.valueOf(count_B));
            white.setText(String.valueOf(count_W));
            if (currentStatus == PLAYER_B_WON) {
                status.setText("BLACK PLAYER WON");
            } else if (currentStatus == PLAYER_W_WON)
                status.setText("WHITE PLAYER WON");
            else
                status.setText("DRAW");

            builder.setView(output);
            AlertDialog dialog = builder.create(); //show the dialog
            dialog.show();

        }
    }


