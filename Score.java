/*
Name : Harsh Desai & Ngoc Tran
Net ID : hbd140030 & nmt140230
Date : 4/17/2015
Class : CS 6301
Section : 001

Purpose for writing : UID Project Pacman

General Description : This module is used to create the Score part that the Pacman eats and updates the score when it travels the maze.


 */


package myloverz.com.pacman;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;


public class Score {

    public static int scorevar=0;
    public static int HighScore1=0;         // The topmost High Score
    public static int HighScore2=0;         // The second High Score
    public static int HighScore3=0;         // The 3rd High Score.


    /*

    Empty score constructor
     */
    public Score()
    {
    }
    /*
    Uses File Handling to handle the score . This method will write the Highscore to the file when the Pacman dies


     */
    public static void writeToFile() {
        FileOutputStream fos;
        FileOutputStream fos2;
        String data=Integer.toString(scorevar);
        String data2;
        try {
            fos = MainActivity.getContext().openFileOutput("hello_file", Context.MODE_PRIVATE);
            fos2 = MainActivity.getContext().openFileOutput("hello_file2",Context.MODE_PRIVATE);

            fos.write(data.getBytes());
            if(scorevar>=HighScore1)
            {
                HighScore3=HighScore2;
                HighScore2=HighScore1;
                HighScore1=scorevar;

            }
            else if(scorevar>=HighScore2)
            {
                HighScore3=HighScore2;
                HighScore2=scorevar;

            }
            else if(scorevar>=HighScore3)
            {
                HighScore3=scorevar;
            }
            File temp = new File("hello_file2");
            if (temp.exists()) {
                RandomAccessFile raf = new RandomAccessFile(temp, "rw");
                raf.setLength(0);
            }
            data2= Integer.toString(HighScore1) + "\t" + Integer.toString(HighScore2) + "\t" + Integer.toString(HighScore3);
            fos2.write(data2.getBytes());
            fos.close();
            fos2.close();
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
    }

    /*
    This method will read the Highscore from the file and display it on the screen when the HighScore button is clicked.


     */

    public static void readFromFile()
    {
        try
        {
            FileInputStream fis = MainActivity.getContext().openFileInput("hello_file");
            FileInputStream fis2 = MainActivity.getContext().openFileInput("hello_file2");
            InputStreamReader isr = new InputStreamReader(fis);
            InputStreamReader isr2 = new InputStreamReader(fis2);
            BufferedReader bufferedReader = new BufferedReader(isr);
            BufferedReader bufferedReader2 = new BufferedReader(isr2);
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            String line;
            String line2;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            while ((line2 = bufferedReader2.readLine()) != null) {
                sb2.append(line2);
            }
            System.out.println(sb2.toString());
            String score = sb2.toString();
            String[] scores = score.split("\t");
            if (!score.isEmpty()) {
                if (!scores[0].isEmpty())
                {HighScore1 = Integer.parseInt(scores[0]);}
                if (!scores[1].isEmpty())
                {HighScore2 = Integer.parseInt(scores[1]);}
                if (!scores[2].isEmpty())
                    HighScore3 = Integer.parseInt(scores[2]);
            }

        }
        catch(FileNotFoundException e){e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}
    }


}
