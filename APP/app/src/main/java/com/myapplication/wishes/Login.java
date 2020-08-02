package com.myapplication.wishes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    public static String user;
    public static String pwd;
    EditText edt1;
    EditText edt2;
    boolean bool;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt1=(EditText)findViewById(R.id.edt1);
        edt2=(EditText)findViewById(R.id.edt2);
    }

    @Override
    public void onClick(View v) {
        user=edt1.getText().toString();
        pwd=edt2.getText().toString();
        if(user.equals("admin")&&pwd.equals("admin"))bool=true;

        if(edt1.length()>0&&edt2.length()>0)if(DataUtilsW.readString(this,user)!=null)
            if((DataUtilsW.readString(this,user).equals(pwd)))bool=true;
            else bool=false;


        if(v.getId()==R.id.login){
            if(!bool)
                Toast.makeText(this,"用户不存在或密码错误",0).show();
            else startActivity(new Intent().setClass(this,MainActivity.class));
            //if(bool)finish();
        }


        else{

            startActivity(new Intent().setClass(this, Reg.class));


        }
        bool=false;
    }
}
