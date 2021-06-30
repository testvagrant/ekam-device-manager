package com.testvagrant.ekam.devicemanager.remote.browserstack;

import com.testvagrant.ekam.commons.cache.DataCache;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.BrowserStackDeviceClient;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.shuffle;

public class BrowserStackDeviceCache extends DataCache<List<TargetDetails>> {

    private String username;
    private String accessKey;

    public BrowserStackDeviceCache(String username, String accessKey) {
        super();

        this.username = username;
        this.accessKey = accessKey;
    }

    public void put() {
        List<TargetDetails> targetDetails = getTargetDetails();
        put("browserstack.devices", targetDetails);
    }

    public List<TargetDetails> get() {
        return get("browserstack.devices").orElse(new ArrayList<>());
    }

    private List<TargetDetails> getTargetDetails() {
        List<TargetDetails> browserStackDevices = new ArrayList<>();
        BrowserStackDeviceClient browserStackDeviceClient = new BrowserStackDeviceClient(username, accessKey);
        browserStackDevices.addAll(browserStackDeviceClient.getAndroidDevices());
        browserStackDevices.addAll(browserStackDeviceClient.getIosDevices());
        shuffle(browserStackDevices);
        return browserStackDevices;
    }
}
