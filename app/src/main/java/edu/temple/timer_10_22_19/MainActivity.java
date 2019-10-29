package edu.temple.timer_10_22_19;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerLabel;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerLabel = findViewById(R.id.timerLabel);
        button = findViewById(R.id.button);

        // create instance of AsyncTask and execute it, because I cannot execute same AsyncTask twice, don't even need to hold a reference to it, only need reference with cancel operation
        button.setOnClickListener(v -> new TimerAsyncTask().execute(50));
    }

    // provide 3 data types when defining AsyncTask,
    // 1st is type of data that will be passed to AsyncTask for it to operate on, this data will be available in doInBackground, -
    // 2nd arg will be data passed in any call to publishProgress and therefore will be the argument received by onUpdateProgress - what you're putting in the .what() for Message object in this case
    // the 3rd arg will be the type returned by doInBackground which will be the type received by onPostExecute() - print a message in our case when everything is done in this case
    class TimerAsyncTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // where you can set up your display for whatever reason
            timerLabel.setText("Click button to Start"); // first thing to happen when AsyncTask executes
        }

        @Override
        protected String doInBackground(Integer... integers) {
            // Takes list of integers specified in generic for AsyncTask above, the 3 argument data types
            for (int i = 20; integers[i] >= 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            timerLabel.setText(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            timerLabel.setText(s);
        }
    }

}