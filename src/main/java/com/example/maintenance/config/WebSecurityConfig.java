package com.example.maintenance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                    .disable()

                .authorizeRequests()
                    // Доступ только для незарегистрированных пользователей
                    .antMatchers("/registration").not().fullyAuthenticated()
                    // Доступ только для пользователей с ролью Администратор
                    .antMatchers(
                            "/users/**",
                            "/api/user/**"
                    ).hasRole("ADMIN")
                    // Доступ разрешен всем пользователям
                    .antMatchers(
                            "/",
                            "/report",
                            "/api/action/report",
                            "/resources/**",
                            "/static/**"
                    ).permitAll()

                // Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()

                .and()
                    // Настройка для входа в систему
                    .formLogin()
                    .loginPage("/login")
                    // Перенарпавление на главную страницу после успешного входа
                    .defaultSuccessUrl("/")
                    .permitAll()

                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessUrl("/");
    }
}
