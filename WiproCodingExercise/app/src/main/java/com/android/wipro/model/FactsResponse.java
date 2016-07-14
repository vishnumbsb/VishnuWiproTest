package com.android.wipro.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FactsResponse {

    @SerializedName("title")
    private String title;

    @SerializedName("rows")
    private List<Rows> rows;


    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
