package com.example.marek.timereggapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView resulTime;
    SeekBar timeSeekBar;
    boolean isCounterActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void resetTimer() {
        timeSeekBar.setEnabled(true);
        resulTime.setText("0:30");
        timeSeekBar.setProgress(90);
        countDownTimer.cancel();
        goButton.setText("GO!");
        isCounterActive = false;
    }

    public void onClickGo (View view) {
        if(isCounterActive) {
            resetTimer();
        } else {

            isCounterActive = true;
            timeSeekBar.setEnabled(false);
            goButton.setText("STOP");

            countDownTimer = new CountDownTimer(timeSeekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }
                @Override
                public void onFinish() {
                    //Log.i("Finish", "All done");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn2);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft) {
        int minute = secondsLeft / 60;
        int second = secondsLeft - (minute * 60); //tak definiujemy sobie czas!! wazne!!
        String secondString = Integer.toString(second);
        if(second <= 9) {
            secondString = "0" + secondString;
        }

        resulTime.setText(minute + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.buttonGo);
        timeSeekBar = findViewById(R.id.seekBarId);
        resulTime = findViewById(R.id.timeViewId);

        timeSeekBar.setMax(600);
        timeSeekBar.setProgress(90);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
