package com.integration.ZoomApiIntegration.Utils;

import com.integration.ZoomApiIntegration.Constants.Constants;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URISyntaxException;
import java.util.HashMap;

public class HttpUtil {

    public static String post(String url, HashMap<String, String> headers, HashMap<String, String> pathParams) throws Exception {
        HttpResponse response = null;
        URIBuilder builder = new URIBuilder(url);
        pathParams.forEach((k, v) -> builder.setParameter(k, v));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost request = new HttpPost(builder.build());
            headers.forEach((k, v) -> request.addHeader(k, v));
            response = httpClient.execute(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.getEntity().toString();
    }

    public static String get(String url, HashMap<String, String> headers, HashMap<String, String> pathParams) throws Exception {

        HttpResponse response = null;
        URIBuilder builder = new URIBuilder(url);
        pathParams.forEach((k, v) -> builder.setParameter(k, v));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(builder.build());
            headers.forEach((k, v) -> request.addHeader(k, v));
            response = httpClient.execute(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return EntityUtils.toString(response.getEntity());
        //return response.getEntity().toString();
    }
}
