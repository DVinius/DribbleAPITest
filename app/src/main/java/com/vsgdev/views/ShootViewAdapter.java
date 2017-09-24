package com.vsgdev.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vsgdev.models.Shoot;

import java.util.List;

/**
 * Created by franklin soares on 23/09/17.
 */

public class ShootViewAdapter extends ArrayAdapter<Shoot> {
    private final Context context;
    private final List<Shoot> values;

    public ShootViewAdapter(Context context, List<Shoot> values){
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ShootViewHolder shootHolder;

        if (convertView == null) {
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_item_layout, null);
            shootHolder = new ShootViewHolder();
            shootHolder.tvTitle = convertView.findViewById(R.id.shootTitle);
            shootHolder.tvShootViewsCount = convertView.findViewById(R.id.shootViewCount);
            shootHolder.tvCreatedAt = convertView.findViewById(R.id.shootCreatedAt);
            shootHolder.ivShootImage = convertView.findViewById(R.id.shootImage);

            convertView.setTag(shootHolder);
        } else {
            shootHolder = (ShootViewHolder) convertView.getTag();
        }

        final Shoot shoot = values.get(position);
        Picasso.with(context)
                .load(shoot.getTeaserImageUrl())
                .placeholder(R.mipmap.downloading_img)
                .into(shootHolder.ivShootImage);

        shootHolder.tvTitle.setText(shoot.getTitle());
        shootHolder.tvShootViewsCount.setText(String.valueOf(shoot.getViewCount()));
        shootHolder.tvCreatedAt.setText(shoot.getCreatedAt());

        return convertView;
    }

    static class ShootViewHolder {
        TextView tvTitle;
        ImageView ivShootImage;
        TextView tvShootViewsCount;
        TextView tvCreatedAt;
    }
}
