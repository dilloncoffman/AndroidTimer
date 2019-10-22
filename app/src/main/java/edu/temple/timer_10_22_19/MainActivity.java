package edu.temple.timer_10_22_19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerLabel = findViewById(R.id.timerLabel);

        findViewById((R.id.startButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Anonymous Thread class, since it's single use, don't need to go through naming the class
                new Thread () {
                    @Override
                    public void run() {
                        for (int i = 20; i >= 0; i--){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            //Log.d("Countdown",String.valueOf(i));
                            timerLabel.setText(String.valueOf(i));
                        }
                    }
                }.start(); // could also just say Thread t = new Thread() and then call t.start() to have a reference to that thread to do something else later
            }
        });
    }
}