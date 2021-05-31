package com.ariana.homeassistant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ariana.homeassistant.ui.DeviceActivity;
import com.ariana.homeassistant.ui.MainActivity;
import com.ariana.homeassistant.R;
import com.ariana.homeassistant.ui.RoomsActivity;
import com.ariana.homeassistant.ui.RoutinesActivity;
import com.ariana.homeassistant.model.Entity;

import java.util.ArrayList;

public class ButtonAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Entity> mEntities;

    public ButtonAdapter(MainActivity mainActivity, ArrayList<Entity> entities) {
        mContext = mainActivity;
        mEntities = entities;
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.grid_item, null);

        TextView tv = v.findViewById(R.id.RoomDevicesTitle);
        ImageView iv = v.findViewById(R.id.imageView);

        tv.setText(mEntities.get(position).getText());
        iv.setImageResource(mEntities.get(position).getImage());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Intent i = new Intent(mContext, RoomsActivity.class);
                    mContext.startActivity(i);
                } else if (position == 1) {
                    Intent i = new Intent(mContext, RoutinesActivity.class);
                    mContext.startActivity(i);
                } else if (position == 3) {
                    Intent i = new Intent(mContext, DeviceActivity.class);
                    mContext.startActivity(i);
                }
            }
        });

        return v;
    }
}
