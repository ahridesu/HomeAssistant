package com.ariana.homeassistant.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "placement")
public class Placement {
    @PrimaryKey(autoGenerate = true)
    int ID;

    @ColumnInfo(name = "RoomId")
    int RoomId;

    @ColumnInfo(name = "DeviceId")
    int DeviceId;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRoomId() {
        return RoomId;
    }

    public void setRoomId(int roomId) {
        RoomId = roomId;
    }

    public int getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(int deviceId) {
        DeviceId = deviceId;
    }

    @Override
    public String toString() {
        return  String.valueOf(DeviceId);

    }
}
