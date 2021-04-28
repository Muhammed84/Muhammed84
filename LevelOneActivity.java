package com.example.derdiedasfinal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class LevelOneActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LevelOneActivity";

    Toolbar toolbar;
    Button nextButton, prevButton, derButton, dieButton, dasButton;
    TextView wordsTextView, meaningTextView, resultTextView;


    long seed;

    //Arrays
    String[] derArray, dieArray, dasArray;

    // Counter
    static int index;

    //ArrayLists;
    private ArrayList<Words> wordsArrayList;
    public ArrayList<Words> checkBoxKeyArrayList;
    //String article = "";

    ProgressBar progressBar;
    CheckBox mCheckbox;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);


        Log.d(TAG, "onCreate: LevelOne");
        setToolbar();
        findViewById(nextButton);

        // 3 Arrays in Background to compare with
        derArray = getResources().getStringArray(R.array.derArray);
        dieArray = getResources().getStringArray(R.array.dieArray);
        dasArray = getResources().getStringArray(R.array.dasArray);

        wordsArrayList = new ArrayList<>();
        checkBoxKeyArrayList = new ArrayList<>();

        setWords();

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        derButton.setOnClickListener(this);
        dieButton.setOnClickListener(this);
        dasButton.setOnClickListener(this);


        mCheckbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Words words = wordsArrayList.get(index);
                MyJasonSaver myJasonSaver = new MyJasonSaver(LevelOneActivity.this);


                if (mCheckbox.isChecked()) {

                    save(checkBoxKeyArrayList.get(index).getGermanWord(), true);
                    myJasonSaver.addFavorite(words);


                    Toast.makeText(LevelOneActivity.this, getResources().getString(R.string.add_favr),
                            Toast.LENGTH_SHORT).show();

                } else {

                    save(checkBoxKeyArrayList.get(index).getGermanWord(), false);
                    myJasonSaver.removeFavorites(words);

                    Toast.makeText(LevelOneActivity.this, getResources().getString(R.string.remove_favr),
                            Toast.LENGTH_SHORT).show();

                }

            }
        });


        mCheckbox.setChecked(load(checkBoxKeyArrayList.get(index).getGermanWord()));


    }


    private void save(String key, boolean isChecked) {

        SharedPreferences mPreferences = getApplicationContext().getSharedPreferences("CheckBoxPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPreferences.edit();
        mEditor.putBoolean(key, isChecked);
        mEditor.apply();


    }

    private boolean load(String key) {

        SharedPreferences mPreferences = getApplicationContext().getSharedPreferences("CheckBoxPreference", Context.MODE_PRIVATE);
        return mPreferences.getBoolean(key, false);
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: LevelOneActivity");

        String article = "";
        String anotherArticle = "";
        String articleKind = "";
        String word = wordsTextView.getText().toString();
        String message = getResources().getString(R.string.WrongAnswer);
        boolean isButtonPressed;

        int id = v.getId();

        if (id == R.id.derButton || id == R.id.dieButton || id == R.id.dasButton){
            isButtonPressed = true;
        }else {
            isButtonPressed = false;
        }


        if (id == R.id.nextButton) {
            prevButton.setEnabled(true);
            derButton.setEnabled(true);
            // move to the next el.
            if (index < wordsArrayList.size() - 1) {
                index += 1;

            } else {
                openResult();
            }

        } else if (id == R.id.prevButton) {
            // move to the prev el.
            if (index > 0) {
                index -= 1;

            } else {
                prevButton.setEnabled(false);
                String prevMessage = "Please hit next";

                Snackbar.make(v, prevMessage, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }

        } else if (id == R.id.derButton) {

            for (int i = 0; i < derArray.length; i++) {
                if (word.contentEquals(derArray[i])) {
                    articleKind = " <font color='#5063CE'>der</font> ";
                    article = articleKind + word;
                    ResultActivity.rscore++;

                } else if (word.contentEquals(dieArray[i]) || word.contentEquals(dasArray[i]) ) {

                    article = message;
                    ResultActivity.wscore++;


                }
            }

        } else if (id == R.id.dieButton) {

            for (int i = 0; i < dieArray.length; i++) {
                if (word.contentEquals(dieArray[i])) {
                    articleKind = " <font color='#EB0DE1'>die</font> ";
                    article = articleKind + word;
                    ResultActivity.rscore++;

                } else if (word.contentEquals(derArray[i]) || word.contentEquals(dasArray[i])) {

                    article = message;
                    ResultActivity.wscore++;

                }
            }

        } else {
            id = R.id.dasButton;

            for (int i = 0; i < dasArray.length; i++) {
                if (word.contentEquals(dasArray[i])) {
                    articleKind = " <font color='#4CAF50'>das</font> ";
                    article = articleKind + word;
                    ResultActivity.rscore++;

                } else if (word.contentEquals(derArray[i]) || word.contentEquals(dieArray[i])) {

                    article = message;
                    ResultActivity.wscore++;


                }
            }


        }




        wordsTextView.setText(wordsArrayList.get(index).getGermanWord());
        meaningTextView.setText(wordsArrayList.get(index).getArabicWord());
        resultTextView.setText(Html.fromHtml(article));
        mCheckbox.setChecked(load(checkBoxKeyArrayList.get(index).getGermanWord()));


        



/*
        if (id == R.id.nextButton) {
            prevButton.setEnabled(true);
            if (index <= wordsArrayList.size() - 1)
                if (index < wordsArrayList.size() - 1)
                    index += 1;
                else

                    openResult();

        } else if (id == R.id.prevButton) {
            if (index >= 0)
                if (index > 0)
                    index -= 1;
                else {

                    String prevMessage = "Please hit next";

                    Snackbar.make(v, prevMessage, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    prevButton.setEnabled(false);
                }
        }


        wordsTextView.setText(wordsArrayList.get(index).getGermanWord());
        meaningTextView.setText(wordsArrayList.get(index).getArabicWord());

        String german = wordsTextView.getText().toString();
        String massage = getString(R.string.WrongAnswer);
        article = "";


        if (id == R.id.derButton) {
            for (String der : derArray) {

                if (der.contentEquals(german)) {

                    article = " <font color='#5063CE'>der</font> " + der;
                    nextButton.setEnabled(true);
                    prevButton.setEnabled(true);
                    ResultActivity.rscore++;
                    break;

                } else {

                    article = massage;
                    nextButton.setEnabled(false);
                    prevButton.setEnabled(false);


                }
            }
        } else if (id == R.id.dieButton) {
            for (String die : dieArray) {

                if (die.contentEquals(german)) {
                    ResultActivity.rscore++;
                    article = " <font color='#EB0DE1'>die</font> " + die;
                    nextButton.setEnabled(true);
                    prevButton.setEnabled(true);
                    break;

                } else {
                    article = massage;

                    nextButton.setEnabled(false);
                    prevButton.setEnabled(false);


                }
            }
        } else if (id == R.id.dasButton) {
            for (String das : dasArray) {

                if (das.contentEquals(german)) {

                    ResultActivity.rscore++;
                    nextButton.setEnabled(true);
                    prevButton.setEnabled(true);
                    article = " <font color='#4CAF50'>das</font> " + das;

                    break;
                } else {
                    article = massage;

                    nextButton.setEnabled(false);
                    prevButton.setEnabled(false);


                }
            }
        }

        resultTextView.setText(Html.fromHtml(article));
        mCheckbox.setChecked(load(checkBoxKeyArrayList.get(index).getGermanWord()));

        if (article.equals(massage)) {
            ResultActivity.wscore++;
        }

 */


    }

    public void openResult() {

        Intent intent = new Intent(LevelOneActivity.this, ResultActivity.class);

        startActivity(intent);
    }


    public void setWords() {
        Words words1 = new Words("der", "Tisch", "طاولة");
        Words words2 = new Words("der", "Boden", "أرضية");
        Words words3 = new Words("der", "Teller", "صحن");
        Words words4 = new Words("der", "Teppich", "سجادة");
        Words words5 = new Words("der", "Buchstabe", "حرف أبجدي");
        Words words6 = new Words("die", "Banane", "موزة");
        Words words7 = new Words("die", "Flasche", "زجاجة");
        Words words8 = new Words("die", "Frage", "سؤال");
        Words words9 = new Words("die", "Wand", "حائط");
        Words words10 = new Words("die", "Stunde", "ساعة( 60 دقيقة )");
        Words words11 = new Words("das", "Buch", "كتاب");
        Words words12 = new Words("das", "Kind", "طفل");
        Words words13 = new Words("das", "Fenster", "نافذة");
        Words words14 = new Words("das", "Brot", "خبز");
        Words words15 = new Words("das", "Heft", "كتيب");

        wordsArrayList.add(words1);
        wordsArrayList.add(words2);
        wordsArrayList.add(words3);
        wordsArrayList.add(words4);
        wordsArrayList.add(words5);
        wordsArrayList.add(words6);
        wordsArrayList.add(words7);
        wordsArrayList.add(words8);
        wordsArrayList.add(words9);
        wordsArrayList.add(words10);
        wordsArrayList.add(words11);
        wordsArrayList.add(words12);
        wordsArrayList.add(words13);
        wordsArrayList.add(words14);
        wordsArrayList.add(words15);

        checkBoxKeyArrayList.addAll(wordsArrayList);
        Log.d(TAG, "setWords: " + checkBoxKeyArrayList.size());


        //shuffle

        seed = System.nanoTime();
        Collections.shuffle(wordsArrayList, new Random(seed));
        Collections.shuffle(checkBoxKeyArrayList, new Random(seed));


        //resultTextView.setText(wordsArrayList.get(index).getArticleWord());
        wordsTextView.setText(wordsArrayList.get(index).getGermanWord());
        meaningTextView.setText(wordsArrayList.get(index).getArabicWord());


    }

    public void setToolbar() {
        Log.d(TAG, "setToolbar: MainActivity");

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Level One");
    }

    public void findViewById(View view) {
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        derButton = findViewById(R.id.derButton);
        dieButton = findViewById(R.id.dieButton);
        dasButton = findViewById(R.id.dasButton);

        wordsTextView = findViewById(R.id.wordsTextView);
        meaningTextView = findViewById(R.id.meaningTextView);
        resultTextView = findViewById(R.id.resultTextView);

        progressBar = findViewById(R.id.progressBar);
        mCheckbox = findViewById(R.id.checkbox);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: LevelOneActivity");

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.basemenu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(TAG, "onOptionsItemSelected: LevelOneActivity");
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


}
