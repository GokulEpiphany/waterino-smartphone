package ai.eloop.waterino;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gokulakrishnanm on 15/04/19.
 */

public class ApiManager {

    // interface containing HTTP methods as given by Retrofit
    // static adapter to be used in entire app
    static volatile Retrofit retroFitAdapter = null;
    //dynamic endpoints if needed to change base url
    private static String  hostName;

    public ApiManager() {

    }



    public static Retrofit getAdapter() {
        if (retroFitAdapter == null) {
            synchronized (ApiManager.class) {
                if (retroFitAdapter == null) {
                    hostName = DefaultValueConstants.hostName;

                    OkHttpClient httpClient = new OkHttpClient().newBuilder()
                            .connectTimeout(600, TimeUnit.SECONDS)
                            .writeTimeout(600, TimeUnit.SECONDS)
                            .readTimeout(600,TimeUnit.SECONDS)
                            .build();
                    retroFitAdapter = new Retrofit.Builder().baseUrl(DefaultValueConstants.hostName).addConverterFactory(GsonConverterFactory.create()).client(httpClient).build();

                }
            }
        }
        return retroFitAdapter;
    }


}
