package com.ariana.homeassistant.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.ariana.homeassistant.Adapters.ButtonAdapter;
import com.ariana.homeassistant.DataBase.AppDB;
import com.ariana.homeassistant.Dialogues.DeviceDialogue;
import com.ariana.homeassistant.Dialogues.RoomDialogue;
import com.ariana.homeassistant.Dialogues.RoutineDialogue;
import com.ariana.homeassistant.R;
import com.ariana.homeassistant.model.Device;
import com.ariana.homeassistant.model.Entity;
import com.ariana.homeassistant.model.Placement;
import com.ariana.homeassistant.model.Room;
import com.ariana.homeassistant.model.Routine;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RoomDialogue.RoomDialogueListner, DeviceDialogue.DeviceDialogueListner, RoutineDialogue.RoutineDialogueListner {
    AppDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<Entity> entities = new ArrayList<>();

        entities.add(new Entity("Rooms", R.drawable.room));
        entities.add(new Entity("Routines", R.drawable.routines));
        entities.add(new Entity("Settings", R.drawable.settings));
        entities.add(new Entity("Devices", R.drawable.devices));

        GridView gridview = (GridView) findViewById(R.id.grid);
        gridview.setAdapter(new ButtonAdapter(this, entities));

        FloatingActionButton room_fab = findViewById(R.id.room_fab);
        FloatingActionButton routines_fab = findViewById(R.id.routines_fab);
        FloatingActionButton device_fab = findViewById(R.id.devices_fab);
        database = AppDB.getInstance(this);
       /* Placement placement = new Placement();
        placement.setDeviceId(2);
        placement.setRoomId(1);
        database.mainDao().insertPlacement(placement);*/

        room_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddRoomDialogue();
            }
        });

        routines_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddRoutineDialogue();
            }
        });

        device_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDeviceDialogue();
            }
        });


    }


    private void openAddRoomDialogue() {
        RoomDialogue roomDialogue = new RoomDialogue();
        roomDialogue.show(getSupportFragmentManager(), "roomDialogue");
    }

    private void openAddRoutineDialogue() {
        RoutineDialogue routineDialogue = new RoutineDialogue();
        routineDialogue.show(getSupportFragmentManager(), "routineDialogue");
    }

    private void openAddDeviceDialogue() {
        DeviceDialogue deviceDialogue = new DeviceDialogue();
        deviceDialogue.show(getSupportFragmentManager(), "deviceDialogue");
    }


    @Override
    public void applyDeviceText(String name) {
        //Toast.makeText(getApplicationContext(), "applyDeviceText: " + name, Toast.LENGTH_LONG).show();
        if (!name.equals("")) {
            Device device = new Device();
            device.setName(name);
            database.mainDao().insertDevice(device);
            Toast.makeText(getApplicationContext(), name+" Added", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void applyRoutineText(String name) {
        //Toast.makeText(getApplicationContext(), "applyRoutineText: " + name, Toast.LENGTH_LONG).show();
        if (!name.equals("")) {
            Routine device = new Routine();
            device.setName(name);
            database.mainDao().insertRoutine(device);
            Toast.makeText(getApplicationContext(), name+" Added", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void applyRoomText(String name) {
        //Toast.makeText(getApplicationContext(), "applyRoomText: " + name, Toast.LENGTH_LONG).show();
        if (!name.equals("")) {
            Room device = new Room();
            device.setName(name);
            database.mainDao().insertRoom(device);
            Toast.makeText(getApplicationContext(), name+" Added", Toast.LENGTH_LONG).show();

        }

    }
}