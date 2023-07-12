package com.controller;

import com.entity.*;
import com.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    private MainService mainService;


    @GetMapping("/getAllTrees")
    public List<Plants> getAllTrees(){
        return mainService.getAllTrees();
    }

    @GetMapping("/details")
    public Details getUserDetails(){
        return mainService.getUserDetails();
    }


    @GetMapping("/page")
    public List<DetailsData> page(QueryInfo queryInfo){
        return mainService.page(queryInfo);
    }

    @GetMapping("/total")
    public int getTotal(){
        return mainService.getTotal();
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return mainService.login(user);
    }

    @GetMapping("/isLogin")
    public Boolean isLogin(){
        return mainService.isLogin();
    }
}
