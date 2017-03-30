package com.example.wautel_l.reversi;

/**
 * Created by wautel_l on 29/03/2017.
 */

public class GameLogic {
    private int[][] board;
    private int current_player;

    public GameLogic()
    {

        board = new int[8][8];
        int inc;
        for (int i = 0; i < 8; i+=1)
        {
            for (inc = 0; i < 8; i += 1)
            {
                board[i][inc] = 0;
            }
        }
    }

}
