package com.marsplay.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        init();

        callAPI();
    }

    /**
     * Bind view
     */
    private void init() {

        setUpActionBarWithUpButton();
        setActionBarTitle("Uploaded Images");

        txtErrorMessage = findViewById(R.id.txt_error_message);
        txtErrorMessage.setVisibility(View.GONE);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new ImagesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                //Call zoom image activity
                Intent intent = new Intent(ImagesListActivity.this, ZoomImageActivity.class);
                intent.putExtra(ZoomImageActivity.FIELD_IMAGE_URL, adapter.getObject(position).getImage_Url());
                startActivity(intent);
            }
        }));

        refreshLayout = findViewById(R.id.swipe_view);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAPI();
            }
        });
    }

    private void callAPI() {

        //Show refresh view
        refreshLayout.setRefreshing(true);

        ServiceManager manager = new ServiceManager(this, this);
        manager.getImagesAPI();
    }

    @Override
    public void onRequestComplete(Object data, int caller) {

        if (caller == ServiceCallBacks.IMAGES) {

            refreshLayout.setRefreshing(false);

            ImagesResponse response = new Gson().fromJson((String) data, ImagesResponse.class);

            if (response.getResponse().isSuccess()) {

                adapter.clear();
                adapter.addItem(response.getData());

            } else {
                adapter.clear();
                adapter.notifyDataSetChanged();
            }

            updateUI("No Images Found");
        }
    }

    @Override
    public void onError(String errorString, int caller) {

        refreshLayout.setRefreshing(false);
        updateUI(errorString);
    }

    @Override
    public void onRequestCancel(String errorString, int caller) {

        refreshLayout.setRefreshing(false);
        updateUI(errorString);
    }

    /*Update error message based on list items*/
    private void updateUI(String message) {

        if (adapter.isEmpty()) {

            txtErrorMessage.setVisibility(View.VISIBLE);
            txtErrorMessage.setText(message);

        } else {
            txtErrorMessage.setVisibility(View.GONE);
        }
    }
}

