package com.example.bipl.smsviewer;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fetchInbox("inbox", "+923343278741", "03343278741"));
        lv.setAdapter(adapter);
    }

    public ArrayList fetchInbox(String name, String number1, String number2) {
        ArrayList sms = new ArrayList();

        Uri uriSms = Uri.parse("content://sms/" + name);
        Cursor cursor = getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"}, null, null, null);

        cursor.moveToFirst();
        String address = cursor.getString(1);
        String body = cursor.getString(3);
        Long millis = cursor.getLong(cursor.getColumnIndexOrThrow("date"));

        System.out.println("======&gt; Mobile number =&gt; " + address);
        System.out.println("=====&gt; SMS Text =&gt; " + body);
        Long currentMillis = System.currentTimeMillis();

        int currentHrs = Integer.parseInt(DateFormat.format("hh", new Date(currentMillis)).toString());
        int currentMins = Integer.parseInt(DateFormat.format("mm", new Date(currentMillis)).toString());
        int currentDay = Integer.parseInt(DateFormat.format("dd", new Date(currentMillis)).toString());
        int currentMonth = Integer.parseInt(DateFormat.format("MM", new Date(currentMillis)).toString());
        int currentYear = Integer.parseInt(DateFormat.format("yyyy", new Date(currentMillis)).toString());
        String currentMeridiem = DateFormat.format("aa", new Date(currentMillis)).toString();
        Log.e("CurrentTime>>>", "" + currentHrs + ":" + currentMins + " " + currentMeridiem);
        Log.e("CurrentDate>>>", "" + currentDay + "/" + currentMonth + "/" + currentYear);
        Log.e("date>>>>", String.valueOf(DateFormat.format("MMMM dd, yyyy h:mm aa", new Date(millis))));
        int smsHrs = Integer.parseInt(DateFormat.format("hh", new Date(millis)).toString());
        int smsMins = Integer.parseInt(DateFormat.format("mm", new Date(millis)).toString());
        int smsDay = Integer.parseInt(DateFormat.format("dd", new Date(millis)).toString());
        int smsMonth = Integer.parseInt(DateFormat.format("MM", new Date(millis)).toString());
        int smsYear = Integer.parseInt(DateFormat.format("yyyy", new Date(millis)).toString());
        String smsMeridiem = DateFormat.format("aa", new Date(millis)).toString();
        if (address.equalsIgnoreCase(number1) || address.equalsIgnoreCase(number2)) {
            if (currentYear == smsYear && currentMonth == smsMonth && currentDay == smsDay && currentHrs == smsHrs
                    && currentMins-2 <= smsMins && currentMeridiem.equalsIgnoreCase(smsMeridiem)) {
                sms.add("Address=&gt; " + address + "n SMS =&gt; " + body);
                Toast.makeText(this, "You have pending msg", Toast.LENGTH_SHORT).show();
            }
        }
    /*    while (cursor.moveToNext()) {
            String address = cursor.getString(1);
            String body = cursor.getString(3);

            System.out.println("======&gt; Mobile number =&gt; " + address);
            System.out.println("=====&gt; SMS Text =&gt; " + body);
            if (address.equalsIgnoreCase("+923340246110")) {
                sms.add("Address=&gt; " + address + "n SMS =&gt; " + body);
            }
        }*/
        return sms;

    }
}
