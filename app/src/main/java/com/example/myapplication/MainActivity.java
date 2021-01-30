package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView timerEditText;
    private Button startButton;

    MediaPlayer mediaPlayer1, mediaPlayer2;

    Boolean isActive = false;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerEditText = findViewById(R.id.timerEditText);
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isActive) {

                    isActive = true;

                    startButton.setText("Reset the Countdown");

                    cretaeMediaPlayer1();

                    countDownTimer = new CountDownTimer(1800000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {

                            long millis = millisUntilFinished;
                            @SuppressLint("DefaultLocale")
                            String hms = String.format("%02d:%02d:%02d",
                                    TimeUnit.MILLISECONDS.toHours(millis),
                                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                            timerEditText.setText(hms);

                            if(mediaPlayer1!=null) {
                                mediaPlayer1.start();
                            }
                        }

                        @Override
                        public void onFinish() {

                            isActive = false;

                            timerEditText.setText("00:00:00");
                            startButton.setText("Start Countdown Timer");

                            stopMediaPlayer1();

                            createMediaPlayer2();
                            if(mediaPlayer2!=null) {
                                mediaPlayer2.start();
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    stopMediaPlayer2();

                                }
                            }, 5000);

                        }
                    }.start();

                }else{

                    isActive = false;
                    countDownTimer.cancel();
                    stopMediaPlayer1();

                    startButton.setText("Start Countdown Timer");
                    timerEditText.setText("00:00:00");

                }

            }
        });

    }

    private void cretaeMediaPlayer1(){

        mediaPlayer1 = MediaPlayer.create(this, R.raw.birds);

    }

    private void createMediaPlayer2(){

        mediaPlayer2 = MediaPlayer.create(this, R.raw.notify);
    }

    private void stopMediaPlayer1(){
        if(mediaPlayer1!=null) {

            mediaPlayer1.stop();
            mediaPlayer1.release();
        }
    }

    private void stopMediaPlayer2(){
        if(mediaPlayer2!=null) {
            mediaPlayer2.stop();
            mediaPlayer2.release();
        }
    }

}