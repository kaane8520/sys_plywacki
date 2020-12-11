package com.my_app.sys_plywacki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http

            .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/registration", "/searchClubs", "/serachCompetitions", "/searchPlayers").permitAll()
                .antMatchers("/insertResults", "/acceptApplications", "/registrationCompetitions",
                        "/organizerCompetitionView", "/refereesOnCompetitionView","/searchApplication").hasAuthority("organizator")
                .antMatchers("/judgingCompetitions", "/chooseCompetitionForReferee").hasAuthority("sedzia")
                .antMatchers("/registrationCompetitorsCoach", "/registrationClub", "/regClubForCompetition").hasAuthority("trener")
                .antMatchers("/registrationCompetitiorsPlayer","/test").hasAuthority("trener")
                .antMatchers("/verificationMedicalExaminations", "/editCoach", "/editPlayerByModerator", "/editReferee").hasAuthority("Moderator")
                .antMatchers("/editPlayer").hasAuthority("zawodnik")
                .antMatchers("/welcome","/changeYourRole","/seeAcceptedRegistrations","/searchPlayerResults/**","/addDocumentation").authenticated()
                .antMatchers("/edit").hasAnyAuthority("trener","zawodnik","sedzia")
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}