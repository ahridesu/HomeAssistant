package com.ariana.homeassistant.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ariana.homeassistant.DataBase.AppDB;
import com.ariana.homeassistant.R;
import com.ariana.homeassistant.model.Device;
import com.ariana.homeassistant.model.Room;
import com.ariana.homeassistant.ui.RoomDevicesActivity;

import java.util.List;


public class RoomsRVAdapter extends RecyclerView.Adapter<RoomsRVAdapter.ViewHolder> {
    private List dataList;
    private Activity context;
    private AppDB database;

    public RoomsRVAdapter(Activity context , List dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RoomsRVAdapter.ViewHolder holder, int position) {
        Room data = (Room) dataList.get(position);
        database = AppDB.getInstance(context);

        holder.textView.setText(data.getName());
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Room device = (Room) dataList.get(holder.getAdapterPosition());

                int sID = device.getID();

                String sName = device.getName();

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width,height);
                dialog.show();

                EditText editText = dialog.findViewById(R.id.dialog_edit);
                Button buttonUpdate = dialog.findViewById(R.id.dialog_btnupdate);

                editText.setText(sName);
                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String updated_text = editText.getText().toString().trim();
                        database.mainDao().updateRoom(sID,updated_text);

                        dataList.clear();
                        dataList.addAll(database.mainDao().getAllRooms());
                        notifyDataSetChanged();
                    }
                });

            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Room device = (Room) dataList.get(holder.getAdapterPosition());
                database.mainDao().resetPlacements(database.mainDao().getAllRoomDevices(device.getID()));
                database.mainDao().deleteRoom(device);

                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RoomDevicesActivity.class);
                Room room = (Room) dataList.get(holder.getAdapterPosition());
                i.putExtra("RoomID",String.valueOf(room.getID()));
                context.startActivity(i);
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
