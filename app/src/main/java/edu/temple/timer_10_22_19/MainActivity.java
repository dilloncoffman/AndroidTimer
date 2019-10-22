package edu.temple.timer_10_22_19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 20; i >= 0; i--) {
            Log.d("Countdown", String.valueOf(i));
        }
    }
}
