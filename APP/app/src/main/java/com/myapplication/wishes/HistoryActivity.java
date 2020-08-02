package com.myapplication.wishes;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ListView listView;
    List<String> list;
    ArrayAdapter<String> adapter;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        list=new ArrayList<String>();
        listView=findViewById(R.id.listv);
        adapter=new ArrayAdapter<String>(HistoryActivity.this,R.layout.item,list);
        pos=getIntent().getIntExtra("pos",-1);
        try {
            adddata();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listView.setAdapter(adapter);
        Log.e("ff",pos+"");
    }
    void adddata() throws IOException {
        File dataBaseFile = new File("/data/data/com.myapplication.wishes" + "/databases/", "history.info");
        if(!(new File(dataBaseFile.getParent()).exists()))(new File(dataBaseFile.getParent())).mkdir();
        if(!dataBaseFile.exists())
            dataBaseFile.createNewFile();

        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dataBaseFile, null);
        String sql = "CREATE TABLE " +"IF NOT EXISTS " +DBManager.getDBManager().TABLE_NAME + "(" +"id" + " Integer PRIMARY KEY AUTOINCREMENT," + "con" + " varchar," + "time" + " varchar)";
        sqLiteDatabase.execSQL(sql);
        list.clear();
        Cursor cursor = sqLiteDatabase.query("history", null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String s= cursor.getString(cursor.getColumnIndex("time"))+"\n"+ cursor.getString(cursor.getColumnIndex("con"));
                if(cursor.getInt(cursor.getColumnIndex("id"))==MainActivity.histories.get(pos).getId())
                    list.add(s);
                Log.e("ff",cursor.getInt(cursor.getColumnIndex("id"))+"   "+pos+MainActivity.histories.get(pos).getId()+" "+""+s);
            }
        }
        adapter.notifyDataSetChanged();
    }
}