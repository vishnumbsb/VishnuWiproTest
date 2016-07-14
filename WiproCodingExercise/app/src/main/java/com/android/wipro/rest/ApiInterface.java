package com.android.wipro.rest;


import com.android.wipro.model.FactsResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    @GET("u/746330/facts.json")
    Call<FactsResponse> getDetails();
}
