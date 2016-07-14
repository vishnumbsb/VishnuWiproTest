package com.android.wipro.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.wipro.R;
import com.android.wipro.adapter.FactsAdapter;
import com.android.wipro.model.FactsResponse;
import com.android.wipro.model.Rows;
import com.android.wipro.rest.ApiClient;
import com.android.wipro.rest.ApiInterface;
import com.android.wipro.util.Utils;
import com.android.wipro.widget.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements MaterialDialog.MaterialDialogListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MaterialDialog mDialog;

    private SwipeRefreshLayout mSwipeContainer;

    private RecyclerView mRecyclerView;

    private List<Rows> rows;

    private Handler mHandler = new Handler();

    private TextView mtvToolBarTitle;

    private RelativeLayout mRLNoRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add toolbar.
        final Toolbar toolbar = getActionBarToolbar();
        if (toolbar != null) {

            mtvToolBarTitle = (TextView) toolbar.findViewById(R.id.mtvToolBarTitle);
        }

        initWidget();

        setListener();

        mSwipeContainer.post(new Runnable() {
                                 @Override
                                 public void run() {


                                     if (Utils.isOnline(getApplicationContext())) {

                                         mSwipeContainer.setRefreshing(true);
                                         makeHttpCall();

                                     } else {
                                         mDialog.withTitle("Alert")
                                                 .withMessage(R.string.no_connection_error)
                                                 .withTag("50")
                                                 .isButton2Visible(View.INVISIBLE)
                                                 .show();
                                     }


                                 }
                             }
        );


    }

    private void initWidget() {

        mDialog = MaterialDialog.getInstance(MainActivity.this);

        mRLNoRecord = (RelativeLayout) findViewById(R.id.mRLNoRecord);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lookup the swipe container view
        mSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        rows = new ArrayList<>();
    }

    private void setListener() {

        // Setup refresh listener which triggers new data loading
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.

                if (Utils.isOnline(getApplicationContext())) {

                    makeHttpCall();

                } else {
                    mDialog.withTitle("Alert")
                            .withMessage(R.string.no_connection_error)
                            .withTag("50")
                            .isButton2Visible(View.INVISIBLE)
                            .show();
                }


            }
        });
        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }

    private void makeHttpCall() {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<FactsResponse> call = apiService.getDetails();

        call.enqueue(new Callback<FactsResponse>() {
            @Override
            public void onResponse(Call<FactsResponse> call, final Response<FactsResponse> response) {
                int statusCode = response.code();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mtvToolBarTitle.setText(response.body().getTitle());
                    }
                });

                rows.clear();
                rows = response.body().getRows();
                if (rows.size() > 0) {
                    mRecyclerView.setAdapter(new FactsAdapter(rows, R.layout.list_item_facts, getApplicationContext()));

                    Log.d("Rows Size: ", "" + rows.size() + " " + rows.get(0).getTitle() + ", " + rows.get(0).getDescription());
                } else {
                    mRLNoRecord.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }

                mSwipeContainer.setRefreshing(false);


            }

            @Override
            public void onFailure(Call<FactsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                mSwipeContainer.setRefreshing(false);


            }
        });


    }


    @Override
    public void onDialogPositiveClick(Dialog dialog, int tagid) {

        if (tagid == 50) {
            finish();
        }
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(Dialog dialog, int tagid) {
        dialog.dismiss();
    }
}
