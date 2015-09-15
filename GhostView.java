/**
 * Ngoc Tran - nmt140230
 * Harsh Desai - hbd140030
 * Pacman Game
 * UI Design - CS 6301.001
 */
package myloverz.com.pacman;

/**
 * Created by Harsh Desaion 4/18/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.Toast;

import java.util.Random;

public class GhostView extends View
{
    private int diameter = 39;
    private int blockSize = 50;
    private static float TRANSPARENCY = 0;
    private int life = 1; //Number of life of player
    private Ghost ghost0, ghost1, ghost2, ghost3; //All the ghosts of the game
    public static GhostAnim p; //Animation of those ghosts

    //A ghost will have a x, y position and an option to determine which way it will go
    public class Ghost
    {
        ShapeDrawable ghost; // Shape of a ghost
        int posX;
        int posY;
        int option;

        public Ghost(int x, int y)
        {
            posX = x;
            posY = y;
            Random r = new Random();
            option = r.nextInt(3);
            //Initialize shape of the ghost
            ghost = new ShapeDrawable(new OvalShape());
            ghost.getPaint().setColor(Color.RED);
            ghost.setBounds(posX, posY, diameter + posX, diameter + posY);
        }
    }

    public GhostView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Initialize all the ghosts with positions
        ghost0 = new Ghost(500,500);
        ghost1 = new Ghost(100,100);
        ghost2 = new Ghost(300,301);
        ghost3 = new Ghost(0,500);
        p = new GhostAnim(1000, ghost0, ghost1, ghost2, ghost3);
        //Start the animation and run its infinitely
        startAnimation(p);
        p.setRepeatCount(Animation.INFINITE);
    }


    protected void onDraw(Canvas canvas)
    {
        ghost0.ghost.draw(canvas);
        ghost1.ghost.draw(canvas);
        ghost2.ghost.draw(canvas);
        ghost3.ghost.draw(canvas);
    }

    protected void moveGhost(Ghost ghost, float x, float y) {
        int newX = ghost.posX - (int)x;
        int newY = ghost.posY  + (int)y;
        boolean movable = canGhostMove(ghost,newX, newY);
        if (movable) {
            ghost.posX = newX;
            ghost.posY = newY;
        } else {
            Random r=new Random();
            ghost.option=r.nextInt(4);
        }
        ghost.ghost.setBounds(ghost.posX, ghost.posY, diameter + ghost.posX, diameter + ghost.posY);
    }

    private boolean canGhostMove(Ghost ghost,int x, int y) {
        int newMidX = x + diameter/2;
        int newMidY = y + diameter/2;
        // Find out which cell pacMan's middle was in.
        // Note that this division will round down, which is correct
        int oldCellX = (ghost.posX + diameter/2)/ blockSize + 1;
        int oldCellY = (ghost.posY + diameter/2)/ blockSize + 1;
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

    //Animation class of ghost
    public class GhostAnim extends Animation
    {
        //Movements of the 4 ghosts
        double xMove = 0, yMove = 0, xMove1 = 0, yMove1 = 0,
                xMove2 = 0,yMove2 = 0, xMove3 = 0, yMove3 = 0;
        Ghost theGhost;
        Ghost theGhost1;
        Ghost theGhost2;
        Ghost theGhost3;
        public GhostAnim (long duration, Ghost ghost, Ghost ghost1, Ghost ghost2, Ghost ghost3) {
            theGhost = ghost;
            theGhost1 = ghost1;
            theGhost2 = ghost2;
            theGhost3 = ghost3;
            setDuration(duration);
            setInterpolator(new LinearInterpolator());
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            xMove = getMove(theGhost.option).x;
            yMove = getMove(theGhost.option).y;
            xMove1 = getMove(theGhost1.option).x;
            yMove1 = getMove(theGhost1.option).y;
            xMove2 = getMove(theGhost2.option).x;
            yMove2 = getMove(theGhost2.option).y;
            xMove3 = getMove(theGhost3.option).x;
            yMove3 = getMove(theGhost3.option).y;
            moveGhost(theGhost,(float)xMove,(float)yMove);
            moveGhost(theGhost1,(float)xMove1,(float)yMove1);
            moveGhost(theGhost2,(float)xMove2,(float)yMove2);
            moveGhost(theGhost3,(float)xMove3,(float)yMove3);
            Point p = new Point(BoardActivity.pacMan.getPoint());

            if ( checkCollision(p.x, p.y, theGhost)
                    || checkCollision(p.x, p.y, theGhost1)
                    || checkCollision(p.x, p.y, theGhost2)
                    || checkCollision(p.x, p.y, theGhost3) )
            {
                if(life > 0) //Still have life
                {
                    life--;
                    BoardActivity.pacMan.paxX = 0;
                    BoardActivity.pacMan.paxY = 1;

                }
                else //Game Over
                {
                    BoardActivity.pacMan.p.setRepeatCount(0);
                    BoardActivity.pacMan.startAnimation(BoardActivity.pacMan.dp);
                    String score = Integer.toString(Score.scorevar);
                    clearAnimation();
                    Toast.makeText(BoardActivity.getContext(), "Game Over!\nYour Score is "+score,
                            Toast.LENGTH_LONG).show();
                    BoardActivity.pacMan.setEnabled(false);

                    ;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            BoardActivity.pacMan.paxX = 0;
                            BoardActivity.pacMan.paxY = 1;
                            Context context = BoardActivity.getContext();
                            Intent i = new Intent(context, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                            Score.writeToFile();
                            Score.scorevar = 0;
                        }
                    }, 4000);
                }
            }
            invalidate();

        }

        //Use the ghost's option to determine its movement
        public Point getMove(int option)
        {
            Point move = new Point();
            if( option == 0) move = new Point(0,2);
            if( option == 1) move = new Point(2,0);
            if( option == 2) move = new Point(0,-2);
            if( option == 3) move = new Point(-2,0);
            return move;
        }

        //Check collision between a ghost and a particular position
        public boolean checkCollision(int x, int y, Ghost ghost)
        {
            Rect r1 = new Rect(x,y,x+diameter,y+diameter);
            Rect r2 = new Rect(ghost.posX,ghost.posY,ghost.posX+diameter,ghost.posY+diameter );

            return r1.intersect(r2);
        }
    }


}
