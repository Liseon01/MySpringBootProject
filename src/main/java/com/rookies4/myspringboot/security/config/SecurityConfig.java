package com.rookies4.myspringboot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // [DISABLED]
import org.springframework.security.web.SecurityFilterChain;

// ✅ 추가 import
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
// @EnableMethodSecurity  // [DISABLED] 메서드 단위 보안까지 완전 비활성
public class SecurityConfig {

    // ✅ Encoder Bean만 복구(서비스에서 의존성 주입 필요)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // 또는 Delegating 사용 원하면:
        // return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // [CHANGED] CSRF 비활성(REST 개발용)
                .csrf(csrf -> csrf.disable())

                // [CHANGED] 전 요청 허용
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

                // [CHANGED] 폼 로그인/Basic/로그아웃 전부 비활성 → /login HTML 리다이렉트 방지
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .logout(logout -> logout.disable())

                // (선택) H2 콘솔 프레임 허용
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    // ⚠ 아래 Bean들은 “전부 허용 모드”에선 불필요하니 계속 비활성 유지
    // @Bean public UserDetailsService userDetailsService() { ... }
    // @Bean public AuthenticationProvider authenticationProvider() { ... }
    // @Bean public UserDetailsService inMemoryUsers(PasswordEncoder encoder) { ... }
}
