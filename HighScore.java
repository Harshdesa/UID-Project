/*
Name : Harsh Desai & Ngoc Tran
Net ID : hbd140030 & nmt140230
Date : 4/17/2015
Class : CS 6301
Section : 001

Purpose for writing : UID Project Pacman

General Description : This module is used to display the HighScore part that the Pacman eats and updates the score when it travels the maze.


 */


package myloverz.com.pacman;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HighScore extends ActionBarActivity {
    private TextView hs1, hs2, hs3;

    /*
    This is the On create method which is called when the intent of the activity is invoked.

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        final Button bBtn = (Button) findViewById(R.id.backButton);
        hs1 = (TextView)findViewById(R.id.firstScore);
        hs2 = (TextView)findViewById(R.id.secondScore);
        hs3 = (TextView)findViewById(R.id.thirdScore);
        hs1.setText(String.valueOf(Score.HighScore1));
        hs2.setText(String.valueOf(Score.HighScore2));
        hs3.setText(String.valueOf(Score.HighScore3));

        bBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
    /*
    When the menu button is clicked action Bar the menu_high_score.xml is called.
     Inflate the menu; this adds items to the action bar if it is present.
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_high_score, menu);
        return true;
    }

    /*
      Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.

     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
