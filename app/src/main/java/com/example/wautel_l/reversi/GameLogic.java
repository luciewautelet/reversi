package com.example.wautel_l.reversi;

import android.test.ProviderTestCase2;
import android.util.Log;

/**
 * Created by wautel_l on 29/03/2017.
 */

public class GameLogic {
    private int[][] board;
    private int current_player;
    protected MainActivity act;
    protected CustomView mview;

    public  GameLogic(CustomView view)
    {
         mview = view;
        board = new int[8][8];
        int inc;
        for (int i = 0; i < 8; i+=1)
        {
            for (inc = 0; i < 8; i += 1)
            {
                board[i][inc] = 0;
            }
        }
        board[3][3] = 1;
        board[4][4] = 1;
        board[4][3] = 2;
        board[3][4] = 2;
        current_player = 1;
    }

    protected void setAct(MainActivity activity)
    {
        act = activity;
    }

    protected int  setnextTurn()
    {
        if (current_player == 1)
            current_player = 2;
        else
            current_player =1;
        return current_player;
    }

    protected int get_turn(){
        return current_player;
    }

    protected boolean check_allowed(int x, int y)
    {
        if (board[x][y] == 0 && check_tandb(1, x, y) == false && check_tandb(-1, x, y) == false) {
            if (check_side(1, x, y) == false && check_side(-1, x, y) == false)
                return check_diag(x, y);
            else
                return true;

            }
        else {
            board[x][y] = current_player;
            return true;
        }
    }

    protected boolean check_diag(int x, int y)
    {
        //first check top left  and bottom right
        if (check_diag_int(-1, -1, x, y) == false && check_diag_int(1, 1, x, y) == false)
        {
            if (check_diag_int(-1, 1, x, y) == false && check_diag_int(1, -1, x, y) == false)
                return false;
            else {
                board[x][y] = current_player;
                return true;
            }
        }
        else {
            board[x][y] = current_player;
            return true;
        }

}
    protected  boolean check_diag_int(int ind, int ind2, int x, int y)
    {
        boolean opposite = false;
        while (x > 0 && x < 8 && y < 8 && y > 0)
        {
            if (board[x][y] != current_player && board[x][y] != 0)
                opposite = true;
            if (board[x][y] == current_player)
                return opposite;
            x = x + ind;
            y = y + ind2;
        }
        return false;
    }

    protected boolean check_tandb(int ind, int x, int y)
    {
        boolean opposite  = false;
        int tmp = y;
        while (tmp > 0 &&  tmp < 8)
        {
            if (board[x][tmp] != current_player && board[x][tmp] != 0)
                opposite = true;
            if (board[x][tmp] == current_player)
                return opposite;
            tmp = tmp + ind;
        }
        return  false;
    }

    protected boolean check_side(int ind, int x, int y)
    {
        boolean opposite = false;
        int tmp = x;
        while (tmp > 0 && tmp < 8)
        {
            if (board[tmp][y] != current_player && board[tmp][y] != 0)
                opposite = true;
            if (board[tmp][y] == current_player)
                return opposite;
            tmp = tmp + ind;
        }
        return false;
    }

    protected void set_all_case(int x, int y)
    {
        Log.e("y : ", y + "");
        Log.e("x : ", x + "");
        set_bant(1, x, y);
        set_bant(-1, x, y);
        set_side(1, x,y);
        set_side(-1,x , y);
    }

    protected void set_side(int ind, int x, int y)
    {
        boolean opposite = false;
        int tmp = x + ind;
        while (tmp > 0 && tmp < 8)
        {
            if (board[tmp][y] != current_player && board[tmp][y] != 0)
            {
                if (opposite == false)
                {
                    board[tmp][y] =current_player;
                    mview.draw_case(tmp, y, current_player);
                    set_bant(1, tmp, y);
                    set_bant(-1, tmp, y);
                }
            }
            if (board[tmp][y] == current_player)
                return;
            tmp = tmp + ind;
        }
    }

    protected void set_bant(int ind, int x, int y)
    {
        boolean opposite = false;
        int tmp = y + ind;
        while (tmp > 0 && tmp < 8)
        {
            if (board[x][tmp] != current_player && board[x][tmp] != 0)
            {
                if (opposite == false) {
                    board[x][tmp] = current_player;
                    mview.draw_case(x, tmp, current_player);
                    set_side(1, x, tmp);
                    set_side(-1, x, tmp);
                }
            }
            if (board[x][tmp] == current_player)
                return;
            tmp = tmp + ind;
        }
        return;
    }

}

