package com.integration.ZoomApiIntegration.Controllers;

import com.integration.ZoomApiIntegration.Constants.Constants;
import com.integration.ZoomApiIntegration.Repository.Meetings;
import com.integration.ZoomApiIntegration.Utils.HttpUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping(value = "/User/")
public class AuthController {

    @GetMapping(value = "/login")
    public String loginPage(Model model) {

        model.addAttribute("clientId", Constants.clientId);
        model.addAttribute("response_type", "code");
        model.addAttribute("redirect_uri", Constants.redirect_uri);
        return "Login";
    }

    @GetMapping(value = "/auth")
    public String getToken(@RequestParam("code") String code, Model model) throws Exception {

        System.setProperty("code", code);
        HashMap<String, String> pathParams = new HashMap<>();
        pathParams.put("grant_type", "authorization_code");
        pathParams.put("code", code);
        pathParams.put("redirect_uri", Constants.redirect_uri);


        String Authorization = Base64.getEncoder()
                .encodeToString((Constants.clientId + ":" + Constants.clientSecret).getBytes());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + Authorization);

        ResponseEntity response = generateAccessToken(headers, pathParams);
        JSONObject authResponse = new JSONObject(response.getBody().toString());
        model.addAttribute("authResponse", authResponse);
        System.setProperty("accessToken", authResponse.getString("access_token"));

        return "User";
    }


    public ResponseEntity<?> generateAccessToken(HashMap<String, String> headers, HashMap<String, String> pathParams) throws Exception {
        String url = Constants.Zoom.zoomOathUrl;
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

        return ResponseEntity.status(HttpStatus.OK).body(EntityUtils.toString(response.getEntity()));
    }

    @GetMapping(value = "/getFutureMeetings")
    public String getFutureMeetings(Model model) throws Exception {
        String type = "scheduled";
        String page_size = "30";
        String page_number = "1";
        HashMap<String, String> pathParams = new HashMap<>();
        pathParams.put("type", type);
        pathParams.put("page_size", page_size);
        pathParams.put("page_number", page_number);
        HashMap<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + System.getProperty("accessToken"));
        String response = HttpUtil.get(Constants.Zoom.zoomApiBaseUrl + Constants.Zoom.getMeetings
                , header, pathParams);

        List<Meetings> meetings = new ArrayList<>();
        JSONObject obj = new JSONObject(response);
        for (int i = 0; i < obj.getJSONArray("meetings").length(); i++) {
            JSONObject m = obj.getJSONArray("meetings").getJSONObject(i);
            Meetings meeting = new Meetings();
            meeting.setId(m.getInt("id"));
            meeting.setTopic(m.getString("topic"));
            meeting.setStart_time(m.getString("start_time"));
            meeting.setJoin_url(m.getString("join_url"));
            meetings.add(meeting);
        }
        model.addAttribute("meetings", meetings);
        return "User";
    }
}
