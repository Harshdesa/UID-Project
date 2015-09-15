/*
Name : Harsh Desai & Ngoc Tran
Net ID : hbd140030 & nmt140230
Date : 4/17/2015
Class : CS 6301
Section : 001

Purpose for writing : UID Project Pacman

General Description : This module is the Pacman View. It creates the Pacman and displays the Pacman with constant animation.

 */


package myloverz.com.pacman;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class Pacman extends View {
    private int diameter = 39;
    private int blockSize = 50;
    private static float TRANSPARENCY = 0;
    public static ShapeDrawable pacMan;
    public static int paxX = 0;
    public static int paxY = 1;
    private int topXPrev ;
    private int topYPrev ;
    public static int startAngle=0;
    public static int sweepAngle=310;
    Paint paint = new Paint();
    public static OpenPacman p;
    public static DeadPacman dp;

    /*
    This is the Pacman constructor which starts the Animation of the Pacman when it is alive and also shows the Animation when the Pacman dies


     */


    public Pacman(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        p = new OpenPacman(0,310,1000);
        dp = new DeadPacman(1000);
        startAnimation(p);
        p.setRepeatCount(Animation.INFINITE);
    }

    /*
    The Pacman gets the position of itself when it calls getPoint()

     */

    public Point getPoint()
    {
        Point p = new Point(paxX, paxY);
        return p;
    }

    /*
    Initializes the Pacman as an arc shape and paints it Green . Also set bounds of the Pacman

     */
    private void init() {
        pacMan = new ShapeDrawable(new ArcShape(startAngle,sweepAngle));
        pacMan.getPaint().setColor(Color.GREEN);
        pacMan.setBounds(paxX, paxY, diameter + paxX, diameter + paxY);
    }

    /*
    Draws the Pacman

     */
    protected void onDraw(Canvas canvas) {
        pacMan.draw(canvas);
    }

    /*
    Constantly moves the Pacman when the Accelerometer changes


     */
    protected void move(float x, float y)
    {
        topXPrev= paxX;
        topYPrev= paxY;
        int newX = paxX - (int)x;
        int newY = paxY + (int)y;

        boolean movable = canPacmanMove(newX, newY);
        if (movable) {
            changeOrientation(paxX, paxY,newX,newY);
            paxX = newX;
            paxY = newY;
        }
        else {
            // pacMan Does not move
        }
        pacMan.setBounds(paxX, paxY, diameter + paxX, diameter + paxY);
    }

    /*
    When the orientation of the device changes, that's when the orientation of the Pacman changes


     */

    private void changeOrientation(int x,int y,int xCompare,int yCompare)
    {
        if((x>xCompare))
        {
            startAngle=180;
        }
        if((x<xCompare))
        {
            startAngle=0;
        }
        if(y<yCompare)
        {
            startAngle=90;
        }
        if((y>yCompare))
        {
            startAngle=270;
        }
    }

    /*
    This method is the method where the ball can move when the orientation changes


     */

    private boolean canPacmanMove(int x, int y)
    {
        int newMidX = x + diameter/2;
        int newMidY = y + diameter/2;
        // Find out which cell pacMan's middle was in.
        int oldCellX = (paxX + diameter/2)/ blockSize + 1;
        int oldCellY = (paxY + diameter/2)/ blockSize + 1;
        // Find out which cell pacMan's middle would be in now
        int newCellX = newMidX/ blockSize + 1;
        int newCellY = newMidY/ blockSize + 1;
        if ((newMidX % blockSize) + diameter/2 >= blockSize) {
            newCellX++;
        }
        if ((newMidX % blockSize) - diameter/2 <= 0) {
            newCellX--;
        }
        if ((newMidY % blockSize) + diameter/2 >= blockSize) {
            newCellY++;
        }
        if ((newMidY % blockSize) - diameter/2 <= 0) {
            newCellY--;
        }
        return !(BoardActivity.board.isCollided(oldCellX, oldCellY, newCellX, newCellY));
    }
/*
Animation to Open the pacman and do the animation.


 */
    public class OpenPacman extends Animation
    {
        float mStartAngle;
        float mSweepAngle;
        public OpenPacman (int startAngle, int sweepAngle, long duration)
        {
            mStartAngle = startAngle;
            mSweepAngle = sweepAngle;
            setDuration(duration);
            setInterpolator(new LinearInterpolator());
        }

        /*

        Applies transformation to the Pacman to open and close the mouth of the Pacman
         */

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t)
        {
            float currAngle = sweepAngle + ((360 - sweepAngle) * interpolatedTime);
            Pacman.pacMan.setShape(new ArcShape(startAngle, currAngle));
        }
    }

    /*

    Does the Animation of the dead pacman. Whe the Pacman dies, the animation for the dead pacman starts and restarts to the start position
     */

    public class DeadPacman extends Animation
    {
        public DeadPacman(long duration)
        {
            setDuration(duration);
            setInterpolator(new LinearInterpolator());
        }

        /*
        Applies transformation to the dead pacman.For proper animation.

         */
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t)
        {
            float deadangle = sweepAngle - (sweepAngle * interpolatedTime);
            Pacman.pacMan.setShape(new ArcShape(startAngle, deadangle));
        }
    }
}
