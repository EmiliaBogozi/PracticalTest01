package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    private EditText number_press;
    private Button ok_button, cancel_button;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.okButton:
                    setResult(RESULT_OK, intent);
                    break;
                case R.id.cancelButton:
                    setResult(RESULT_CANCELED, intent);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        number_press = (EditText)findViewById(R.id.numberPress);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.RESULT)) {
            String result_calc = intent.getExtras().getString(Constants.RESULT);
            number_press.setText(result_calc);
        }

        ok_button = (Button)findViewById(R.id.okButton);
        ok_button.setOnClickListener(buttonClickListener);

        cancel_button = (Button)findViewById(R.id.cancelButton);
        cancel_button.setOnClickListener(buttonClickListener);
    }
}