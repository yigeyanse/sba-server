package com.hb.sba.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author xiaodong
 * @title
 * @date 2019/11/15 15:04
 * @desc
 */
@Configuration
@EnableWebSecurity
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
 private final String adminContextPath;

    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/");

        http.authorizeRequests()
            .antMatchers(adminContextPath + "/assets/**").permitAll()
            .antMatchers(adminContextPath + "/login").permitAll()
            .antMatchers(adminContextPath + "/img/**").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
        .logout().logoutUrl(adminContextPath + "/logout").and()
        .httpBasic().and()
        .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringAntMatchers(
                adminContextPath + "/instances",
                adminContextPath + "/actuator/**"
            );
        // @formatter:on
    }
}
