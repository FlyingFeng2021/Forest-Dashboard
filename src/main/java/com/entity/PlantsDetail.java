package com.entity;

import lombok.Data;

@Data
public class PlantsDetail {
    private String start_time;
    private String end_time;
    private boolean isSucceed;
    private String tag;
    private Integer id;
}
