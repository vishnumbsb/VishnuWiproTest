package com.android.wipro.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.wipro.R;


@SuppressWarnings("ResourceType")
public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    protected Toolbar getActionBarToolbar() {

        if (mToolBar == null) {
            mToolBar = (Toolbar) findViewById(R.id.toolbar);

            if (mToolBar != null) {
                setSupportActionBar(mToolBar);
            }
        }

        return mToolBar;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        getActionBarToolbar();
    }

}
