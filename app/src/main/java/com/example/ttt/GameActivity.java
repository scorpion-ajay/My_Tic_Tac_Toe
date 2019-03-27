package com.example.ttt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.regex.Matcher;

public class GameActivity extends AppCompatActivity {

    public int[][] Matrix = new int[3][3];  // create a matrix
    public int turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // initialise matrix
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                Matrix[i][j] = -1;

        // initialise turn
        turn = 1;
    }

    public boolean check(int row,int col,int val){
        boolean all = true;

        //check if every column element in that row is same
        for(int i=0;i<3;i++)
            if(Matrix[row][i]!=val) {
                all = false;
                break;
            }

        if(all)
            return all;

        all = true;
        //check if every row element in that column is same
        for(int i=0;i<3;i++)
            if(Matrix[i][col]!=val){
                all = false;
                break;
            }

        if(all)
            return all;

        //check if top left - bottom right diagonal have same elements
        if(Matrix[0][0]==Matrix[1][1] && Matrix[1][1]==Matrix[2][2] && Matrix[0][0]==val)
            return true;

        //check if top right - bottom left diagonal have same elements
        if(Matrix[0][2]==Matrix[1][1] && Matrix[1][1]==Matrix[2][0] && Matrix[0][2]==val)
            return true;

        return false;
    }

    public void drop(View view) {
        ImageView box = (ImageView) view;
        int tag = Integer.parseInt((String) box.getTag()) - 1;
        int row = tag/3;
        int col = tag%3;
        if(Matrix[row][col]!=-1)
            return;
        if(turn%2==1){
            box.setImageResource(R.drawable.cross);
            Matrix[row][col] = 1;
        }
        else{
            box.setImageResource(R.drawable.zero);
            Matrix[row][col] = 0;
        }

        // check if someone has won
        if(check(row,col,turn%2)==true) {
            Intent i = new Intent(GameActivity.this, ResultActivity.class);
            i.putExtra("winner", turn%2);
            startActivity(i);
            finish();
        }

        // check if its a draw
        boolean draw = true;
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(Matrix[i][j] == -1){
                    draw = false;
                    break;
                }

        if(draw){
            Intent i = new Intent(GameActivity.this, ResultActivity.class);
            i.putExtra("winner", 2);
            startActivity(i);
            finish();
        }
        turn++;
    }
}
