package com.example.demo.auth.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserDetailsServiceImpl
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/12/22
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<String> roleCodeList = Arrays.asList("ADMIN");
        Set<GrantedAuthority> authorities =
                roleCodeList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

        return new User("admin", "{bcrypt}$2a$10$zt3xDTDmnFFZdzaTZSPUhu.ZhvQYijtGpj4y5BrkBn/6lKi/SQQZ2", authorities);
    }

    public UserDetails loadUserByPhone(String phone, String code) {
        //在验证手机号和验证码
        if (!"123456".equals(code)) {
            throw new InvalidGrantException("验证码错误或已过期");
        }
        return new User(phone, "", AuthorityUtils.createAuthorityList("USER"));
    }

}
