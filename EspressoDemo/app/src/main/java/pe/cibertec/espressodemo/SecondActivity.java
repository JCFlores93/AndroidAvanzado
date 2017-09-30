package pe.cibertec.espressodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_INPUT = "input";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView txtResult = (TextView) findViewById(R.id.txt_result);
        String input = getIntent().getStringExtra(EXTRA_INPUT);
        txtResult.setText(input);

    }
}
