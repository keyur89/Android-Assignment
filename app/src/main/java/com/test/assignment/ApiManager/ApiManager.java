package com.test.assignment.ApiManager;


import com.test.assignment.BuildConfig;
import com.test.assignment.Model.TravelDataModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Keyur Tailor on 01-Nov-17.
 */

public class ApiManager {

    // Please select the product flavor using build variants accordingly.
    public final static String BASE_URL = BuildConfig.HOST;
    private static ApiManager instance = null;
    private static Map<String, String> headers = new HashMap<>();
    public Retrofit retrofit;
    private ApiModule apiModule;

    public ApiManager() {
        final TimeZone tz = TimeZone.getDefault();
        //OkHttpClient client = new OkHttpClient();
        try {
            OkHttpClient.Builder okClient = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
                okClient.addInterceptor(interceptor);// adding HttpLoggingInterceptor
            }


            okClient.addInterceptor(
                    new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {

                            // Request customization: add request headers
                            Request.Builder builder = chain.request().newBuilder();
                            if (headers != null) {
                                for (Map.Entry<String, String> entry : headers.entrySet()) {
                                    builder.addHeader(entry.getKey(), entry.getValue());
                                }
                            }

                            Request request = builder.build();
                            return chain.proceed(request);
                        }
                    });


            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiManager.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okClient.build())
                    .build();
            //return  retrofit.create(ApiManager.class);
            apiModule = retrofit.create(ApiModule.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    public ApiModule getService() {
        return apiModule;
    }

    public ApiModule getService(String token) {
        return apiModule;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    /**
     * Add multiple headers
     */
    public void addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public void removeHeader(String key) {
        headers.remove(key);
    }

    /**
     * Remove all headers
     */
    public void clearHeaders() {
        headers.clear();
    }

    /**
     * Get all headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    public static void setHeaders(Map<String, String> headers) {
        ApiManager.headers = headers;
    }

    public interface ApiModule {

        @GET("sample.json")
        Call<ArrayList<TravelDataModel>> getSampleData();

    }

}
