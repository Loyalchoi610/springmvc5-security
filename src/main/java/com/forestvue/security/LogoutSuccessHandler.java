package com.forestvue.security;


import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("utf-8");
        Map<String, String> body = new HashMap<>();
        body.put("status","success");

        try {
            PrintWriter out = httpServletResponse.getWriter();
            out.println(new Gson().toJson(body));
            out.close();
        }catch (Exception e){
            e.getMessage();
        }
    }
}
