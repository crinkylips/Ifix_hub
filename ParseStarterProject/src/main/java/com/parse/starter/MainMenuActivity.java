package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener{

    Button JobsSheet;
    Button forSale;
    Button toDoList;
    Button parts;
    Button timeSheet;
    Button training;
    Button cashUp;

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            MenuInflater menuInflater = getMenuInflater();

            menuInflater.inflate(R.menu.logout, menu);

            return super.onCreateOptionsMenu(menu);

        }

          @Override
              public boolean onOptionsItemSelected(MenuItem item) {


              if (item.getItemId() == R.id.logout) {

                      ParseUser.logOut();

                      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                      startActivity(intent);

                  }

                  return super.onOptionsItemSelected(item);
              }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        JobsSheet =  (Button) findViewById(R.id.JobsSheet);
        forSale =    (Button) findViewById(R.id.forSale);
        toDoList =   (Button) findViewById(R.id.toDoList);
        parts =      (Button) findViewById(R.id.Parts);
        timeSheet =  (Button) findViewById(R.id.timeSheet);
        training =   (Button) findViewById(R.id.training);
        cashUp =     (Button) findViewById(R.id.cashUp);


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }

//    public void showJobsSheet (View view)
//    {
//        Intent i = new Intent(this, jobsList.class);
//        startActivity(i);
//    }

    public void showForSale(View view)
    {
        Intent i = new Intent(this, UserListActivity.class);
        startActivity(i);
    }

//    public void showToDoList(View view)
//    {
//        Intent i = new Intent(this, toDoList.class);
//        startActivity(i);
//    }
//
    public void showParts(View view)
    {
        Intent i = new Intent(this, FavPartsAdapter.class);
        startActivity(i);
    }

//    public void showTimeSheet(View view)
//    {
//        Intent i = new Intent(this, timeSheet.class);
//        startActivity(i);
//    }

//    public void showTraining(View view)
//    {
//        Intent i = new Intent(this, training.class);
//        startActivity(i);
//    }
//
//    public void showCashUp(View view)
//    {
//        Intent i = new Intent(this, cashUp.class);
//        startActivity(i);
//    }



}

