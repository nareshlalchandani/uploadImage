package com.marsplay.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.marsplay.demo.R;
import com.marsplay.demo.response.model.ImagesResponseModel;
import com.marsplay.demo.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naresh on 10/12/18.
 */

public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ImagesResponseModel> dataList;
    private Context context;

    public ImagesAdapter(Context context) {
        dataList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_images, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder _holder, int position) {

        ImagesResponseModel model = dataList.get(position);

        ViewHolder holder = (ViewHolder) _holder;

        //Utility.loadImage(model.getImage_Url(), holder.imgFile);
        Utility.loadImageWithoutBase(model.getImage_Url(), holder.imgFile);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFile;

        ViewHolder(View itemView) {
            super(itemView);
            imgFile = itemView.findViewById(R.id.img_file);
        }
    }

    public void addItem(List<ImagesResponseModel> data) {

        if (data != null) {

            dataList.addAll(data);

            notifyDataSetChanged();
        }
    }

    public void clear() {

        if (dataList != null) {
            dataList.clear();
        }
    }

    public boolean isEmpty() {
        return dataList.isEmpty();
    }

    public ImagesResponseModel getObject(int position) {
        return dataList.get(position);
    }
}