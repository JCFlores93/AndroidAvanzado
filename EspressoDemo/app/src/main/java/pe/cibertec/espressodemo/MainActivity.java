package pe.cibertec.espressodemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edtInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtInput = (EditText) findViewById(R.id.edt_input);


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_change_text :
                edtInput.setText("Real Madrid!!!");
                break;

            case  R.id.btn_switch_activity:
                Intent intent = new Intent(this,SecondActivity.class);
                intent.putExtra(SecondActivity.EXTRA_INPUT,edtInput.getText().toString());
                startActivity(intent);
                break;
        }
    }
}
