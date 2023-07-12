package com.service;

import com.entity.*;

import java.util.HashMap;
import java.util.List;

public interface MainService {
    public List<Plants> getAllTrees();

    public Details getUserDetails();

    public List<DetailsData> page(QueryInfo queryInfo);

    public int getTotal();

    public String login(User user);

    public Boolean isLogin();
}
