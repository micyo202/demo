package com.example.demo.auth.mobile;

import com.example.demo.auth.service.UserDetailsServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

/**
 * PhoneSmsTokenGranter
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/12/23
 */
public class PhoneSmsTokenGranter extends CustomAbstractTokenGranter {

    private static final String PHONE_SMS = "phone_sms";

    private UserDetailsServiceImpl userDetailsService;

    public PhoneSmsTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, UserDetailsServiceImpl userDetailsService) {
        super(tokenServices, clientDetailsService, requestFactory, PHONE_SMS);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected UserDetails getUserDetails(Map<String, String> parameters) {
        String phone = parameters.get("phone");
        String smsCode = parameters.get("sms_code");
        return userDetailsService.loadUserByPhone(phone, smsCode);
    }
}
