package com.vnco.fusiontech.security;

import com.google.firebase.auth.FirebaseToken;
import com.vnco.fusiontech.security.filter.FirebaseTokenFilter;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan
public class SecurityModuleConfiguration {
    FirebaseToken token;
    final
    FirebaseTokenFilter firebaseTokenFilter;

    public SecurityModuleConfiguration(FirebaseTokenFilter firebaseTokenFilter) {
        this.firebaseTokenFilter = firebaseTokenFilter;
    }

    @SneakyThrows
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {
        http.cors(Customizer.withDefaults());
        http.headers().frameOptions()
                .sameOrigin()
                .and()
                .csrf()
                .ignoringRequestMatchers("/h2-console/**");
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.csrf(Customizer.withDefaults());
        http.httpBasic().disable();
        http.authorizeHttpRequests().requestMatchers("/**")
                .permitAll();
        addFilters(http);
        return http.build();
        //    addFilters(http);
        // todo: add http.anonymous()
        //    http.csrf().disable();
        // http.authorizeHttpRequests().requestMatchers("/h2-console",
        // "/h2-console/**").permitAll();
        // authorizeRequest(http);
        // http.httpBasic();

    }

    //  @Bean
    //  public CorsConfigurationSource corsConfigurationSource() {
    //    var config = new CorsConfiguration();
    //    config.addAllowedOrigin("http://localhost:3000");
    //    config.setAllowedHeaders(List.of("*"));
    //    //    config.addAllowedOriginPattern("*");
    //    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    //    config.setAllowedHeaders(List.of("Access-Control-Allow-Origin"));
    //    config.setExposedHeaders(List.of("Content-Type", "Origin"));
    //    config.setAllowCredentials(false);
    //    config.setMaxAge(3600L);
    //
    //    var source = new UrlBasedCorsConfigurationSource();
    //    source.registerCorsConfiguration("/**", config);
    //    return source;
    //  }


    @SneakyThrows
    private void addFilters(HttpSecurity http) {
//            http
//                    .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
//         .apply(jwtConfigurerAdapter())
//        ;
        http.addFilterBefore(firebaseTokenFilter, UsernamePasswordAuthenticationFilter.class);
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
