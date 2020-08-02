package com.myapplication.wishes;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Reg extends AppCompatActivity implements View.OnClickListener {
    public static String user;
    public static String pwd;
    EditText edt1;
    EditText edt2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);

        edt1=(EditText)findViewById(R.id.edt1);

        edt2=(EditText)findViewById(R.id.edt2);
    }

    @Override
    public void onClick(View v) {


        if(edt1.length()>0&&edt2.length()>0){
            user=edt1.getText().toString();
            pwd=edt2.getText().toString();

            Toast.makeText(this,"注册成功！",0).show();
            DataUtilsW.writeString(this,user,pwd);
            finish();
        }
        else Toast.makeText(this,"输入出错！",0).show();





    }
}
