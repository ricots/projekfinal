package com.projectzero.renatto.aplikasifinalfix.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projectzero.renatto.aplikasifinalfix.R;
import com.projectzero.renatto.aplikasifinalfix.model.DataModel;

import java.util.List;



public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataModel> item;

    public Adapter(Activity activity, List<DataModel> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);

        TextView txt_nama = (TextView) convertView.findViewById(R.id.txt_nama);
        TextView txt_alamat = (TextView) convertView.findViewById(R.id.txt_alamat);

        txt_nama.setText(item.get(position).getNama());
        txt_alamat.setText(item.get(position).getAlamat());

        return convertView;
    }
}