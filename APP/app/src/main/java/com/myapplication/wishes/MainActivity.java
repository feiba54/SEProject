package com.myapplication.wishes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static MyAdapter adapter;
    public static List<History> histories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        histories=new ArrayList<History>();

        adapter =new MyAdapter(histories);
        try {
            adddata();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ListView listv=(ListView) findViewById(R.id.listv);
        listv.setAdapter(adapter);
        listv.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final EditText inputServer = new EditText(MainActivity.this);
                inputServer.setText("   完成情况："+histories.get(position).getTimes()+"/"+histories.get(position).getTimesall());
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(inputServer)
                        .setNegativeButton("To Do", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent();
                                intent.putExtra("pos",position);
                                intent.setClass(MainActivity.this,Check.class);
                                if(histories.get(position).getTimes()==histories.get(position).getTimesall()) {
                                    Toast.makeText(MainActivity.this, "you have finished", Toast.LENGTH_SHORT).show();
                                }
                                else
                                startActivity(intent);
                            }
                        });
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent();
                        intent.putExtra("pos",position);
                        intent.setClass(MainActivity.this,Edit.class);
                        startActivity(intent);
                    }
                });
                builder.setNeutralButton("Done", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent();
                        intent.putExtra("pos",position);
                        intent.setClass(MainActivity.this,HistoryActivity.class);
                        startActivity(intent);


                    }
                });
                builder.show();
            }
        });



    }


    private void adddata() throws IOException {
        histories.clear();
        DBManager.getDBManager().createTable();
        DBManager.getDBManager().queryAll(null);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//菜单创建，没啥用
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.add){
            startActivity(new Intent().setClass(MainActivity.this,Edit.class));


        } else if(id==R.id.mine){

            startActivity(new Intent().setClass(MainActivity.this,Mine.class));

        }

            else startActivity(new Intent().setClass(MainActivity.this,DataActivity.class));









        return super.onOptionsItemSelected(item);
    }



    public  class MyAdapter extends BaseAdapter {
        private List<History> infos;
        private LayoutInflater inflater;

        public MyAdapter(List<History> infos) {
            super();
            this.infos = infos;
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {

            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int i=0;
            View view = inflater.inflate(R.layout.item, null);
            TextView tv_number = (TextView) view.findViewById(R.id.textView);
            History info = infos.get(position);
            tv_number.setText(info.getTitle());
            if(info.getTimesall()<=info.getTimes())tv_number.setTextColor(Color.RED);
            return view;
        }
    }
}