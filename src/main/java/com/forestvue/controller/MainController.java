package com.forestvue.controller;

import com.forestvue.domain.MemberVO;
import com.forestvue.security.JwtGenerator;
import com.forestvue.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    JwtGenerator jwtGenerator;

    @Autowired
    MemberService memberService;

    @GetMapping(value="/")
    public String index(){
        return "index";

    }

    @GetMapping("/jwt")
    @ResponseBody
    public Object jwt(){
        Map<String,String> obj = new HashMap<>();
        MemberVO memberVO = memberService.getUserByName("spring");
        obj.put("jwt",jwtGenerator.generateToken(memberVO));
        return obj;
    }

    @GetMapping(value = "/ajaxlogin")
    public String login(){
        return "ajaxlogin";
    }
    @GetMapping(value = "/register")
    public String register(){
        return "register";
    }
    @RequestMapping(value="/auth-failure", produces = "application/json")
    @ResponseBody
    public Object failed(){
        Map<String, String> m = new HashMap<>();
        m.put("auth", "failed");
        return m;
    }
    @GetMapping(value="/auth-success", produces = "application/json")
    @ResponseBody
    public Object success(){
        Map<String, String> m = new HashMap<>();
        m.put("auth", "success");
        return m;
    }
}

