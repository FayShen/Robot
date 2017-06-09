package com.kefujiqiren.util;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by ShangHen on 2017/6/6.
 */

public class RetrofitUtil {
    public static String IP = "115.196.144.247";
    public static Retrofit retrofit = new Retrofit.Builder()
            //.baseUrl("https://api.douban.com/v2/book/")
            .baseUrl("http://"+IP+":8080/Webtest/")
            .addConverterFactory(ScalarsConverterFactory.create())
            //.addConverterFactory(SimpleXmlConverterFactory.create())
            .build();
}
