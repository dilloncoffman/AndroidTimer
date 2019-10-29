package edu.temple.timer_10_22_19;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerLabel;
    Button button;

    TimerStates timerState = TimerStates.STOPPED;

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
        button = findViewById(R.id.button);


        // Create an Anonymous Thread class, since it's single use, don't need to go through naming the class
        Thread t = new Thread () {
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
        };

        button.setOnClickListener(v -> { // lambda function, v is the View object on which the event is occurring
            // Change behavior of button to move through various states, button can pause and start, not stop, timer will only stop when time runs out
            if (timerState == TimerStates.STOPPED) {
                timerState = TimerStates.RUNNING;
                button.setText("Pause");
            } else if (timerState == TimerStates.RUNNING) {
                timerState = TimerStates.PAUSED;
                button.setText("Start");
            } else {
                // Timer was PAUSED, but clicked Start
                timerState = TimerStates.RUNNING;
                button.setText("Pause");
            }
        });
    }

    enum TimerStates {STOPPED, RUNNING, PAUSED}

}