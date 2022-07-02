package com.example.uas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Activity3 extends AppCompatActivity {

    SQLiteDatabase SQLITEDATABASE;
    SQLiteHelper SQLITEHELPER;
    SQLiteListAdapter ListAdapter;

    Cursor cursor;

    ArrayList<String> ID_ArrayList = new ArrayList<>();
    ArrayList<String> TITLE_ArrayList = new ArrayList<>();
    ArrayList<String> azimuth_ArrayList = new ArrayList<>();
    ArrayList<String> pitch_ArrayList = new ArrayList<>();
    ArrayList<String> roll_ArrayList = new ArrayList<>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        listView = findViewById(R.id.list);

        SQLITEHELPER = new SQLiteHelper(this);
    }

    @Override
    protected void onResume() {

        ViewSQLiteDBData();
        super.onResume();
    }

    @SuppressLint("Range")
    private void ViewSQLiteDBData() {
        SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM Nama_Tabel", null);

        ID_ArrayList.clear();
        TITLE_ArrayList.clear();
        azimuth_ArrayList.clear();
        pitch_ArrayList.clear();
        roll_ArrayList.clear();

        if (cursor.moveToFirst()) {
            do {
                ID_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_ID)));
                TITLE_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_TITLE)));
                azimuth_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_azimuth)));
                pitch_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_pitch)));
                roll_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_roll)));
            } while (cursor.moveToNext());
        }

        ListAdapter = new SQLiteListAdapter(Activity3.this, ID_ArrayList, TITLE_ArrayList,azimuth_ArrayList,pitch_ArrayList, roll_ArrayList);

        listView.setAdapter(ListAdapter);
        cursor.close();
    }

    public void MainActivity (View view){
        Intent intent = new Intent(Activity3.this, MainActivity.class);
        startActivity(intent);
    }

    public void Activity2 (View view){
        Intent intent = new Intent(Activity3.this, Activity2.class);
        startActivity(intent);
    }
}