package edu.temple.timer_10_22_19;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            if (msg.what >= 0) {
                timerLabel.setText(String.valueOf(msg.what)); // now Main thread handler can access timerLabel, whereas the worker thread could not
            } else {
                timerState = TimerStates.STOPPED; // set timer state to stopped after loop finishes
                button.setText("Start"); // set button text back to start once it's stopped
            }
            return false;
        }
    }); // use a Handler that takes a callback object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerLabel = findViewById(R.id.timerLabel);
        button = findViewById(R.id.button);

        button.setOnClickListener(v -> { // lambda function, v is the View object on which the event is occurring
            // Change behavior of button to move through various states, button can pause and start, not stop, timer will only stop when time runs out
            if (timerState == TimerStates.STOPPED) {
                timerState = TimerStates.RUNNING;
                button.setText("Pause");
                // Create an a new thread and start on each button press if it was stopped
                Thread t = new Thread () {
                    @Override
                    public void run() {
                        for (int i = 20; i >= 0; i--){

                            while(timerState == TimerStates.PAUSED); // Spin-lock

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            // Ask the Main thread via its Handler to do the job for us of counting down
                            timerHandler.sendEmptyMessage(i); // if all you want to send is one integer value
                        }
                        // Send message to main UI thread to update view to start button
                        timerHandler.sendEmptyMessage(-1);
                    }
                };
                t.start();
            } else if (timerState == TimerStates.RUNNING) {
                timerState = TimerStates.PAUSED;
                button.setText("Start");
            } else {
                // Timer was PAUSED, but clicked Start
                timerState = TimerStates.RUNNING; // set UI thread will set to running which would get us out o Spin-lock above for background thread timer
                button.setText("Pause");
            }
        });
    }

    enum TimerStates {STOPPED, RUNNING, PAUSED}

}