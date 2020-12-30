package com.example.demo.auth.mobile;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * CustomAbstractTokenGranter
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/12/23
 */
public abstract class CustomAbstractTokenGranter extends AbstractTokenGranter {

    CustomAbstractTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        //Map<String, String> parameters = tokenRequest.getRequestParameters();
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        UserDetails details = getUserDetails(parameters);
        if (null == details) {
            throw new InvalidGrantException("账户未找到");
        }
        Authentication userAuth = new UsernamePasswordAuthenticationToken(details.getUsername(),
                details.getPassword(), details.getAuthorities());

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);

        /*
       	这段已经弃用了，用这种写法会出现即使你重写了getAuthorities方法，访问也没有权限403，让你产生明明给你登录的用户设置了角色或者权限，但是却访问无权限403的错觉。这种写法是没有将authorities（OAuth2Authentication的父类AbstractAuthenticationToken的属性 ）的值给设置上去，authorities永远都是一个空数组。
        OAuth2Authentication authentication = super.getOAuth2Authentication(client, tokenRequest);
        authentication.setDetails(details);
        authentication.setAuthenticated(true);
        return authentication;*/
        //![在这里插入图片描述](https://img-blog.csdnimg.cn/20191228002332851.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzMzMzk2NjA4,size_16,color_FFFFFF,t_70)
    }

    /**
     * 自定义获取用户信息
     */
    protected abstract UserDetails getUserDetails(Map<String, String> parameters);

}
