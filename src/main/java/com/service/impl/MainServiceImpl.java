package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.MyHashMap;
import com.dto.UserDto;
import com.entity.*;
import com.mapper.MainMapper;
import com.service.MainService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class MainServiceImpl implements MainService {

    @Autowired
    private MainMapper mapper;

    Boolean isLogin = false;
    Boolean databaseInited=false;
    String token;
    String userId;

    @Override
    public List<Plants> getAllTrees() {
        List<Plants> plantsList = new ArrayList<>();
        if (isLogin) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders requestHeaders = new HttpHeaders();
            HttpHeaders headers = new HttpHeaders();
            String tokenStr = "remember_token=" + token;
            headers.set("Cookie", tokenStr);
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.67");
            // 注意几个请求参数
            HttpEntity<String> res = restTemplate
                    .exchange("https://forest-china.upwardsware.com/api/v1/plants?seekrua=extension_chrome-5.12.0&from_date=1970-01-01T00:00:00.000Z"
                            , HttpMethod.GET, new HttpEntity<>(null, headers),
                            String.class);
            HttpEntity<String> tag_res = restTemplate
                    .exchange("https://forest-china.upwardsware.com/api/v1/tags?seekrua=extension_chrome-5.12.0"
                            , HttpMethod.GET, new HttpEntity<>(null, headers),
                            String.class);
            JSONArray objs = JSONArray.parseArray(res.getBody());
            MyHashMap myHashMap = new MyHashMap();
            JSONArray tag_objs = JSONArray.parseArray(JSONObject.parseObject(tag_res.getBody()).getString("tags"));
            String[] tagArray = new String[50];
            if(!databaseInited)mapper.delData();
            if (tag_objs != null) {
                for (int i = 0; i < tag_objs.size(); i++) {
                    String title = tag_objs.getJSONObject(i).get("title").toString();
                    String tag_id = tag_objs.getJSONObject(i).get("tag_id").toString();
                    int tagId = Integer.parseInt(tag_id);
                    tagArray[tagId] = title;
                }
            }
            if (objs != null) {
                for (int i = 0; i < objs.size(); i++) {
                    String startTime = objs.getJSONObject(i).get("start_time").toString();
                    String endTime = objs.getJSONObject(i).get("end_time").toString();
                    int tag = objs.getJSONObject(i).getIntValue("tag");
                    DateFormat dft = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
                    try {
                        Date start = dft.parse(startTime);//开始时间
                        Date endDay = dft.parse(endTime);//结束时间
                        Long starTime = start.getTime();
                        Long endtime = endDay.getTime();
                        int time = (int) ((endtime - starTime) / 60 / 1000);//时间戳相差的毫秒数
                        myHashMap.put(tag, time);
                        DetailsData detailsData = new DetailsData();
                        detailsData.setTag(tagArray[tag]);
                        detailsData.setStart_time(startTime);
                        detailsData.setEnd_time(endTime);
                        detailsData.setTime(time);
                        if(!databaseInited){
                            mapper.initData(detailsData);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                myHashMap.forEach((key, value) -> {
                    Plants plants = new Plants();
                    plants.setValue(value);
                    plants.setName(tagArray[key]);
                    plantsList.add(plants);
                });
                if(!databaseInited)databaseInited=true;
            } else return null;
        } else return null;
        return plantsList;
    }

    @Override
    public Details getUserDetails() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String tokenStr = "remember_token=" + token;
        headers.set("Cookie", tokenStr);
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.67");
        HttpEntity<String> res = restTemplate
                .exchange("https://forest-china.upwardsware.com/api/v1/users/"+userId+"?seekrua=extension_chrome-5.12.0"
                        , HttpMethod.GET, new HttpEntity<>(null, headers),
                        String.class);
        Object objs = JSON.parse(res.getBody());
        if (objs != null) {
            String obj = objs.toString();
            JSONObject jsonObject = JSONObject.parseObject(obj);
            String coin = jsonObject.getString("coin");
            String trees = jsonObject.getString("health_tree_count");
            String timeInMinutes = jsonObject.getString("total_minutes");
            String gems = jsonObject.getString("gem");
            String avatar = jsonObject.getString("avatar");
            String name = jsonObject.getString("name");
            Details details = new Details();
            details.setCoins(coin);
            details.setTrees(trees);
            details.setGems(gems);
            details.setTimeInMinutes(timeInMinutes);
            details.setAvatar(avatar);
            details.setName(name);
            return details;
        } else return null;
    }

    @Override
    public List<DetailsData> page(QueryInfo queryInfo) {
        int pageStart = (queryInfo.getPageNum() - 1) * 7;
        return mapper.page(pageStart, queryInfo.getPageSize());
    }

    @Override
    public int getTotal() {
        return mapper.getTotal();
    }

    @Override
    public String login(User user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        UserDto userDto=new UserDto();
        userDto.setSession(user);
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.67");
        try {
            HttpEntity<String> res = restTemplate
                    .exchange("https://forest-china.upwardsware.com/api/v1/sessions?seekrua=extension_chrome-5.12.0"
                            , HttpMethod.POST, new HttpEntity<>(userDto, headers),
                            String.class);
            System.out.println(res.toString());
            if (res.hasBody()) {
                JSONObject login_res = JSONObject.parseObject(res.getBody());
                System.out.println(res.getBody());
                token = login_res.getString("remember_token");
                isLogin = true;
                userId=login_res.getString("user_id");
                return "获取token成功";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return "邮箱或密码错误";
        }

        return null;
    }

    @Override
    public Boolean isLogin() {
        return isLogin;
    }
}
