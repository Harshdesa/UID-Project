/**
 * Ngoc Tran - nmt140230
 * Harsh Desai - hbd140030
 * Pacman Game
 * UI Design - CS 6301.001
 */
package myloverz.com.pacman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * This class is the main menu for the game
 * From here, we can create a new game, check the high score
 * read the about page or exit the game.
 */
public class MainActivity extends ActionBarActivity {
    ImageView logo;
    public static Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mcontext=getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = (ImageView) findViewById(R.id.mainLogo);
        Bitmap bmp;
        bmp= BitmapFactory.decodeResource(getResources(), R.drawable.pacman);//image is your image
        bmp=Bitmap.createScaledBitmap(bmp, 100,100, true);
        logo.setImageBitmap(bmp);
        final Button newGameBtn = (Button) findViewById(R.id.newGame);
        final Button highScoreBtn = (Button) findViewById(R.id.hsBtn);
        final Button aboutBtn = (Button) findViewById(R.id.aboutBtn);
        final Button exitBtn = (Button) findViewById(R.id.exitBtn);
        Score.readFromFile(); // load from file to the high score

        //Create new game
        newGameBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MainActivity.this, BoardActivity.class);
                startActivity(intent);
            }
        });

        //Open about page
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        //Check the high scores
        highScoreBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HighScore.class);
                startActivity(intent);
            }
        });

        //Exit the game
        exitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    //Return the current context
    public static Context getContext()
    {
        return mcontext;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
