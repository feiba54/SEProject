package com.myapplication.wishes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static volatile DBManager dbManager;
    private SQLiteDatabase sqLiteDatabase;
    public static boolean isCheck=false;
    public String TABLE_NAME = "history";
    private DBManager() throws IOException {
        String hash=Login.user+Login.pwd;
        String code=Integer.toString(hash.hashCode());
        TABLE_NAME="history"+code;
        openDataBase();
        createTable();
    }

    public static DBManager getDBManager() throws IOException {
        if (dbManager == null) {
            synchronized (DBManager.class) {
                if (dbManager == null) {
                    dbManager = new DBManager();
                }
            }
        }
        return dbManager;
    }

    /**
     * 数据库名称
     */
    private final String DATABASE_NAME = "info.db";
    /**
     * 表名
     */


    /**
     * 表格所包含的字段
     */
    private class HistoryDbColumn {

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String TIMES = "times";
        public static final String STATE = "state";
        public static final String CONTENT = "content";
        public static final String TIME = "time";
        public static final String TIMESALL= "timesall";
    }

    /**
     * 1.创建或打开数据库连接
     **/
    private void openDataBase() throws IOException {
        File dataBaseFile = new File("/data/data/com.myapplication.wishes" + "/databases/", DATABASE_NAME);
        if(!(new File(dataBaseFile.getParent()).exists()))(new File(dataBaseFile.getParent())).mkdir();
        if(!dataBaseFile.exists())
            dataBaseFile.createNewFile();

        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dataBaseFile, null);
    }

    /****
     * 2.创建表
     */
    public void createTable() {
        String hash=Login.user+Login.pwd;
        String code=Integer.toString(hash.hashCode());
        TABLE_NAME="history"+code;
        String sql = "CREATE TABLE " +
                "IF NOT EXISTS " +
                TABLE_NAME + "(" +
                HistoryDbColumn.ID + " Integer PRIMARY KEY AUTOINCREMENT," +
                HistoryDbColumn.CONTENT + " varchar," +
                HistoryDbColumn.TITLE + " varchar," +
                HistoryDbColumn.TIMES + " Integer," +
                HistoryDbColumn.TIMESALL + " Integer," +
                HistoryDbColumn.STATE + " Integer," +
                HistoryDbColumn.TIME + " varchar)";
        sqLiteDatabase.execSQL(sql);
    }

    /**
     * 插入一条数据
     *
     * @param history
     * @return
     */
    public long insert(History history) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(HistoryDbColumn.CONTENT, history.getContent());
        contentValues.put(HistoryDbColumn.TIME, history.getTime());
        contentValues.put(HistoryDbColumn.TITLE, history.getTitle());
        contentValues.put(HistoryDbColumn.TIMES, history.getTimes());
        contentValues.put(HistoryDbColumn.TIMESALL, history.getTimesall());
        contentValues.put(HistoryDbColumn.STATE, history.getState());
        long num = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return num;
    }

    /**
     * 根据id删除一条数据
     *
     * @param id
     * @return
     */
    public long delete(int id) {
        long num = sqLiteDatabase.delete(TABLE_NAME, HistoryDbColumn.ID + "=?", new String[]{String.valueOf(id)});
        return num;
    }

    /**
     * 根据id修改一条数据
     *
     * @param id
     * @return
     */
    public long update(History history, int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(HistoryDbColumn.CONTENT, history.getContent());
        contentValues.put(HistoryDbColumn.TIME, history.getTime());
        contentValues.put(HistoryDbColumn.TITLE, history.getTitle());
        contentValues.put(HistoryDbColumn.TIMES, history.getTimes());
        contentValues.put(HistoryDbColumn.TIMESALL, history.getTimesall());
        contentValues.put(HistoryDbColumn.STATE, history.getState());
        long num = sqLiteDatabase.update(TABLE_NAME, contentValues, HistoryDbColumn.ID + "=?", new String[]{String.valueOf(id)});
        return num;
    }

    /**
     * 根据id查询一条数据
     *
     * @param id
     * @return
     */
    public History qurey(int id) {
        History history = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, HistoryDbColumn.ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                history = new History();
                history.setId(cursor.getInt(cursor.getColumnIndex(HistoryDbColumn.ID)));
                history.setContent(cursor.getString(cursor.getColumnIndex(HistoryDbColumn.CONTENT)));
                history.setTime(cursor.getString(cursor.getColumnIndex(HistoryDbColumn.TIME)));
            }
        }

        return history;
    }

    /**
     * 根据id查询一条数据
     * 倒序
     *
     * @return
     */
    public List<History> queryAll(String sss) {
        /*String hash=Login.user+Login.pwd;
        String code=Integer.toString(hash.hashCode());
        TABLE_NAME=TABLE_NAME+code;
         */
        List<History> historys = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                History history = new History();
                history.setId(cursor.getInt(cursor.getColumnIndex(HistoryDbColumn.ID)));
                history.setContent(cursor.getString(cursor.getColumnIndex(HistoryDbColumn.CONTENT)));
                history.setTime(cursor.getString(cursor.getColumnIndex(HistoryDbColumn.TIME)));
                history.setState(cursor.getInt(cursor.getColumnIndex(HistoryDbColumn.STATE)));
                history.setTitle(cursor.getString(cursor.getColumnIndex(HistoryDbColumn.TITLE)));
                history.setTimesall(cursor.getInt(cursor.getColumnIndex(HistoryDbColumn.TIMESALL)));
                history.setTimes(cursor.getInt(cursor.getColumnIndex(HistoryDbColumn.TIMES)));
                if(sss==null)
                MainActivity.histories.add(history);
                else DataActivity.histories.add(history);
            }
        }
        return historys;
    }

    /**
     * 根据内容查询一条数据
     *
     * @return
     */
    public History queryByContent(String content) {
        History history = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, HistoryDbColumn.CONTENT + "=?", new String[]{content}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                history = new History();
                history.setId(cursor.getInt(cursor.getColumnIndex(HistoryDbColumn.ID)));
                history.setContent(cursor.getString(cursor.getColumnIndex(HistoryDbColumn.CONTENT)));
                history.setTime(cursor.getString(cursor.getColumnIndex(HistoryDbColumn.TIME)));
            }
        }
        return history;
    }
}