package com.example.inthetech.myapplication;

import android.app.AlertDialog;
import android.app.Notification;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private Menu mMenu;

    myDB my;
    public EditText editText1, editText2;
    public TextView user_name, setting_time;
    public Button button1, button2, button3, button_Main;
    SQLiteDatabase sql;

    public ArrayList<String> mUserNameArrayList = new ArrayList<String>();
    private AlertDialog mUserListDialog;

    String user_name2;
    String setting_time2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my = new myDB(this);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button_Main = (Button) findViewById(R.id.button_Main);


        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);

        user_name = (TextView) findViewById(R.id.textView5);
        setting_time = (TextView) findViewById(R.id.textView6);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql = my.getWritableDatabase();
                my.onUpgrade(sql, 1, 2);
                sql.close();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sql = my.getReadableDatabase();
                Cursor cursor;
                cursor = sql.rawQuery("SELECT * FROM MEMBER;", null);
                user_name2 = "이름" + "\r\n";
                setting_time2 = "시간" + "\r\n";

                while (cursor.moveToNext()) {
                    user_name2 += cursor.getString(0) + "\r\n";
                    setting_time2 += cursor.getString(1) + "\r\n";
                }

                user_name.setText(user_name2);
                setting_time.setText(setting_time2);
                cursor.close();
                sql.close();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql = my.getWritableDatabase();
                sql.execSQL("INSERT INTO member VALUES('" + editText1.getText().toString()
                        + "','" + editText2.getText().toString() + "');"
                );
                sql.close();
                Toast.makeText(getApplicationContext(), "정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        button_Main.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), NextActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.show_dialog) {
            initUserListDialog();
            mUserListDialog.show();
            return true;
        } else {

            return true;
        }
    }

    private void initUserListDialog() {
        mUserNameArrayList.clear();

        sql = my.getReadableDatabase();
        Cursor cursor;
        cursor = sql.rawQuery("SELECT * FROM MEMBER;", null);


        while (cursor.moveToNext()) {
            user_name2 += cursor.getString(0) + "\r\n";
            setting_time2 += cursor.getString(1) + "\r\n";
            mUserNameArrayList.add(cursor.getString(0) + "\n" + cursor.getString(1));
        }

        final String[] items = mUserNameArrayList.toArray(new String[mUserNameArrayList.size()]);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select User");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mUserListDialog = builder.create();
        mUserListDialog.setCanceledOnTouchOutside(false);
    }

}
