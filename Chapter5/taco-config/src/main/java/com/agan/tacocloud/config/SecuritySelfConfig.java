package com.agan.tacocloud.config;

import com.agan.tacocloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecuritySelfConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Bean
    protected PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/design", "/orders")
//                    .hasRole("USER")
                    .access("hasRole('USER')")     // design 和 orders 路径，需要有user角色
                .antMatchers("/", "/**")
//                    .permitAll();
                    .access("permitAll")    //对于/和/**路径不拦截
            .and()
                .formLogin()
                    .loginPage("/login")   //登录页面
                    .defaultSuccessUrl("/design")  //成功后重定向到design页面
            ;
    }
}
