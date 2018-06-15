package com.company.sosison.daytime.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.company.sosison.daytime.DBhelper;
import com.company.sosison.daytime.MainActivity;
import com.company.sosison.daytime.PushNotification;
import com.company.sosison.daytime.R;
import com.company.sosison.daytime.fragment.Tasks;
import com.company.sosison.daytime.pojo.Consts;
import com.company.sosison.daytime.pojo.Task;

import java.util.Calendar;


public class NoteActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    Button confirm;
    EditText toDo;
    AutoCompleteTextView markView;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        confirm = toolbar.findViewById(R.id.confirm);
        toDo = findViewById(R.id.to_do);
        timePicker = findViewById(R.id.time_picker);

        markView = findViewById(R.id.mark_write_view);
        markView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,Consts.MARKS));

        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = String.valueOf(timePicker.getCurrentHour())+":"+String.valueOf(timePicker.getCurrentMinute());
                String task = toDo.getText().toString();
                String mark = markView.getText().toString();
                if (!task.equals("") && !mark.equals("")){
                    DBhelper dBhelper = new DBhelper(getApplicationContext());
                    SQLiteDatabase db = dBhelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(DBhelper.KEY_TASK,task);
                    cv.put(DBhelper.KEY_TIME,time);
                    cv.put(DBhelper.KEY_CHECK,0);
                    cv.put(DBhelper.KEY_MARK,mark);
                    db.insert(DBhelper.TABLE_TASK,null,cv);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Fields can't be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
