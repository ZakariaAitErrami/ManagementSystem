package com.example.managementsystem.FragementMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managementsystem.Classes.Marchandise;
import com.example.managementsystem.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter <ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Marchandise> mUploads;
    public ImageAdapter(Context context, List<Marchandise> uploads){
        mContext = context;
        mUploads =uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.merchandise_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Marchandise uploadCurrent = mUploads.get(position);
        holder.textviewBarCode.setText(uploadCurrent.getReference());
        holder.textPrice.setText(uploadCurrent.getPrice());
        holder.textDescription.setText(uploadCurrent.getDescription());
       Picasso.with(mContext).load(uploadCurrent.getmImageUrl()).fit().centerCrop().into(holder.imageView);
      //  Picasso.with(mContext).load(uploadCurrent.getmImageUrl()).into(holder.imageView);
        //.placeholder(R.mipmap.ic_launcher)
        //.fit().placeholder(R.mipmap.ic_launcher)
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textviewBarCode;
        public TextView textDescription;
        public TextView textPrice;
        public ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textviewBarCode =itemView.findViewById(R.id.barcode);
            textDescription = itemView.findViewById(R.id.desc);
            imageView = itemView.findViewById(R.id.image_view_upload);
            textPrice = itemView.findViewById(R.id.prix);
        }
    }
}
