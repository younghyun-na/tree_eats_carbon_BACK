package smu.earthranger.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import smu.earthranger.config.auth.MemberDetailsService;
import smu.earthranger.config.oauth.Oauth2DetailsService;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberDetailsService memberDetailsService;
    private final Oauth2DetailsService oauth2DetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("member/login", "member/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("member/login")
                .permitAll()
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("member/login")
                .invalidateHttpSession(true)
                .and()
                .oauth2Login()   // oauth2Login에 대한 설정 시작
                .loginPage("member/login")
                .userInfoEndpoint()    // oauth2Login 성공 이후의 설정을 시작
                .userService(oauth2DetailsService)  //oauth2DetailsService에서 처리하겠다.
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}

