package com.ariana.homeassistant.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ariana.homeassistant.DataBase.AppDB;
import com.ariana.homeassistant.R;
import com.ariana.homeassistant.model.Device;

import java.util.List;


public class DeviceRVAdapter extends RecyclerView.Adapter<DeviceRVAdapter.ViewHolder> {
    private List dataList;
    private Activity context;
    private AppDB database;

    public DeviceRVAdapter(Activity context, List dataList) {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_device_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DeviceRVAdapter.ViewHolder holder, int position) {
        Device data = (Device) dataList.get(position);
        database = AppDB.getInstance(context);

        holder.textView.setText(data.getName());
        holder.rv_OnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.rv_OnOff.getTag()== "OFF") {
                    holder.rv_OnOff.setChecked(true);
                    holder.rv_OnOff.setTag("ON");
                    Device device = (Device) dataList.get(holder.getAdapterPosition());
                    database.mainDao().turnDeviceOn(device.getID(),1);
                } else {
                    holder.rv_OnOff.setChecked(false);
                    holder.rv_OnOff.setTag("OFF");
                    Device device = (Device) dataList.get(holder.getAdapterPosition());
                    database.mainDao().turnDeviceOn(device.getID(),0);

                }
            }
        });
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device device = (Device) dataList.get(holder.getAdapterPosition());

                int sID = device.getID();

                String sName = device.getName();

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width, height);
                dialog.show();

                EditText editText = dialog.findViewById(R.id.dialog_edit);
                Button buttonUpdate = dialog.findViewById(R.id.dialog_btnupdate);

                editText.setText(sName);
                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String updated_text = editText.getText().toString().trim();
                        database.mainDao().updateDevice(sID, updated_text);

                        dataList.clear();
                        dataList.addAll(database.mainDao().getAllDevices());
                        notifyDataSetChanged();
                    }
                });

            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device device = (Device) dataList.get(holder.getAdapterPosition());
                database.mainDao().deleteDevice(device);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView btEdit, btDelete;
        Switch rv_OnOff;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.rv_text);
            btEdit = itemView.findViewById(R.id.rv_edit);
            btDelete = itemView.findViewById(R.id.rv_delete);
            rv_OnOff = itemView.findViewById(R.id.rv_OnOff);
            rv_OnOff.setTag("OFF");
        }
    }
}
