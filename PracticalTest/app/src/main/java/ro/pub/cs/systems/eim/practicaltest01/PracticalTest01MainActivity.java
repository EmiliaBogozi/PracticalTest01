package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private Button press1, press2, navigateToSecondaryActivity;
    private EditText editText1, editText2;

    private int serviceStatus = Constants.SERVICE_STOPPED;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int count1, count2;
            count1 = Integer.parseInt(editText1.getText().toString());
            count2 = Integer.parseInt(editText2.getText().toString());

            if(v.getId() == R.id.pressMe1) {
                count1++;
                editText1.setText(String.valueOf(count1));
            }
            if(v.getId() == R.id.pressMe2) {
                count2++;
                editText2.setText(String.valueOf(count2));
            }
            if(v.getId() == R.id.navigate_to_secondary_activity) {
                int n1, n2;
                n1 = Integer.parseInt(editText1.getText().toString());
                n2 = Integer.parseInt(editText2.getText().toString());

                Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                intent.putExtra(Constants.RESULT, String.valueOf(n1+n2));
                startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
            }
            if(count1 + count2 > Constants.NUMBER_OF_CLICKS_THRESHOLD && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra(Constants.FIRST_NUMBER, count1);
                intent.putExtra(Constants.SECOND_NUMBER, count2);
                getApplicationContext().startService(intent);

                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        Log.d(Constants.TAG, "onCreate() method was invoked");

        editText1 = (EditText)findViewById(R.id.count1);
        editText1.setOnClickListener(buttonClickListener);
        editText1.setText("0");

        editText2 = (EditText)findViewById(R.id.count2);
        editText2.setOnClickListener(buttonClickListener);
        editText2.setText("0");

        press1 = (Button)findViewById(R.id.pressMe1);
        press1.setOnClickListener(buttonClickListener);

        press2 = (Button)findViewById(R.id.pressMe2);
        press2.setOnClickListener(buttonClickListener);

        navigateToSecondaryActivity = (Button)findViewById(R.id.navigate_to_secondary_activity);
        navigateToSecondaryActivity.setOnClickListener(buttonClickListener);

        intentFilter.addAction(Constants.INTENT_ACTION);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(Constants.TAG, "onSaveInstanceState() method was invoked");

        savedInstanceState.putString(Constants.FIRST_NUMBER, editText1.getText().toString());
        savedInstanceState.putString(Constants.SECOND_NUMBER, editText2.getText().toString());

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(Constants.TAG, "onRestoreInstanceState() method was invoked");
        if(savedInstanceState.containsKey(Constants.FIRST_NUMBER))
            editText1.setText(savedInstanceState.getString(Constants.FIRST_NUMBER));
        if(savedInstanceState.containsKey(Constants.SECOND_NUMBER))
            editText2.setText(savedInstanceState.getString(Constants.SECOND_NUMBER));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Constants.TAG, "onRestart() method was invoked");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Constants.TAG, "onStart() method was invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Constants.TAG, "onStop() method was invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Constants.TAG, "onResume() method was invoked");
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
        Log.d(Constants.TAG, "onPause() method was invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG, "onDestroy() method was invoked");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }
}