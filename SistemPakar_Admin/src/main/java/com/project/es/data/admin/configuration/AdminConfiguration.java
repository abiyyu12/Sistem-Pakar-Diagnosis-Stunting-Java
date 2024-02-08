package com.project.es.data.admin.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AdminConfiguration {
    @Bean
    public UserDetailsService userDetailsServiceAdmin(){
        return new AdminServiceConfig();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoderAdmin(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) ->
                web.ignoring()
                        .requestMatchers("/stisla-master/**");
    }

    @Bean
    public SecurityFilterChain filterChainAdmin(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsServiceAdmin())
                .passwordEncoder(passwordEncoderAdmin());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        http.csrf(c -> c.disable())
                .authorizeHttpRequests(request -> request.requestMatchers("/admin/**")
                        .hasAuthority("ADMIN")
                        .requestMatchers("/*","/static/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").loginProcessingUrl("/do-login").usernameParameter("email")
                        .defaultSuccessUrl("/home?login=true",true)
                        .permitAll())
                .logout(form -> form
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login?logout").permitAll().invalidateHttpSession(true)
                        .clearAuthentication(true))
                .authenticationManager(authenticationManager);
        return http.build();
    }

}
