package com.example.wautel_l.reversi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.media.MediaActionSound;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.R.color.white;

/**
 * Created by wautel_l on 28/03/2017.
 */

public class CustomView extends View {

    private ShapeDrawable[][] square;
    private ShapeDrawable[][] pions;
    private int width;
    private int height;
    private int inc;
    protected GameLogic gl;
    private MainActivity context;
    private int i;
    private int turn;

    public CustomView(Context context)
    {
        super(context);
        context = (MainActivity) context;
        init();
    }

    public CustomView(Context c, AttributeSet as) {
        super(c, as);
        context = (MainActivity) c;
        init();
    }

    private void init() {

        gl = new GameLogic(this);

        square = new ShapeDrawable[8][8];
        for ( inc = 0; inc < 8; inc += 1)
        {
            for (i = 0; i < 8; i += 1)
            {
                square[inc][i] = new ShapeDrawable(new RectShape());

               square[inc][i].getPaint().setStyle(Paint.Style.STROKE);
                square[inc][i].getPaint().setStrokeWidth(2);
                square[inc][i].getPaint().setColor(Color.GRAY);
            }
        }

        pions = new ShapeDrawable[8][8];

        for ( inc = 0; inc < 8; inc += 1)
        {
            for (i = 0; i < 8; i += 1)
            {
                pions[inc][i] = new ShapeDrawable(new OvalShape());

                pions[inc][i].getPaint().setColor(Color.TRANSPARENT);
            }
        }


    turn = 1;


    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpect)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpect);
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width  = metrics.widthPixels;
        height = metrics.heightPixels;

        height = height - 50;

        width = (width - 80) / 8;
        height = (height -250) / 8;

        if (width < height)
            height = width;
        else
            width = height;





        draw_case(3, 3, 1);
        draw_case(4, 4, 1);
        draw_case(3, 4, 2);
        draw_case(4, 3, 2);
        gl.check_move();
    }


    public void onDraw(Canvas convas)
    {
        super.onDraw(convas);

        int x = 10;
        int y = 10;



        for (inc = 0; inc < 8; inc += 1)
        {
            for (i = 0; i < 8; i += 1)
            {

                square[inc][i].setBounds(x, y, width +x, height + y);
                square[inc][i].draw(convas);
                x = width +x;
            }
            x = 10;
            y = height + y;
        }

        for (inc = 0; inc < 8; inc += 1)
        {
            for (i = 0; i < 8; i += 1)
            {
                if (pions[inc][i].getPaint().getColor() != Color.TRANSPARENT)
                    pions[inc][i].draw(convas);
            }
        }
    }

    public void draw_case(int x, int y, int color)
    {
       pions[x][y] = null;
        pions[x][y] = new ShapeDrawable(new OvalShape());
        if (color == 1)
            pions[x][y].getPaint().setColor(Color.BLACK);
        else if (color == 2)
            pions[x][y].getPaint().setColor(Color.BLUE);
        else if (color == 3)
            pions[x][y].getPaint().setColor(Color.GREEN);
        else if (color == 4)
            pions[x][y].getPaint().setColor(Color.TRANSPARENT);
        if (color > 0 && color < 5 )
        {
            pions[x][y].setBounds(new Rect(((x) * width) + 10, ((y) * width) + 10,((x +1) * width) + 10 , ((y +1) * width) + 10));
        }
        invalidate();
    }


    public void init_board()
    {
        int i;
        int j;

        for (i = 0; i < 8; i +=1)
        {
            for (j = 0; j < 8; j += 1)
            {
                if (pions[i][j].getPaint().getColor() == Color.GREEN)
                    pions[i][j].getPaint().setColor(Color.TRANSPARENT);
            }
        }
        invalidate();
    }

    public void reset_game()
    {
        int i;
        int j;
        for (i = 0; i < 8; i += 1)
        {
            for (j = 0; j < 8; j += 1)
            {
                draw_case(i, j, 4);
            }
        }


        draw_case(3, 3, 1);
        draw_case(4, 4, 1);
        draw_case(3, 4, 2);
        draw_case(4, 3, 2);
        gl.init_game();
        turn = 1;
        gl.check_move();

    }

    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();

        int tmp2 = 10 + height;
        int tmp = 10 + width;
        int inc = 0;
        int inc2 = 0;
        if (x > 10.0 && y > 10.0)
        {
            while (x > tmp && inc < 8)
            {
                tmp = tmp + width;
                inc += 1;
            }
            inc2  = 0;
            while (y > tmp2 && inc2 < 8)
            {
                tmp2 = tmp2 + height;
                inc2 +=1;
            }
        }

        if (inc != 8 && inc2 != 8 ) {
            if (pions[inc][inc2].getPaint().getColor() == Color.GREEN) {
                draw_case(inc, inc2, turn);
                gl.set_all_case(inc, inc2);
                init_board();
                turn = gl.setnextTurn();
                context.set_text(3, turn);
                gl.check_move();
            }
        }

        return super.onTouchEvent(event);
    }

}
