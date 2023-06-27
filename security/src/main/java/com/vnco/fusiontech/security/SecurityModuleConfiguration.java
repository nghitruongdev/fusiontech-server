package com.vnco.fusiontech.security;

import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity (prePostEnabled = true, securedEnabled = true)
@ComponentScan
@EntityScan ("com.vnco.fusiontech.security.entity")
@EnableJpaRepositories ("com.vnco.fusiontech.security.repository")
public class SecurityModuleConfiguration {
  
  @SneakyThrows
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) {
    http.csrf().disable();
    http.cors(Customizer.withDefaults());
    http.headers().frameOptions().sameOrigin()
        .and()
        .csrf()
        .ignoringRequestMatchers("/h2-console/**")
        .disable();
    // http.authorizeHttpRequests().requestMatchers("/h2-console",
    // "/h2-console/**").permitAll();
    // authorizeRequest(http);
    // http.httpBasic();
    addFilters(http);
    http.httpBasic().disable();
    return http.build();
    // todo: add http.anonymous()
  }
  
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    var config = new CorsConfiguration();
    config.addAllowedOrigin("http://localhost:3000");
    config.setAllowedHeaders(List.of("*"));
    //    config.addAllowedOriginPattern("*");
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("Access-Control-Allow-Origin"));
    config.setExposedHeaders(List.of("Content-Type", "Origin"));
    config.setAllowCredentials(false);
    config.setMaxAge(3600L);
  
    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
  
  @SneakyThrows
  private void addFilters(HttpSecurity http) {
//    http
//            .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
    // .apply(jwtConfigurerAdapter())
    ;
  }
  
//  private CorsFilter corsFilter() {
//    var source = new UrlBasedCorsConfigurationSource();
//    var config = new CorsConfiguration();
//    config.addAllowedOrigin("http://localhost:3000");
//    config.addAllowedOrigin("*");
//    config.addAllowedHeader("*");
//    config.addAllowedMethod("*");
//    config.setExposedHeaders(Arrays.asList("Content-Type"));
//    // var config = appProperties.corsConfig();
//    // if(!CollectionUtils.isEmpty(config.getAllowedOrigins()) ||
//    // !CollectionUtils.isEmpty(config
//    // .getAllowedOriginPatterns())){
//    // log.debug("Registering CORS filter");
//    // source.registerCorsConfiguration("/api/**", config);
//    // source.registerCorsConfiguration("/management/**", config);
//    // source.registerCorsConfiguration("/v3/api-docs", config);
//    // source.registerCorsConfiguration("/swagger-ui/**", config);
//    // }
//    source.registerCorsConfiguration("/**", config);
//    return new CorsFilter(source);
//  }
}
