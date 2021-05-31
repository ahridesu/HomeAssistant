package com.ariana.homeassistant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.homeassistant.Adapters.PlacementsRVAdapter;
import com.ariana.homeassistant.DataBase.AppDB;
import com.ariana.homeassistant.R;
import com.ariana.homeassistant.model.Device;
import com.ariana.homeassistant.model.Placement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RoomDevicesActivity extends AppCompatActivity {
    List<Placement> deviceList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    AppDB database;
    PlacementsRVAdapter deviceRvAdapter;
    int currentRoomId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_devices);


        currentRoomId = Integer.parseInt(getIntent().getStringExtra("RoomID"));
        Spinner spinner = findViewById(R.id.Spinner);
        Button addButton = findViewById(R.id.button_add);
        Button resetButton = findViewById(R.id.button_reset);
        RecyclerView recyclerView = findViewById(R.id.entityrv);
        TextView title = findViewById(R.id.RoomDevicesTitle);

        database = AppDB.getInstance(this);

        deviceList = database.mainDao().getAllRoomDevices(currentRoomId);
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

        title.setText( (database.mainDao().getRoomById(currentRoomId)).getName() + " Devices");
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        deviceRvAdapter = new PlacementsRVAdapter(this, deviceList);
        recyclerView.setAdapter(deviceRvAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deviceNames.size() != 0){
                    int text = spinner.getSelectedItemPosition() + 1;

                    Placement placement = new Placement();
                    placement.setRoomId(currentRoomId);
                    placement.setDeviceId(text);
                    database.mainDao().insertPlacement(placement);

                    deviceList.clear();
                    deviceList.addAll(database.mainDao().getAllRoomDevices(currentRoomId));
                    deviceRvAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getApplicationContext(),"Please Add Devices First to Choose from",Toast.LENGTH_LONG).show();

                }

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database.mainDao().resetPlacements(database.mainDao().getAllRoomDevices(currentRoomId));

                deviceList.clear();
                deviceList.addAll(database.mainDao().getAllRoomDevices(currentRoomId));
                deviceRvAdapter.notifyDataSetChanged();
            }
        });

    }
}