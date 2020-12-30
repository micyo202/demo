package com.example.demo.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * AuthController
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/12/22
 */
@RestController
public class AuthController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/login/mobile")
    public Map<String, Object> loginMobile(String phone, String smsCode) {
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("client_id", "client");
        param.add("client_secret", "secret");
        param.add("scope", "all");
        param.add("grant_type", "phone_sms");
        param.add("phone", phone);
        param.add("sms_code", smsCode);
        Map<String, Object> result = restTemplate.postForObject("http://localhost:8888/oauth/token", param, Map.class);
        return result;
    }

    @PostMapping("/login/password")
    public Map<String, Object> loginPassword(String username, String password) {
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("client_id", "client");
        param.add("client_secret", "secret");
        param.add("scope", "all");
        param.add("grant_type", "password");
        param.add("username", username);
        param.add("password", password);
        Map<String, Object> result = restTemplate.postForObject("http://localhost:8888/oauth/token", param, Map.class);
        return result;
    }

    @GetMapping("/hi")
    public String hi() {
        return "Hi... 无需token访问";
    }

    @GetMapping("/init")
    public String init() {
        return "Hello init... 需要token访问";
    }

    @GetMapping("/admin/init")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminInit() {
        return "Hello admin init... 需要具有ADMIN权限的token访问";
    }

    @GetMapping("/user/init")
    @PreAuthorize("hasAuthority('USER')")
    public String userInit() {
        return "Hello user init... 需要具有USER权限的token访问";
    }

}
