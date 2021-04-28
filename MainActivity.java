package com.example.derdiedasfinal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import hotchemi.android.rate.AppRate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    Toolbar toolbar;
    Button levelOneButton, levelTwoButton, levelThreeButton, favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: onCreate");


        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor();

        AppRate.showRateDialogIfMeetsConditions(this);
        AppRate.with(this).clearAgreeShowDialog();

        setToolbar();

        findViewById(levelOneButton);


        levelOneButton.setOnClickListener(this);
        levelTwoButton.setOnClickListener(this);
        levelThreeButton.setOnClickListener(this);
        favoriteButton.setOnClickListener(this);


    }



    public void findViewById ( View view){
         levelOneButton = findViewById(R.id.levelOneButton);
         levelTwoButton = findViewById(R.id.levelTwoButton);
         levelThreeButton = findViewById(R.id.levelThreeButton);
         favoriteButton = findViewById(R.id.favoriteButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: MainActivity");
        
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.basemenu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(TAG, "onOptionsItemSelected: MainActivity");
        int id = item.getItemId();

        if (id == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://developer.android.com/topic/libraries/architecture");

            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }


        return super.

                onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View view) {

        Log.d(TAG, "onClick: MainActivity");
        int id = view.getId();

        Intent intent;
        if ( id == R.id.levelOneButton){

            intent = new Intent(getApplicationContext(), LevelOneActivity.class);
            startActivity(intent);

        }else if ( id == R.id.levelTwoButton){

            intent = new Intent(getApplicationContext(), LevelTwoActivity.class);
            startActivity(intent);


        }else if ( id == R.id.levelThreeButton){

            intent = new Intent(getApplicationContext(), LevelThreeActivity.class);
            startActivity(intent);

        }else if ( id == R.id.favoriteButton){

            intent = new Intent(getApplicationContext(), FavoriteActivity.class);
            startActivity(intent);

        }
    }

    public void setToolbar() {
        Log.d(TAG, "setToolbar: MainActivity");
        
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Der Die Das Game");
    }

}
