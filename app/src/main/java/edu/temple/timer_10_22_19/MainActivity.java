package edu.temple.timer_10_22_19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    Log.d("Countdown",String.valueOf(i));
                }
            }
        }.start(); // could also just say Thread t = new Thread() and then call t.start() to have a reference to that thread to do something else later
    }
}