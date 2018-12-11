package com.marsplay.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.marsplay.demo.R;
import com.marsplay.demo.adapter.ImagesAdapter;
import com.marsplay.demo.net.ServiceCallBacks;
import com.marsplay.demo.net.ServiceManager;
import com.marsplay.demo.response.ImagesResponse;
import com.marsplay.demo.ui.base.BaseActivity;
import com.marsplay.demo.utils.RecyclerItemClickListener;

public class ImagesListActivity extends BaseActivity implements ServiceCallBacks {

    private TextView txtErrorMessage;
    private ImagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        init();

        callAPI();
    }

    private void init() {

        setUpActionBarWithUpButton();
        setActionBarTitle("Uploaded Images");

        txtErrorMessage = (TextView) findViewById(R.id.txt_error_message);
        txtErrorMessage.setVisibility(View.GONE);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new ImagesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(ImagesListActivity.this, ZoomImageActivity.class);
                intent.putExtra(ZoomImageActivity.FIELD_IMAGE_URL, adapter.getObject(position).getImageUrl());
                startActivity(intent);
            }
        }));
    }

    private void callAPI() {

        ServiceManager manager = new ServiceManager(this, this);
        manager.getImagesAPI();
    }

    @Override
    public void onRequestComplete(Object data, int caller) {

        if (caller == ServiceCallBacks.IMAGES) {

            ImagesResponse response = new Gson().fromJson((String) data, ImagesResponse.class);

            if (response.getResponse().isSuccess()) {

                adapter.clear();
                adapter.addItem(response.getData());

            } else {
                adapter.clear();
            }

            updateUI(response.getResponse().getMessage());
        }
    }

    @Override
    public void onError(String errorString, int caller) {
        updateUI(errorString);
    }

    @Override
    public void onRequestCancel(String errorString, int caller) {
        updateUI(errorString);
    }

    private void updateUI(String message) {

        if (adapter.isEmpty()) {

            txtErrorMessage.setVisibility(View.VISIBLE);
            txtErrorMessage.setText(message);

        } else {
            txtErrorMessage.setVisibility(View.GONE);
        }
    }
}

