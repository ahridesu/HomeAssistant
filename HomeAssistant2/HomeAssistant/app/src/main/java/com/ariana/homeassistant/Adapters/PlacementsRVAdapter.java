package com.ariana.homeassistant.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ariana.homeassistant.DataBase.AppDB;
import com.ariana.homeassistant.R;
import com.ariana.homeassistant.model.Placement;
import com.ariana.homeassistant.model.Room;
import com.ariana.homeassistant.ui.RoomDevicesActivity;

import java.util.List;


public class PlacementsRVAdapter extends RecyclerView.Adapter<PlacementsRVAdapter.ViewHolder> {
    private List dataList;
    private Activity context;
    private AppDB database;

    public PlacementsRVAdapter(Activity context , List dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_room_device_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlacementsRVAdapter.ViewHolder holder, int position) {
        Placement data = (Placement) dataList.get(position);
        database = AppDB.getInstance(context);
        holder.textView.setText((database.mainDao().getDevicebyID(data.getDeviceId()).getName()));

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Placement placement = (Placement) dataList.get(holder.getAdapterPosition());
                database.mainDao().deletePlacement(placement);


                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView btEdit , btDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.rv_text);
            btEdit = itemView.findViewById(R.id.rv_edit);
            btDelete = itemView.findViewById(R.id.rv_delete);
        }
    }
}
