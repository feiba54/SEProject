package com.myapplication.wishes;
import java.util.Random;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {

    public static MyAdapter adapter;
    public static List<History> histories;
    private PieChart pieChart;
    int num=0;
    int done=0;
    int bucket_sum=0;
    int[] bucket_count= new int[100];
    String[] bucket_name= new String[100];
    String[] rgb={"F9D98E","C9FAE3","565E20","75CCA4","A85A11","FFCF77","FE93EA","764101","027466","0369C0"};
    int[] RR =new int[10];
    int[] G =new int[10];
    int[] B= new int[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        histories=new ArrayList<History>();
       ((TextView)findViewById(R.id.tvall)).setVisibility(View.VISIBLE);
        adapter =new MyAdapter(histories);

        try {
            adddata();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ListView listv=(ListView) findViewById(R.id.listv);
        listv.setAdapter(adapter);
        for(int i=0;i<10;i++){
            RR[i]=(int)Long.parseLong(rgb[i].substring(0,2),16);
            G[i]=(int)Long.parseLong(rgb[i].substring(2,4),16);
            B[i]=(int)Long.parseLong(rgb[i].substring(4,6),16);
        }


            pieChart= (PieChart) findViewById(R.id.consume_pie_chart);
            pieChart.setUsePercentValues(true);//设置value是否用显示百分数,默认为false
            pieChart.setDescription("心愿可视化");//设置描述
            pieChart.setDescriptionTextSize(20);//设置描述字体大小

            pieChart.setExtraOffsets(5,5,5,5);//设置饼状图距离上下左右的偏移量

            pieChart.setDrawCenterText(true);//是否绘制中间的文字
            pieChart.setCenterTextColor(Color.RED);//中间的文字颜色
            pieChart.setCenterTextSize(15);//中间的文字字体大小

            pieChart.setDrawHoleEnabled(true);//是否绘制饼状图中间的圆
            pieChart.setHoleColor(Color.WHITE);//饼状图中间的圆的绘制颜色
            pieChart.setHoleRadius(40f);//饼状图中间的圆的半径大小

            pieChart.setTransparentCircleColor(Color.BLACK);//设置圆环的颜色
            pieChart.setTransparentCircleAlpha(100);//设置圆环的透明度[0,255]
            pieChart.setTransparentCircleRadius(40f);//设置圆环的半径值

            // enable rotation of the chart by touch
            pieChart.setRotationEnabled(true);//设置饼状图是否可以旋转(默认为true)
            pieChart.setRotationAngle(10);//设置饼状图旋转的角度

            pieChart.setHighlightPerTapEnabled(true);//设置旋转的时候点中的tab是否高亮(默认为true)

            //右边小方框部分
            Legend l = pieChart.getLegend(); //设置比例图
            l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);//设置每个tab的显示位置（这个位置是指下图右边小方框部分的位置 ）
//        l.setForm(Legend.LegendForm.LINE);  //设置比例图的形状，默认是方形
            l.setXEntrySpace(0f);
            l.setYEntrySpace(0f);//设置tab之间Y轴方向上的空白间距值
            l.setYOffset(0f);

            //饼状图上字体的设置
            // entry label styling
            pieChart.setDrawEntryLabels(true);//设置是否绘制Label
            pieChart.setEntryLabelColor(Color.BLACK);//设置绘制Label的颜色
            pieChart.setEntryLabelTextSize(10f);//设置绘制Label的字体大小

//        pieChart.setOnChartValueSelectedListener(this);//设值点击时候的回调
            pieChart.animateY(3400, Easing.EasingOption.EaseInQuad);//设置Y轴上的绘制动画
            ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
            for(int i=0;i<histories.size();i++) {
                pieEntries.add(new PieEntry(bucket_count[i], bucket_name[i]+" "+bucket_count[i]+"次"));
            }
            String centerText = "共计打卡心愿\n"+bucket_sum+"次";
            pieChart.setCenterText(centerText);//设置圆环中间的文字
            PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
            ArrayList<Integer> colors = new ArrayList<Integer>();
            for(int i=0;i<histories.size();i++) {
                colors.add(Color.rgb(RR[i%10], G[i%10], B[i%10]));
            }
            // 饼图颜色
            pieDataSet.setColors(colors);

            pieDataSet.setSliceSpace(0f);//设置选中的Tab离两边的距离
            pieDataSet.setSelectionShift(5f);//设置选中的tab的多出来的
            PieData pieData = new PieData();
            pieData.setDataSet(pieDataSet);

            //各个饼状图所占比例数字的设置
            pieData.setValueFormatter(new PercentFormatter());//设置%
            pieData.setValueTextSize(12f);
            pieData.setValueTextColor(Color.BLUE);

            pieChart.setData(pieData);
            // undo all highlights
            pieChart.highlightValues(null);
            pieChart.invalidate();


    }


    private void adddata() throws IOException {
        num=0;done=0;
        histories.clear();
        DBManager.getDBManager().queryAll("  ");
        adapter.notifyDataSetChanged();


        num=histories.size();
        for(int i=0;i<histories.size();i++){
            if(histories.get(i).getTimesall()<=histories.get(i).getTimes())done++;
            bucket_count[i]=histories.get(i).getTimes();
            bucket_name[i]=histories.get(i).getTitle();
            bucket_sum=bucket_sum+bucket_count[i];
        }

        ((TextView)findViewById(R.id.tvall)).setText("已完成心愿"+done+"个，占比"+(100*done/num)+"%\n"+"未完成心愿"+(num-done)+"个，占比"+(100-100*done/num)+"%");

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
            startActivity(new Intent().setClass(DataActivity.this,Edit.class));


        }
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
            num++;

            View view = inflater.inflate(R.layout.item, null);
            TextView tv_number = (TextView) view.findViewById(R.id.textView);
            History info = infos.get(position);
            tv_number.setText("心愿"+info.getTitle()+"已打卡"+info.getTimes()+"次，"+"总进度"+(100*info.getTimes()/info.getTimesall())+"%");

            return view;

        }

    }
}