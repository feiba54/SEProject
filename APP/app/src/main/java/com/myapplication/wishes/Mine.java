package com.myapplication.wishes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Mine extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ((TextView)findViewById(R.id.edt1)).setText(""+Login.user);
        ((TextView)findViewById(R.id.edt3)).setText(""+Login.pwd);
    }
    @Override
    public void onClick(View view) {
        startActivity(new Intent().setClass(Mine.this,Login.class));
        finish();
    }
}