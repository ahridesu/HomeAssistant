package com.ariana.homeassistant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ariana.homeassistant.Adapters.RoutinesRVAdapter;
import com.ariana.homeassistant.DataBase.AppDB;
import com.ariana.homeassistant.R;
import com.ariana.homeassistant.model.Device;
import com.ariana.homeassistant.model.Routine;
import com.dpro.widgets.OnWeekdaysChangeListener;
import com.dpro.widgets.WeekdaysPicker;
import com.dpro.widgets.WeekdaysPicker;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class RoutinesActivity extends AppCompatActivity {
    List<Routine> deviceList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    AppDB database;
    RoutinesRVAdapter deviceRvAdapter;
    TextView btime, etime;
    int t1h,t1min,t2h,t2min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);



        EditText editText = findViewById(R.id.Spinner);
        Spinner spinner = findViewById(R.id.devices_spinner);

        btime = findViewById(R.id.btime);
        etime = findViewById(R.id.etime);
        WeekdaysPicker widget = (WeekdaysPicker) findViewById(R.id.weekdays);
        btime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        RoutinesActivity.this ,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                                t1h = hourOfDay;
                                t1min = minute;
                                String time = t1h +":"+t1min;
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    btime.setText(f12Hours.format(date));
                                }catch (ParseException e){
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t1h,t1min);
                timePickerDialog.show();
            }
        });
        etime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        RoutinesActivity.this ,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                                t2h = hourOfDay;
                                t2min = minute;
                                String time = t2h +":"+t2min;
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    btime.setText(f12Hours.format(date));
                                }catch (ParseException e){
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t2h,t2min);
                timePickerDialog.show();
            }
        });
        String start = Integer.toString(t1h) +":"+Integer.toString(t1min);
        String finish = Integer.toString(t2h) +":"+Integer.toString(t2min);


        Button addButton = findViewById(R.id.button_add);
        Button resetButton = findViewById(R.id.button_reset);
        RecyclerView recyclerView = findViewById(R.id.entityrv);

        database = AppDB.getInstance(this);

        deviceList = database.mainDao().getAllRoutines();
        database = AppDB.getInstance(this);

        ArrayList<Device> alldevice = (ArrayList<Device>) database.mainDao().getAllDevices();

        ArrayList<String> deviceNames = new ArrayList<>();
        for (Device deviceName:alldevice) {

            deviceNames.add(deviceName.getName());
        }
        if(deviceNames.size() == 0){
            Toast.makeText(this,"Please Add Devices First to Choose from",Toast.LENGTH_LONG).show();
        }else{
            // Toast.makeText(this,"Found"+ deviceNames.size(),Toast.LENGTH_LONG).show();

        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, deviceNames);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        deviceRvAdapter = new RoutinesRVAdapter(this, deviceList);
        recyclerView.setAdapter(deviceRvAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName = editText.getText().toString().trim();

                List<String> selectedDays = widget.getSelectedDaysText();
                String weekdays = "";
                for(String s : selectedDays){
                    weekdays += s+",";
                }

                String sBTime = start;
                String sETime = finish;
                String DeviceName = spinner.getSelectedItem().toString();

                if(!sName.equals("") && /*!sBdate.equals("") &&
                        !sEdate.equals("") &&*/ !sBTime.equals("")
                        && !sETime.equals("") && deviceNames.size()!=0 && !weekdays.equals("")){

                    Routine device = new Routine();
                    device.setName(sName);
                    device.setDays(weekdays);
                    device.setBtime(sBTime);

                    device.setEtime(sETime);
                    device.setDevice(DeviceName);
                    database.mainDao().insertRoutine(device);
                    //editText.setText("");

                    deviceList.clear();
                    deviceList.addAll(database.mainDao().getAllRoutines());
                    deviceRvAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getApplicationContext(),"Please Fill all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.mainDao().resetRoutines(deviceList);
                deviceList.clear();
                deviceList.addAll(database.mainDao().getAllRoutines());
                deviceRvAdapter.notifyDataSetChanged();
            }
        });

    }
}