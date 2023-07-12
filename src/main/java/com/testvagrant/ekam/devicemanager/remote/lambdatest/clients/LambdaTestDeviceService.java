package com.testvagrant.ekam.devicemanager.remote.lambdatest.clients;

import com.testvagrant.ekam.devicemanager.remote.lambdatest.clients.responses.LambdaTestDeviceDetails;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface LambdaTestDeviceService {

    @GET("api/v1/list")
    Call<List<LambdaTestDeviceDetails>> lambdaTestDevices();
}
