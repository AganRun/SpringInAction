package com.agan.tacocloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityJdbcConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        //自定义一个加密流程
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                //加1加密，哈哈
                System.out.println("encode" + charSequence);
                return charSequence.toString() + 1;
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                System.out.println("match,charSequence:" + charSequence + ", dbPassword:" + s);
                return encode(charSequence).equals(s);
            }
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from Users where username = ?"
                )
                .authoritiesByUsernameQuery(
                        "select username, authority from UserAuthorities where username = ?"
                );
    }

}
