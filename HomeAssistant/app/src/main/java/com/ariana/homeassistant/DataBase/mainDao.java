package com.ariana.homeassistant.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ariana.homeassistant.model.Device;
import com.ariana.homeassistant.model.Placement;
import com.ariana.homeassistant.model.Room;
import com.ariana.homeassistant.model.Routine;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface mainDao {

    @Insert(onConflict = REPLACE)
    void insertRoom(Room room);

    @Delete
    void deleteRoom(Room room);

    @Delete
    void resetRooms(List<Room> roomList);

    @Query("Update rooms SET name = :sText Where ID = :sID")
    void updateRoom(int sID,String sText);

    @Query("SELECT * from rooms")
    List<Room> getAllRooms();

    @Query("SELECT * from rooms Where ID = :sID")
    Room getRoomById(int sID);


    //Devices
    @Insert(onConflict = REPLACE)
    void insertDevice(Device device);

    @Delete
    void deleteDevice(Device device);

    @Delete
    void resetDevices(List<Device> deviceList);

    @Query("Update devices SET name = :sText Where ID = :sID")
    void updateDevice(int sID,String sText);

    @Query("Update devices SET isOn = :isOn Where ID = :sID")
    void turnDeviceOn(int sID,int isOn);

    @Query("SELECT * from devices")
    List<Device> getAllDevices();

    @Query("SELECT * from devices Where ID = :sID")
    Device getDevicebyID(int sID);

    //Routines
    @Insert(onConflict = REPLACE)
    void insertRoutine(Routine routine);

    @Delete
    void deleteRoutine(Routine routine);

    @Delete
    void resetRoutines(List<Routine> routineList);

    @Query("Update routines SET name = :sText Where ID = :sID")
    void updateRoutine(int sID,String sText);

    @Query("SELECT * from routines")
    List<Routine> getAllRoutines();

    //Placement
    @Insert(onConflict = REPLACE)
    void insertPlacement(Placement placement);

    @Delete
    void deletePlacement(Placement placement);

    @Query("SELECT * from placement Where RoomId = :roomID")
    List<Placement> getAllRoomDevices(int roomID);

    @Query("SELECT * from placement Where RoomId = :roomID AND DeviceId = :deviceID")
    Placement getRoomDeviceById(int roomID ,int deviceID);

    @Delete
    void resetPlacements(List<Placement> placements);

}
