package com.apurata.prestamos.creditos.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.apurata.prestamos.creditos.R;
import com.apurata.prestamos.creditos.RequestModels.CoordActivity;
import com.apurata.prestamos.creditos.RequestModels.DataFromValidation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;


public class GodModeActivity extends AppCompatActivity {

    TextView textView;
    DataFromValidation itemToRender;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_god_mode);


        Bundle bundle = getIntent().getBundleExtra("bundle");
        DataFromValidation item = bundle.getParcelable("item");
        Log.i("item", "" + item);
        ArrayList<CoordActivity> itemList = getIntent().getExtras().getParcelableArrayList("list");
        itemToRender = item;
        itemToRender.setSignedPaperPhoto("" + item.getSignedPaperPhoto().length());
        itemToRender.setDniPhoto("" + item.getDniPhoto().length());
        itemToRender.setCoordActivity(itemList);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(itemToRender);
        textView = (TextView) findViewById(R.id.txtMainJson);
        textView.setText(json);


    }
}
