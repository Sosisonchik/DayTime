package com.company.sosison.daytime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.company.sosison.daytime.adapter.MyPager;
import com.company.sosison.daytime.fragment.Tasks;
import com.company.sosison.daytime.fragment.TimeTable;
import com.company.sosison.daytime.pojo.Consts;
import com.company.sosison.daytime.pojo.Task;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements Tasks.OnFragmentInteractionListener,TimeTable.OnFragmentInteractionListener {
    TabLayout tabHost;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabHost = (TabLayout)findViewById(R.id.tab_host);
        viewPager = (ViewPager)findViewById(R.id.pager);

        MyPager adapterPager = new MyPager(getSupportFragmentManager());
        viewPager.setAdapter(adapterPager);
        tabHost.setupWithViewPager(viewPager);

        DBhelper dBhelper = new DBhelper(getApplicationContext());
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        Cursor cursor = db.query(DBhelper.TABLE_TASK,null,null,null,null,null,null);

        if (cursor.moveToFirst()){
            Consts.ADAPTER_RECYCLER.clear();
            int taskI = cursor.getColumnIndex(DBhelper.KEY_TASK);
            int timeI = cursor.getColumnIndex(DBhelper.KEY_TIME);
            int checkI = cursor.getColumnIndex(DBhelper.KEY_CHECK);
            int markI = cursor.getColumnIndex(DBhelper.KEY_MARK);

            do {
                boolean check;
                int che = cursor.getInt(checkI);
                String task = cursor.getString(taskI);
                String time = cursor.getString(timeI);
                String mark = cursor.getString(markI);

                if (che == 0)
                    check = false;
                else
                    check = true;

                Integer hour = getHour(time);
                Integer minute = getMinute(time);
                //registerAlarm(hour,minute);

                Task task1 = new Task(task,time,mark,check);
                Consts.ADAPTER_RECYCLER.add(task1);
                if (!Consts.MARKS.contains(mark)){
                    Consts.MARKS.add(mark);
                }

            }while (cursor.moveToNext());
        }

//        Intent push = new Intent(this,PushNotification.class);
//        startService(push);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        DBhelper dBhelper = new DBhelper(getApplicationContext());
//        SQLiteDatabase db = dBhelper.getReadableDatabase();
//        Cursor cursor = db.query(DBhelper.TABLE_TASK,null,null,null,null,null,null);
//
//        if (cursor.moveToFirst()){
//            int timeI = cursor.getColumnIndex(DBhelper.KEY_TIME);
//
//            do {
//                String time = cursor.getString(timeI);
//
//                Integer hour = getHour(time);
//                Integer minute = getMinute(time);
//                registerAlarm(hour,minute);
//            }while (cursor.moveToNext());
//
//        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

//    public void registerAlarm(Integer hour,Integer minute){
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY,hour);
//        calendar.set(Calendar.MINUTE,minute);
//        calendar.set(Calendar.SECOND,0);
//
//        Intent intent = new Intent(MainActivity.this,PushNotification.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
//        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
//    }

    public Integer getHour(String str){
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        for (char ch : chars){
            if (ch != ':'){
                sb.append(ch);
            }else
                return Integer.valueOf(sb.toString());
        }
        return 12;
    }

    public Integer getMinute(String str){
        char[] chars = str.toCharArray();
        for (int i=0;i<chars.length;i++){
            if (chars[i] == ':'){
                return Integer.valueOf(str.substring(i+1));
            }
        }

        return 0;
    }

}
