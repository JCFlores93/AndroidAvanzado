package pe.cibertec.analyticsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private EditText edtInput;

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtInput = (EditText) findViewById(R.id.edt_input);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }

    public void onClick(View view){
                Bundle bundle = new Bundle();
        bundle.putString("team",edtInput.getText().toString());
        firebaseAnalytics.logEvent("Send_Click",bundle);
    }
}
