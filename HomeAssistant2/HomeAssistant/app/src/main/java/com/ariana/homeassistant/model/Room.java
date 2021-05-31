package com.ariana.homeassistant.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "rooms")
public class Room implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int ID;

    @ColumnInfo(name = "name")
    String Name;

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
