package com.ariana.homeassistant.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "devices")
public class Device {
    @PrimaryKey(autoGenerate = true)
    int ID;

    @ColumnInfo(name = "Name")
    String Name;

    @ColumnInfo(name = "isOn")
    int isOn;

    public int getIsOn() {
        return isOn;
    }

    public void setIsOn(int isOn) {
        this.isOn = isOn;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
