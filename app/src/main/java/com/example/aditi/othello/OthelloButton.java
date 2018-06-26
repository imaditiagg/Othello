package com.example.aditi.othello;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

public class OthelloButton extends AppCompatButton {
    private  int player;

    public OthelloButton(Context context) {
        super(context);
    }

    public void setPlayer(int player)
    {
        this.player =player;
        if(player == MainActivity.PLAYER_B) {
            setBackground(getResources().getDrawable(R.drawable.black_bg));

        }
        else if(player == MainActivity.PLAYER_W) {
            setBackground(getResources().getDrawable(R.drawable.white_bg));

        }
        setEnabled(false);
    }

    public int  getPlayer()
    {
        return this.player;
    }
    public boolean isEmpty()
    {
        if(this.player == MainActivity.NO_PLAYER)
            return true;
        else
            return false;

    }

}
