package lk.ijse.crop_monitoring_backend.config;

import lk.ijse.crop_monitoring_backend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwFilter jwFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        "/crop",
                        "/crop/{cropCode}",
                        "crop/all_crops",
                        "/crop/category",
                        "/crop/season",
                        "/equipment",
                        "/equipment/{equipmentId}",
                        "/equipment/names",
                        "/equipment/all_equip",
                        "/equipment/status",
                        "/equipment/types",
                        "/field",
                        "/field/{fieldCode}",
                        "/field/names",
                        "/field/all_fields",
                        "/field_staff",
                        "/log",
                        "/log/{logCode}",
                        "/log/all_logs",
                        "/staff",
                        "/staff/{staffId}",
                        "/staff/authenticate",
                        "staff/genders",
                        "/staff/designations",
                        "/staff/roles",
                        "/vehicle",
                        "/vehicle/{vehicleCode}",
                        "/vehicle/fuel",
                        "/vehicle/all_vehicles",
                        "/vehicle/status",
                        "/vehicle/categories"
                ).permitAll().anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwFilter, UsernamePasswordAuthenticationFilter.class).build();
    }
}

