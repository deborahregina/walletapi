package com.dbc.walletapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and().cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers("/username").permitAll()
                .antMatchers("/mudar-senha").permitAll()
                .antMatchers("/list-servicos-mes-ano").permitAll()
                .antMatchers("/list-servicos-dolar").permitAll()
                .antMatchers("/list-servicos-euro").permitAll()
                .antMatchers("/list-servicos-real").permitAll()
                //usuários podem mudar senha caso estiverem autenticados
                .antMatchers("/auth/createGerente/").hasRole("ADMIN")
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()

                /* O ADM pode se autenticar e acessar todos os endpoints. o Gerente pode apenas     *
                * se autenticar e acessas seus dados por meio de /username, que retorna um objeto   *
                * com apenas suas informações, e mudar sua senha, através do endpoint /mudar-senha  */

                .and().addFilterBefore(new TokenAuthenticationFilter(tokenService),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui/**",
                "/v2/api-docs",
                "/swagger-ui.html",
                "/swagger-resources/**"
                );
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
