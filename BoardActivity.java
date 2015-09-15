/**
 * Ngoc Tran - nmt140230
 * Harsh Desai - hbd140030
 * Pacman Game
 * UI Design - CS 6301.001
 */
package myloverz.com.pacman;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

/**
 * The Pacman game is actually played on this class.
 */
public class BoardActivity extends ActionBarActivity  implements SensorEventListener
{
    public static BoardView board;
    public static Pacman pacMan;
    private SensorManager manager;
    private Sensor accel;
    private GhostView ghostball;
    public static Food food;
    public static Score s;
    public static Context mcontext;
    int touchCount=0;
    int colorflag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        //Initialize variables
        mcontext=getApplicationContext();
        board = (BoardView)findViewById(R.id.maze);
        pacMan = (Pacman)findViewById(R.id.mazeball);
        ghostball=(GhostView)findViewById(R.id.ghostball);
        food =(Food)findViewById(R.id.food);
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accel = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    //Return the current context of the activity
    public static Context getContext()
    {
        return mcontext;
    }

    protected void onPause()
    {
        super.onPause();
        manager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        //redraw the pacman based on the sensor
        pacMan.invalidate();
    }

    //Reflect the change from the sensor to the pacman movement
    public void onSensorChanged(SensorEvent event) {

        float pacMovex=0;
        float pacMovey=0;
        if((event.values[0]>0)&&(event.values[0]>event.values[1]))
        {
            pacMovex=2;
            pacMovey=0;
        }
        if((event.values[0]<0)&&(event.values[0]<event.values[1]))
        {
            pacMovex=-2;
            pacMovey=0;
        }
        if((event.values[1]>0)&&(event.values[0]<event.values[1]))
        {
            pacMovex=0;
            pacMovey=2;
        }
        if((event.values[1]<0)&&(event.values[0]>event.values[1]))
        {
            pacMovex=0;
            pacMovey=-2;
        }
        pacMan.move(pacMovex, pacMovey);
        pacMan.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_board, menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected void onResume() {
        super.onResume();
        manager.registerListener(this, accel,
                SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * Alter the background color when the screen is swiped
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                return true;
            case (MotionEvent.ACTION_MOVE):
                touchCount++;
                if(touchCount==4) {
                    if (colorflag == 1) {
                        View view = this.getWindow().getDecorView();
                        view.setBackgroundColor(Color.YELLOW);
                        colorflag = 0;
                    } else {
                        View view = this.getWindow().getDecorView();
                        view.setBackgroundColor(Color.WHITE);
                        colorflag = 1;
                    }
                    touchCount=0;
                }
                return true;
            case (MotionEvent.ACTION_UP):
                return true;
            case (MotionEvent.ACTION_CANCEL):;
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            //Return to the main menu
            case R.id.action_mainMenu:
                finish();
                Intent intent = new Intent(BoardActivity.this, MainActivity.class);
                startActivity(intent);
                invalidateOptionsMenu();
                return true;
            //Access the high score
            case R.id.action_highScore:
                Intent intent2 = new Intent(BoardActivity.this, HighScore.class);
                startActivity(intent2);
                invalidateOptionsMenu();
                return true;

            //Exit the game
            case R.id.action_exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder
                        .setTitle("Exit Confirmation")
                        .setMessage("Do you want to quit?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                                //Exit if yes is pressed
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                                //Do nothing otherwise
                        .setNegativeButton("No", null)                        //Do nothing on no
                        .show();
                invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
