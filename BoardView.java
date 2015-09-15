/**
 * Ngoc Tran - nmt140230
 * Harsh Desai - hbd140030
 * Pacman Game
 * UI Design - CS 6301.001
 */
package myloverz.com.pacman;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Ngoc Tran on 4/16/2015.
 */
public class BoardView extends View
{
    boolean[][] visited;
    public static boolean[][] top;
    public static boolean[][] bot;
    public static  boolean[][] right;
    public static boolean[][] left;
    public static  int width, height;
    public static  int blockSize = 50; //One Block size

    public BoardView(Context context, AttributeSet attrs)
    {
        super(context);
        //Get the screen size from the activity
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int maxX = metrics.widthPixels;
        int maxY = metrics.heightPixels;
        //Set the width and height of the board
        setBWidth(maxX / blockSize);
        setBHeight(maxY / blockSize -3 );
        initBoard();
        CreateBlock(1, 1);
    }

    private void initBoard()
    {
        // border of the board have already been visited
        visited = new boolean[width+2][height+2];
        for(int i = 0; i<width+2;i++)
        {
            for(int j = 0; j<height+2;j++)
            {
                visited[i][j]=false;
            }
        }

        //Make the border
        for (int i = 0; i < width+2; i++) {
            visited[i][0] = visited[i][height+1] = true;
        }
        for (int j = 0; j < height+2; j++)
            visited[0][j] = visited[width+1][j] = true;

        // every block has all its own borders to start with
        top = new boolean[width+2][height+2];
        right = new boolean[width+2][height+2];
        left = new boolean[width+2][height+2];
        bot = new boolean[width+2][height+2];
        for (int i = 0; i < width+2; i++)
            for (int j = 0; j < height+2; j++)
                top[i][j] = right[i][j] = bot[i][j] = left[i][j] = true;
    }

    //Create the board, start from position 1-1
    private void CreateBlock(int posX, int posY)
    {
        visited[posX][posY] = true;
        while (!visited[posX][posY+1] || !visited[posX+1][posY] || !visited[posX][posY-1] || !visited[posX-1][posY]) {
            while (true) {
                double random = Math.random();
                if (random < 0.25 && !visited[posX][posY-1]) {
                    top[posX][posY] = bot[posX][posY-1] = false;
                    CreateBlock(posX, posY - 1);
                    break;
                }
                else if (random >= 0.25 && random < 0.50 && !visited[posX+1][posY]) {
                    right[posX][posY] = left[posX+1][posY] = false;
                    CreateBlock(posX + 1, posY);
                    break;
                }
                else if (random >= 0.5 && random < 0.75 && !visited[posX][posY+1]) {
                    bot[posX][posY] = top[posX][posY+1] = false;
                    CreateBlock(posX, posY + 1);
                    break;
                }
                else if (random >= 0.75 && random < 1.00 && !visited[posX-1][posY]) {
                    left[posX][posY] = right[posX - 1][posY] = false;
                    CreateBlock(posX - 1, posY);
                    break;
                }
            }
        }
    }

    /**
     *     Check if the pacman collides with the board
     *     int prevX, int prevY: previous location of the pacman
     *     int nextX, int nextY: next location of the pacman
     *     return true if it collides, false otherwise
     */
    public static boolean isCollided(int prevX, int prevY, int nextX, int nextY) {
        boolean isCollided;
        if      ((prevX == nextX) && (prevY == nextY)) { isCollided = false; }
        else if (nextX == prevX - 1) {
            isCollided = left[prevX][prevY];
        } else if (nextX == prevX + 1) {
            isCollided = right[prevX][prevY];
        } else if (nextY == prevY - 1) {
            isCollided = top[prevX][prevY];
        } else if (nextY == prevY + 1) {
            isCollided = bot[prevX][prevY];
        } else { isCollided = false; }

        if ((prevX != nextX) && (prevY != nextY)) {
            if ((nextX == prevX + 1) && (nextY == prevY + 1) &&
                    (top[nextX][nextY] || left[nextX][nextY]) ) {
                isCollided = true;
            } else if ((nextX == prevX + 1) && (nextY == prevY - 1) &&
                    (bot[nextX][nextY] || left[nextX][nextY]) ) {
                isCollided = true;
            } else if ((nextX == prevX - 1) && (nextY == prevY + 1) &&
                    (top[nextX][nextY] || right[nextX][nextY]) ) {
                isCollided = true;
            } else if ((nextX == prevX - 1) && (nextY == prevY - 1) &&
                    (bot[nextX][nextY] || right[nextX][nextY]) ) {
                isCollided = true;
            }
        }
        return isCollided;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint  = new Paint();
        Paint red = new Paint();
        red.setColor(Color.RED);
        paint.setColor(Color.BLUE);
        for (int x = 1; x <= width; x++) {
            for (int y = 1; y <= height; y++) {

                if (bot[x][y]) {
                    canvas.drawLine(blockSize *(x-1),
                            blockSize *y,
                            blockSize *x,
                            blockSize *y,
                            paint);
                }
                if (top[x][y]) {
                    canvas.drawLine(blockSize *(x-1), blockSize *(y-1),
                            blockSize *x,
                            blockSize *(y-1),
                            paint);
                }
                if (left[x][y]) {
                    canvas.drawLine(blockSize *(x-1), blockSize *(y-1),
                            blockSize *(x-1), blockSize *y,
                            paint);
                }
                if (right[x][y]) {
                    canvas.drawLine(blockSize *x, blockSize *(y-1),
                            blockSize *x, blockSize *y,
                            paint);
                }

            }
        }
    }

    //Get the board width
    public int getBWidth()
    {
        return width;
    }

    //Get the board height
    public int getBHeight()
    {
        return height;
    }

    // Set the board width
    public void setBWidth(int value)
    {
        width = value;
    }

    // Set the board height
    public void setBHeight(int value)
    {
        height = value;
    }
}
