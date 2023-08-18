package com.vnco.fusiontech.security;

import com.google.firebase.auth.FirebaseToken;
import com.vnco.fusiontech.security.filter.FirebaseTokenFilter;
import com.vnco.fusiontech.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan
@RequiredArgsConstructor
public class SecurityModuleConfiguration {
    private final SecurityService securityService;

    @SneakyThrows
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {
        http.cors();
        http.headers().frameOptions().sameOrigin()
                .and()
                .csrf()
                .ignoringRequestMatchers("/h2-console/**");
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable(); // be careful when disable this
        http.httpBasic().disable(); // disable HTTP basic authentication
        http.authorizeHttpRequests()
//                .requestMatchers("**").permitAll();
                .requestMatchers(HttpMethod.PATCH, "/api/auth/update-profile").authenticated()
                .requestMatchers(HttpMethod.GET,
                        "/api/brands",
                        "/api/products/**",
                        "/api/categories/**",
                        "/api/reviews/**",
                        "/api/vouchers/**",
                        "/api/variants/**").permitAll()
                .requestMatchers("/api/statistical/best-seller").permitAll()
                .requestMatchers("/api/auth/register").permitAll()
                .requestMatchers("/api/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated();
        addFilters(http);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var config = new CorsConfiguration();
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedOriginPatterns(List.of("http://*:3000"));
        //    config.addAllowedOriginPattern("*");
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Access-Control-Allow-Origin", "Content-Type", "Authorization"));
        config.setExposedHeaders(List.of("Content-Type", "Origin"));
        config.setAllowCredentials(false);
        config.setMaxAge(3600L);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @SneakyThrows
    private void addFilters(HttpSecurity http) {
        http.addFilterBefore(new FirebaseTokenFilter(securityService), UsernamePasswordAuthenticationFilter.class);
    }

//    private CorsFilter corsFilter() {
//        var source = new UrlBasedCorsConfigurationSource();
//        var config = new CorsConfiguration();
//        config.addAllowedOrigin("http://100.107.221.79:3000/");
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        config.setExposedHeaders(Arrays.asList("Content-Type"));
//        // var config = appProperties.corsConfig();
//        // if(!CollectionUtils.isEmpty(config.getAllowedOrigins()) ||
//        // !CollectionUtils.isEmpty(config
//        // .getAllowedOriginPatterns())){
//        // log.debug("Registering CORS filter");
//        // source.registerCorsConfiguration("/api/**", config);
//        // source.registerCorsConfiguration("/management/**", config);
//        // source.registerCorsConfiguration("/v3/api-docs", config);
//        // source.registerCorsConfiguration("/swagger-ui/**", config);
//        // }
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
}
