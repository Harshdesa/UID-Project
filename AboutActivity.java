/*
Name : Harsh Desai & Ngoc Tran
Net ID : hbd140030 & nmt140230
Date : 4/17/2015
Class : CS 6301
Section : 001

Purpose for writing : UID Project Pacman

General Description : This module gives you the About Information and tells you what the Pacman game is about and the features on clicking the button

 */





package myloverz.com.pacman;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * This is the about page to tell the user about the game and its functionality
 */
public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        final Button backBtn = (Button) findViewById(R.id.bBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Go back to the Main Menu
                finish();
                Intent intent = new Intent(AboutActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
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
