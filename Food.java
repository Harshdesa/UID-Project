/*
Name : Harsh Desai & Ngoc Tran
Net ID : hbd140030 & nmt140230
Date : 4/17/2015
Class : CS 6301
Section : 001

Purpose for writing : UID Project Pacman

General Description : This module is used to create the Food part that the Pacman eats when it travels the maze


 */




package myloverz.com.pacman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;


public class Food extends View
{
    public int diameter = 39;
    float blockSize;
    public int width;
    int height;
    Paint red = new Paint();
    FoodAnim f=new FoodAnim(1000);
    public static float positionx[];
    public static float positiony[];
    public static int positions=0;
    /*

    This is the constructor for the Food View. It will initialize the positions and start the animation for the food.

     */

    public Food(Context context, AttributeSet attrs)
    {

        super(context);
        positionx = new float[1000];
        positiony = new float[1000];
        positions = 0;
        init();
        startAnimation(f);
        f.setRepeatCount(Animation.INFINITE);
    }

    /*

    This method initializes the board. It will store the values of the walls in the board

     */

    private void init()
    {
        for (int x = 1; x <= BoardActivity.board.width; x++) {
            for (int y = 1; y <= BoardActivity.board.height; y++) {
                if(!BoardActivity.board.bot[x][y] || !BoardActivity.board.top[x][y] ||
                        !BoardActivity.board.right[x][y] || !BoardActivity.board.left[x][y])
                {
                    positionx[positions]=(((BoardActivity.board.blockSize)*(x))-(BoardActivity.board.blockSize /2));
                    positiony[positions]=(((BoardActivity.board.blockSize)*(y))-(BoardActivity.board.blockSize /2));
                    positions++;
                }
            }
        }



    }


    /*

    This method will draw the food on the Canvas
     */
    @Override
    protected void onDraw(Canvas canvas)
    {
        red.setColor(Color.RED);

        for(int i=0;i<positions;i++) {
            canvas.drawText("o", positionx[i], positiony[i], red);
        }
    }

    /*
    This method will Animate the food and set the duration for the animation

     */

    public class FoodAnim extends Animation {

        double xMove =0;
        double yMove=3;


        public FoodAnim (long duration) {

            setDuration(duration);
            setInterpolator(new LinearInterpolator());

        }

        /*

        This method will apply the transformation on each food particle and change the display when the pacman eats the food.


         */
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            for(int i=0;i<positions;i++)
            {
                if((BoardActivity.pacMan.paxX <positionx[i])&&(positionx[i]<(BoardActivity.pacMan.paxX + diameter))
                        &&(BoardActivity.pacMan.paxY <positiony[i])&&(positiony[i]<(BoardActivity.pacMan.paxY + diameter)))
                {
                    positionx[i]=0;
                    positiony[i]=0;
                    Score.scorevar++;
                }
            }
            invalidate();

        }
    }

}
