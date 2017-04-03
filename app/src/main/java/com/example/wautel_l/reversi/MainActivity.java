package com.example.wautel_l.reversi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView black_piece;
    private TextView blue_piece;
    private TextView turn;
    private GameLogic gl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        black_piece = (TextView) findViewById(R.id.black_tv);
        blue_piece = (TextView) findViewById(R.id.bluetv);
        turn = (TextView) findViewById(R.id.turntv);

        set_text(1, 2);
        set_text(2, 2);
        set_text(3, 1);

        gl = new GameLogic(this);

    }

    protected void set_text(int ind, int text)
    {
        if (ind == 1)
        {
            black_piece.setText("black pece : " + text);
        }
        else if (ind == 2)
        {
            blue_piece.setText("blue piece : " + text);
        }
        else if (ind == 3)
        {
            if (text == 1)
                turn.setText("turn : black");
            else if (text == 2)
                turn.setText("turn : blue");
        }
    }




}
