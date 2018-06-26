package com.example.aditi.othello;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

public class OthelloButton extends AppCompatButton {
    private int row;
    private int col;
    private int value;
    public boolean is_valid_move=false;
    public boolean reveal =false;
    public int[] valid_direction = new int[8];

    public OthelloButton(Context context) {
        super(context);
    }

    public void setPlayerValue(int player,int value)
    {
        this.reveal=true;
        this.value =value;
        if(value==0) {
            setBackground(getResources().getDrawable(R.drawable.black_bg)); //black

        }
        else if(value==1) {
            setBackground(getResources().getDrawable(R.drawable.white_bg));//white

        }
        this.setEnabled(false);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getValue() {
        return value;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
