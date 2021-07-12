package com.testvagrant.ekam.devicemanager.remote;

import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import okhttp3.Interceptor;

import java.time.Duration;

public class RemoteCloudClient extends RetrofitBaseClient {

    public RemoteCloudClient(String baseUrl, Interceptor... interceptor) {
        super(baseUrl, interceptor);
    }

    public RemoteCloudClient(String baseUrl, Duration readTimeout, Duration connectTimeout, Interceptor... interceptor) {
        super(baseUrl, readTimeout, connectTimeout, interceptor);
    }
}
