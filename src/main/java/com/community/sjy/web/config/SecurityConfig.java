package com.community.sjy.web.config;

import com.community.sjy.web.config.auth.PrincipalDetailService;
import com.community.sjy.web.filtter.JwtAuthentication;
import com.community.sjy.web.filtter.JwtAuthorizationFilter;
import com.community.sjy.web.filtter.SecurityFilter;
import com.community.sjy.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 잇게 하는것
@Configuration
@EnableWebSecurity // 시큐리티 필터를 추가 = 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다.
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final CorsFilter corsFilter;

    private UserRepository userRepository;

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    public BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    }

    //시큐리티가 대신 로그인해주는데 password 가로채기 하는데
    //해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
    //같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
/*
       CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지 설정
        config.addAllowedOrigin(CorsConfiguration.ALL); // 모든 ip에 응답을 허용하겠다
        config.addAllowedHeader(CorsConfiguration.ALL); // 모든 Header에 응답을 허용하겠다
        config.addAllowedMethod(CorsConfiguration.ALL); // 모든 post , get , put , delete , patch 요청을 허용하겠다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/*", config);
*/

              http.addFilterBefore(new SecurityFilter(), SecurityContextPersistenceFilter.class);
              http.csrf().disable(); // csrf 토큰 비활성화 추후에 할수 있으면 열 예정
              http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션사용 x
              .and()
              .addFilter(corsFilter) // @CrossOrigin 인증x / 시큐리티 필터에 등록 인증o
              .httpBasic().disable() // bearer 인증 방식 사용하겠다
              .formLogin().disable()
                      .addFilter(new JwtAuthentication(authenticationManager()))
                      .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
              .authorizeHttpRequests()
              .antMatchers("/" ,"/auth/**" , "/js/**" , "/css/**", "/image/**")
              .permitAll()
              .anyRequest()
              .authenticated();

   //            .and().cors().configurationSource(source);

  /*           //   .and().cors().configurationSource(source);
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/" ,"/auth/**" , "/js/**" , "/css/**", "/image/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc")
                .defaultSuccessUrl("/")
                .and().cors().configurationSource(source);
*/
    }
}
