package com.ariana.homeassistant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ariana.homeassistant.Adapters.DeviceRVAdapter;
import com.ariana.homeassistant.DataBase.AppDB;
import com.ariana.homeassistant.R;
import com.ariana.homeassistant.model.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceActivity extends AppCompatActivity {
    List<Device> deviceList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    AppDB database;
    DeviceRVAdapter deviceRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity);

        EditText editText = findViewById(R.id.Spinner);
        Button addButton = findViewById(R.id.button_add);
        Button resetButton = findViewById(R.id.button_reset);
        RecyclerView recyclerView = findViewById(R.id.entityrv);

        database = AppDB.getInstance(this);

        deviceList = database.mainDao().getAllDevices();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        deviceRvAdapter = new DeviceRVAdapter(this, deviceList);
        recyclerView.setAdapter(deviceRvAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName = editText.getText().toString().trim();
                if(!sName.equals("")){
                    Device device = new Device();
                    device.setName(sName);
                    database.mainDao().insertDevice(device);
                    editText.setText("");

                    deviceList.clear();
                    deviceList.addAll(database.mainDao().getAllDevices());
                    deviceRvAdapter.notifyDataSetChanged();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.mainDao().resetDevices(deviceList);
                deviceList.clear();
                deviceList.addAll(database.mainDao().getAllDevices());
                deviceRvAdapter.notifyDataSetChanged();
            }
        });

    }
}