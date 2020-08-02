package com.myapplication.wishes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Check extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        int pos=getIntent().getIntExtra("pos",-1);
        History h=MainActivity.histories.get(pos);
        ((ProgressBar)findViewById(R.id.progressBar)).setMax(h.getTimesall());
        ((ProgressBar)findViewById(R.id.progressBar)).setProgress(h.getTimes());
        Log.e("ff",pos+"");
        ((Button)findViewById(R.id.check)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               h.setTimes(h.getTimes()+1);

                if(h.getTimes()==h.getTimesall()) Toast.makeText(Check.this,"well done!",Toast.LENGTH_SHORT).show();
                ((ProgressBar)findViewById(R.id.progressBar)).setProgress(h.getTimes());

                try {
                    DBManager.getDBManager().update(h,h.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MainActivity.histories.clear();
                try {
                    DBManager.getDBManager().queryAll(null);
                    MainActivity.adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(((EditText)findViewById(R.id.edt)).length()>0){
                    try {
                        add(pos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                startActivity(new Intent().setClass(Check.this,MainActivity.class));
            }
        });
    }
    private void add(int pos) throws IOException {
//        添加数据
//        文件
        File dataBaseFile = new File("/data/data/com.myapplication.wishes" + "/databases/", "history.info");
        if(!(new File(dataBaseFile.getParent()).exists()))(new File(dataBaseFile.getParent())).mkdir();
        if(!dataBaseFile.exists())
            dataBaseFile.createNewFile();
//表不存在则创建
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dataBaseFile, null);
        String sql = "CREATE TABLE " +
                "IF NOT EXISTS " +
                DBManager.getDBManager().TABLE_NAME + "(" +
                "id" + " Integer," +
                "con" + " varchar," +
                "time" + " varchar)";
        sqLiteDatabase.execSQL(sql);

        ContentValues contentValues = new ContentValues();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        contentValues.put("con", ((EditText)findViewById(R.id.edt)).getText().toString());
        contentValues.put("id",MainActivity.histories.get(pos).getId());
        contentValues.put("time", simpleDateFormat.format(date));
        sqLiteDatabase.insert("history", null, contentValues);

        Log.e("insert",MainActivity.histories.get(pos).getId()+"");
    }
}