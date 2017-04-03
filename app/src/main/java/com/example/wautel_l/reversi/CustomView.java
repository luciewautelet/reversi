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

    private Paint grey, white, black;
    private ShapeDrawable[][] square;
    private ShapeDrawable[][] pions;
    private int width;
    private int height;
    private int inc;
    private int i;

    public CustomView(Context context)
    {
        super(context);
        init();
    }

    public CustomView(Context c, AttributeSet as) {
        super(c, as);
        init();
    }

    private void init() {

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

                pions[inc][i].getPaint().setColor(Color.GRAY);
            }
        }





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
                if (pions[inc][i].getPaint().getColor() == Color.BLACK || pions[inc][i].getPaint().getColor() == Color.BLUE )
                    pions[inc][i].draw(convas);
            }
        }
    }

    public void draw_case(int x, int y, int color)
    {
        Log.e("width" , ""+ width);
        Log.e("width   x" , ""+ ((x +1) * width) + 10);
        Log.e("width   y" , ""+ ((x +2) * width) + 10);
       pions[x][y] = null;
        pions[x][y] = new ShapeDrawable(new OvalShape());
        if (color == 1)
            pions[x][y].getPaint().setColor(Color.BLACK);
        else if (color == 2)
            pions[x][y].getPaint().setColor(Color.BLUE);
        if (color == 1 || color == 2)
        {
           pions[x][y].setBounds(new Rect(((x) * width) + 10, ((y) * width) + 10,((x +1) * width) + 10 , ((y +1) * width) + 10));
           // pions[x][y].setBounds(new Rect(10, 10, 90, 90));
        }
        invalidate();
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



        return super.onTouchEvent(event);
    }
}
