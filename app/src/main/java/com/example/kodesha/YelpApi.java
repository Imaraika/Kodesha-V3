package com.example.kodesha;

import com.example.kodesha.YelpBusinessRenting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YelpApi {
    @GET("businesses/search")
    Call<YelpBusinessRenting> getHouses(
            @Query("location") String location,
            @Query("term") String term
    );

}
