package com.android.wipro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wipro.R;
import com.android.wipro.model.Rows;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;


public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.DeviceViewHolder> {

    private List<Rows> rows;
    private int rowLayout;
    private Context context;


    public static class DeviceViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mDescription;
        ImageView mImage;


        public DeviceViewHolder(View v) {
            super(v);

            mTitle = (TextView) v.findViewById(R.id.mTitle);
            mDescription = (TextView) v.findViewById(R.id.mDescription);
            mImage = (ImageView) v.findViewById(R.id.mImage);

        }
    }

    public FactsAdapter(List<Rows> rows, int rowLayout, Context context) {
        this.rows = rows;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public FactsAdapter.DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new DeviceViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final DeviceViewHolder holder, final int position) {

        if (!TextUtils.isEmpty(rows.get(position).getTitle()) || !TextUtils.isEmpty(rows.get(position).getDescription()) || !TextUtils.isEmpty(rows.get(position).getImageHref()))

        {
            holder.mTitle.setText(rows.get(position).getTitle());
            holder.mDescription.setText(rows.get(position).getDescription());

            // Image loading using glide library

            Glide.with(context).load(rows.get(position).getImageHref())

                    .centerCrop().listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            })
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(holder.mImage);


        } else {

        }

    }

    @Override
    public int getItemCount() {
        return rows.size();
    }
}