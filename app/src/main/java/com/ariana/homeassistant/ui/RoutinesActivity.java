package com.ariana.homeassistant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ariana.homeassistant.Adapters.RoutinesRVAdapter;
import com.ariana.homeassistant.DataBase.AppDB;
import com.ariana.homeassistant.R;
import com.ariana.homeassistant.model.Device;
import com.ariana.homeassistant.model.Routine;

import java.util.ArrayList;
import java.util.List;

public class RoutinesActivity extends AppCompatActivity {
    List<Routine> deviceList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    AppDB database;
    RoutinesRVAdapter deviceRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        EditText editText = findViewById(R.id.Spinner);
        EditText bDtate = findViewById(R.id.bdate);
        EditText eDate = findViewById(R.id.edate);
        EditText bTime = findViewById(R.id.btime);
        EditText eTime = findViewById(R.id.etime);
        Spinner spinner = findViewById(R.id.devices_spinner);


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
                String sBdate = bDtate.getText().toString().trim();
                String sEdate = eDate.getText().toString().trim();
                String sBTime = bTime.getText().toString().trim();
                String sETime = eTime.getText().toString().trim();
                String DeviceName = spinner.getSelectedItem().toString();

                if(!sName.equals("") && !sBdate.equals("") &&
                        !sEdate.equals("") && !sBTime.equals("")
                        && !sETime.equals("") && deviceNames.size()!=0){

                    Routine device = new Routine();
                    device.setName(sName);
                    device.setBdate(sBdate);
                    device.setBtime(sBTime);
                    device.setEdate(sEdate);
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