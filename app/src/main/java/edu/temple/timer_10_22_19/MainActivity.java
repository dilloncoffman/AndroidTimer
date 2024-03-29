package edu.temple.timer_10_22_19;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerLabel;

    // always import android.os.Handler, not the .util one
    Handler timerHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) { // callback invoked every time a message is received from a worker thread (what is sent from the worker thread)
            timerLabel.setText(String.valueOf(msg.what)); // now Main thread handler can access timerLabel, whereas the worker thread could not
            return false;
        }
    }); // use a Handler that takes a callback object

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
                            // Ask the Main thread via its Handler to do the job for us of counting down
                            timerHandler.sendEmptyMessage(i); // if all you want to send is one integer value
                        }
                    }
                }.start(); // could also just say Thread t = new Thread() and then call t.start() to have a reference to that thread to do something else later
            }
        });
    }
}