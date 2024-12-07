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
                        "/backend/user/{id}",
                        "/backend/user",
                        "/backend/user/register",
                        "/backend/user/authenticate",
                        "/crop",
                        "/crop/get/{cropCode}",
                        "/crop/update/{cropCode}",
                        "/crop/delete/{cropCode}",
                        "crop/all_crops",
                        "/crop/category",
                        "/crop/season",
                        "/equipment",
                        "/equipment/get/{equipmentId}",
                        "/equipment/update/{equipmentId}",
                        "/equipment/delete/{equipmentId}",
                        "/equipment/names",
                        "/equipment/all_equip",
                        "/equipment/status",
                        "/equipment/types",
                        "/field",
                        "/field/get/{fieldCode}",
                        "/field/update/{fieldCode}",
                        "/field/delete/{fieldCode}",
                        "/field/names",
                        "/field/all_fields",
                        "/field_staff",
                        "/field_staff/get/{field_staff_id}",
                        "/field_staff/delete/{field_staff_id}",
                        "/field_staff/all_assigns",
                        "/log",
                        "/log/get/{logCode}",
                        "/log/update/{logCode}",
                        "/log/delete/{logCode}",
                        "/log/all_logs",
                        "/staff",
                        "/staff/delete/{staffId}",
                        "/staff/update/{staffId}",
                        "/staff/get/{staffId}",
                        "/staff/getByEmail/{email}",
                        "/staff/all_staff",
                        "staff/genders",
                        "/staff/designations",
                        "/staff/roles",
                        "/vehicle",
                        "/vehicle/get/{vehicleCode}",
                        "/vehicle/update/{vehicleCode}",
                        "/vehicle/delete/{vehicleCode}",
                        "/vehicle/fuel",
                        "/vehicle/all_vehicles",
                        "/vehicle/status",
                        "/vehicle/categories"
                ).permitAll().anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwFilter, UsernamePasswordAuthenticationFilter.class).build();
    }
}

