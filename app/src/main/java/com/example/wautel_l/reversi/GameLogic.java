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
    private int count[];

    public  GameLogic(CustomView view)
    {
         mview = view;
        count = new int[2];
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


    protected void init_game()
    {
        int inc;
        for (int i = 0; i < 8; i+=1)
        {
            for (inc = 0; inc < 8; inc += 1)
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

    private void count_init()
    {
        count[0] = 0;
        count[1] = 0;
        int j;
        for (int i = 0; i < 8; i += 1) {
            for (j = 0; j < 8; j += 1) {
                if (board[i][j] != 0) count[board[i][j] - 1] += 1;
            }
        }
    }

    private boolean verif_win()
    {

        count_init();
        boolean left = false;
        int j;
        for (int i = 0; i < 8; i += 1) {
            for (j = 0; j < 8; j += 1) {
                if (board[i][j] == 0) left = true;
            }}

        return(count[0] == 0 || count[1] == 0 || !left);
    }

     boolean check_move() {
        int j;
        boolean dispo = false;

        if (!verif_win()) {
            for (int i = 0; i < 8; i += 1) {
                for (j = 0; j < 8; j += 1) {
                    if (board[i][j] == 0) {
                        if (!check_tandb(1, i, j) && !check_tandb(-1, i, j)) {
                            if (!check_side(1, i, j) && !check_side(-1, i, j)) {
                                if (check_diag(i, j)) {
                                    mview.draw_case(i, j, 3);
                                    dispo = true;
                                }
                            } else {
                                mview.draw_case(i, j, 3);
                                dispo = true;
                            }
                        } else {
                            dispo = true;
                            mview.draw_case(i, j, 3);
                        }

                        }
                    }
                }
            return dispo;
            }
        else
            return false;
    }



    protected boolean check_diag(int x, int y)
    {
        //first check top left  and bottom right
        if (!check_diag_int(-1, -1, x, y)&& !check_diag_int(1, 1, x, y))
        {
            if (!check_diag_int(-1, 1, x, y)&& !check_diag_int(1, -1, x, y))
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
        if((x + ind) >= 0 && (x + ind) < 8 &&(y + ind2) >= 0 && (y + ind2) < 8  &&  board[x + ind][y + ind2] == 0)
            return false;
        x = x + ind;
        y = y + ind2;
        while (x >= 0 && x < 8 && y < 8 && y >= 0)
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
        if((tmp + ind) >= 0 && (tmp + ind) < 8  && board[x][tmp + ind] == 0) {
            return false;
        }
        tmp = tmp + ind;
        while (tmp >= 0 &&  tmp < 8)
        {
            if (board[x][tmp] != current_player && board[x][tmp] != 0)
                opposite = true;
            if (board[x][tmp] == current_player) {
                return opposite;
            }
            tmp = tmp + ind;
        }
        return  false;
    }

    protected boolean check_side(int ind, int x, int y)
    {

        boolean opposite = false;
        int tmp = x + ind;

        if(tmp >= 0 && tmp  < 8 && board[tmp][y] == 0) return false;

        while (tmp >= 0 && tmp < 8)
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
        board[x][y] = current_player;

        if (check_tandb(1, x, y))
            set_bant(1, x, y);
        if (check_tandb(-1, x, y))
            set_bant(-1, x, y);
        if (check_side(1, x, y))
            set_side(1, x,y);
        if (check_side(-1, x, y))
            set_side(-1,x , y);
        if (check_diag_int(1, -1, x, y))
            set_diag(1, -1, x, y);
        if (check_diag_int(-1, -1, x, y))
            set_diag(-1, -1, x, y);
        if (check_diag_int(-1, 1, x, y))
            set_diag(-1, 1, x, y);
        if (check_diag_int(1, 1, x, y))
            set_diag(1, 1, x, y);
        count_init();
        act.set_text(1, count[0]);
        act.set_text(2, count[1]);
    }

    protected void set_diag(int ind, int ind2, int x, int y)
    {
        x =x + ind;
        y = y + ind2;
        while (x >= 0 && x < 8 && y < 8 && y >= 0)
        {

            if (board[x][y] != current_player && board[x][y] != 0) {
                count[current_player - 1] += 1;
                board[x][y] = current_player;
                mview.draw_case(x, y, current_player);
            }
            if (board[x][y] == current_player)
                return;
            x = x + ind;
            y = y + ind2;
        }
    }

    protected void set_side(int ind, int x, int y)
    {
        int tmp = x + ind;
        while (tmp >= 0 && tmp < 8)
        {
            if (board[tmp][y] != current_player && board[tmp][y] != 0)
            {
                count[current_player - 1] += 1;
                    board[tmp][y] =current_player;
                    mview.draw_case(tmp, y, current_player);
            }
            if (board[tmp][y] == current_player)
                return;
            tmp = tmp + ind;
        }
    }

    protected void set_bant(int ind, int x, int y)
    {
        int tmp = y + ind;
        while (tmp >= 0 && tmp < 8)
        {
            if (board[x][tmp] != current_player && board[x][tmp] != 0)
            {
                    count[current_player - 1] += 1;
                    board[x][tmp] = current_player;
                    mview.draw_case(x, tmp, current_player);
                }
            if (board[x][tmp] == current_player)
                return;
            tmp = tmp + ind;
        }
    }

}

