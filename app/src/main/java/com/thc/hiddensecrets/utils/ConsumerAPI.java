package com.thc.hiddensecrets.utils;

import okhttp3.*;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConsumerAPI {

    public static final MediaType JSON = MediaType.get("application/json");
    private String urlBase;

    public ConsumerAPI(String urlBase) {
        this.urlBase = urlBase;
    }

    public Map<Integer, String> loginUser(String route, JSONObject bodyUser) throws IOException {
        String url = urlBase + route;
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(bodyUser.toString(), JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            int statusCode = response.code();
            String token = responseBody.string();
            Map<Integer, String> map = new HashMap<>();
            map.put(statusCode, token);
            return map;

        }

    }

}
