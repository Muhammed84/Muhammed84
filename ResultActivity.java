package com.example.derdiedasfinal;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ResultActivity";

    TextView rscoreTextView, wscoreTextView;
    static int rscore = 0;
    static int wscore = 0;
    Button playAgainButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        rscoreTextView = findViewById(R.id.rscoreTextView);
        wscoreTextView = findViewById(R.id.wscoreTextView);
        playAgainButton = findViewById(R.id.playAgainButton);

        Log.d(TAG, "onCreate: ResultActivity");


        setToolbar();


        String rightAnswers = getString(R.string.RightAnswers);
        String wrongAnswers = getString(R.string.WrongAnswers);

        rscoreTextView.setText(rightAnswers + ": " + rscore);
        wscoreTextView.setText(wrongAnswers + ": " + wscore);

        playAgainButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.playAgainButton) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            LevelOneActivity.index = 0;
            LevelTwoActivity.index = 0;
            LevelThreeActivity.index = 0;
            rscore = 0;
            wscore = 0;
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
        actionBar.setTitle("Your Result");
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
