package com.myapplication.wishes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class Edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        int pos=getIntent().getIntExtra("pos",-1);
        if(pos<0){
            ((Button)findViewById(R.id.del)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.save)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    History h=new History();
                    h.setTitle( ((EditText)findViewById(R.id.edt_title)).getText().toString());
                    h.setContent( ((EditText)findViewById(R.id.edt_con)).getText().toString());

                    h.setTimesall(Integer.parseInt(((EditText)findViewById(R.id.edt_timesall)).getText().toString()));
                    h.setTime( ((EditText)findViewById(R.id.edt_time)).getText().toString());
                    try {
                        h.setId((int) DBManager.getDBManager().insert(h));
                        MainActivity.histories.add(h);
                        MainActivity.adapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(Edit.this,"done",Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }else{
            History h=MainActivity.histories.get(pos);
            ((EditText)findViewById(R.id.edt_title)).setText(h.getTitle());
             ((EditText)findViewById(R.id.edt_con)).setText(h.getContent());
            ((EditText)findViewById(R.id.edt_timesall)).setText(h.getTimesall()+"");
            ((EditText)findViewById(R.id.edt_time)).setText(h.getTime());

            ((Button)findViewById(R.id.del)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        DBManager.getDBManager().delete(MainActivity.histories.get(pos).getId());
                        MainActivity.histories.clear();
                        DBManager.getDBManager().queryAll(null);
                        MainActivity.adapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(Edit.this,"done",Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            ((Button)findViewById(R.id.save)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    h.setTitle( ((EditText)findViewById(R.id.edt_title)).getText().toString());
                    h.setContent( ((EditText)findViewById(R.id.edt_con)).getText().toString());

                    h.setTimesall(Integer.parseInt(((EditText)findViewById(R.id.edt_timesall)).getText().toString()));
                    h.setTime( ((EditText)findViewById(R.id.edt_time)).getText().toString());
                    try {
                        DBManager.getDBManager().update(h,h.getId());
                        MainActivity.histories.clear();
                        DBManager.getDBManager().queryAll(null);
                        MainActivity.adapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(Edit.this,"done",Toast.LENGTH_SHORT).show();
                    finish();
                }
            });



        }

    }
}