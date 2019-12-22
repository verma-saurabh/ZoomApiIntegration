package com.integration.ZoomApiIntegration.Repository;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meetings {
    private  int Id;
    private String topic;
    private String start_time;
    private String join_url;
}
