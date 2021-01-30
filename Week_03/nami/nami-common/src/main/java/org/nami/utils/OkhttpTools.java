package org.nami.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.nami.exception.NamiException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * OkHttpTools
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class OkhttpTools {

    private static final String HTTP_JSON = "application/json;charset=utf-8";
    private static final Gson GSON = new GsonBuilder().create();
    private static final OkHttpClient CLIENT = buildDefaultOkHttpClient();

    /**
     * send post request with body t
     * application/json
     */
    public static <T> void doPost(String url, T t) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HTTP_JSON), GSON.toJson(t));
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        Call call = CLIENT.newCall(request);
        try {
            Response response = call.execute();
            if (response.code() < 200 || response.code() >= 300) {
                throw new NamiException("request " + url + " fail,http code:" + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new NamiException("request " + url + " fail");
        }
    }

    public static String doPut(String url, Map<String, Object> queryParamMap, String body) {
        String requestUrl;
        if (queryParamMap == null) {
            requestUrl = url;
        } else {

            StringBuilder sb = new StringBuilder(url);
            sb.append("?");
            for (Map.Entry<String, Object> entry : queryParamMap.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue());
                sb.append("&");
            }
            requestUrl = sb.toString();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse(HTTP_JSON), body);
        Request request = new Request.Builder()
                .put(requestBody)
                .url(requestUrl)
                .build();
        Call call = CLIENT.newCall(request);
        try {
            Response response = call.execute();
            if (response.code() < 200 || response.code() >= 300) {
                throw new NamiException("request " + requestUrl + " fail,http code:" + response.code());
            }
            return response.body().string();
        } catch (IOException e) {
            throw new NamiException("request " + requestUrl + " fail");
        }
    }

    public static OkHttpClient buildDefaultOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }
}
