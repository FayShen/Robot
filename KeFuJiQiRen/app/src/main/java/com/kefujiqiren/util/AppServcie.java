package com.kefujiqiren.util;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ShangHen on 2017/5/25.
 */

public interface AppServcie {
    @GET("search")
    Call<String> getResponse(@Query("q") String question);
}
