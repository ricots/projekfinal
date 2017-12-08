package com.projectzero.renatto.aplikasifinalfix.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.projectzero.renatto.aplikasifinalfix.R;
import com.projectzero.renatto.aplikasifinalfix.app.AppController;
import com.projectzero.renatto.aplikasifinalfix.data.DataRs;

import java.util.List;


public class RsAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataRs> newsItems;
    ImageLoader imageLoader;

    public RsAdapter(Activity activity, List<DataRs> newsItems) {
        this.activity = activity;
        this.newsItems = newsItems;
    }

    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public Object getItem(int location) {
        return newsItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row_rs, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.image);
        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        TextView alamat = (TextView) convertView.findViewById(R.id.alamat);

        DataRs news = newsItems.get(position);

        thumbNail.setImageUrl(news.getImage(), imageLoader);
        nama.setText(news.getNama());
        alamat.setText(news.getAlamat());


        return convertView;
    }

}