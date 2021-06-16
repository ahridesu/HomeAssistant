package com.ariana.homeassistant.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "routines")
public class Routine {

    @PrimaryKey(autoGenerate = true)
    int ID;

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



    @ColumnInfo(name = "Name")
    String Name;

    @ColumnInfo(name = "bdate")
    String bdate;

    @ColumnInfo(name = "edate")
    String edate;

    @ColumnInfo(name = "btime")
    String btime;

    @ColumnInfo(name = "device")
    String device;

    @ColumnInfo(name = "days")
    String days;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getBtime() {
        return btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDays() {
        return days;
    }

    @ColumnInfo(name = "etime")
    String etime;
}
