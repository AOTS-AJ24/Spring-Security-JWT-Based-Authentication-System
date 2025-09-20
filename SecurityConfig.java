package com.abhi.SpringSecEx.Config;

import com.abhi.SpringSecEx.Filter.JwtFilter;
import com.abhi.SpringSecEx.Services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService muds;


    @Autowired
    private JwtFilter Jwt_fil;

    //dependency injection
    public SecurityConfig(MyUserDetailsService muds){
        this.muds=muds;
    }


    @Bean
    public SecurityFilterChain sfc(HttpSecurity httpS)throws Exception{

//        httpS.csrf(customizer ->customizer.disable());
//        httpS.authorizeHttpRequests(request->request.anyRequest().authenticated());
////        httpS.formLogin(Customizer.withDefaults()); // starts login page to authenticate
//        httpS.httpBasic(Customizer.withDefaults()); // now can see data in postman
//        httpS.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//        return httpS.build();


        //this is builder pattern method  -- makes it neat and clean
        return httpS
                .csrf(AbstractHttpConfigurer::disable) //replacing lambda with method reference
                .authorizeHttpRequests(request->request
                        .requestMatchers("register" , "login") // no we dont neewd auth for these 2 links
                        .permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(Jwt_fil ,UsernamePasswordAuthenticationFilter.class)
                .build();

        //httpS.formLogin(Customizer.withDefaults());

//        now to  set password from memory
    }

    @Bean
    public UserDetailsService uDs() {

        UserDetails user1 = User.withDefaultPasswordEncoder()
                .username("Pravin")
                .password("123")
                .roles("User")
                .build();

        UserDetails user2 = User
                .withDefaultPasswordEncoder()
                .username("Aman")
                .password("12345")
                .roles("ADMIN")
                .build();

        return new  InMemoryUserDetailsManager(user1,user2);  // this cannot defrenciate between aman & Aman

//        // this can defrenciate between aman & Aman
//       List<UserDetails>  users= List.of(user1,user2);
//        return new InMemoryUserDetailsManager(users) {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                return users.stream()
//                        .filter(u -> u.getUsername().equals(username)) // case-sensitive
//                        .findFirst()
//                        .orElseThrow(() ->
//                                new UsernameNotFoundException("User not found: " + username));
//            }// this can defrenciate between aman & Aman
//        };
    }

    @Bean
    public AuthenticationProvider auth_Provider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());  //when bcrypt not applied
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService((UserDetailsService) muds);
        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration confi) throws Exception {
        return confi.getAuthenticationManager();

    }

}
















































/*

| **Order** | **Filter Name**                             | **Purpose / Responsibility**                                                     |
| --------- | ------------------------------------------- | -------------------------------------------------------------------------------- |
| 1         | `ChannelProcessingFilter`                   | Enforces security channel (HTTP vs HTTPS).                                       |
| 2         | `WebAsyncManagerIntegrationFilter`          | Integrates `SecurityContext` with Spring’s `WebAsyncManager` for async requests. |
| 3         | `SecurityContextPersistenceFilter`          | Loads `SecurityContext` for the request, stores it back after completion.        |
| 4         | `HeaderWriterFilter`                        | Adds security headers (X-Frame-Options, HSTS, CSP, etc.).                        |
| 5         | `CorsFilter`                                | Handles Cross-Origin Resource Sharing (CORS) preflight and request headers.      |
| 6         | `CsrfFilter`                                | Protects against Cross-Site Request Forgery (CSRF) attacks.                      |
| 7         | `LogoutFilter`                              | Processes logout URL, clears authentication/session.                             |
| 8         | `OAuth2AuthorizationRequestRedirectFilter`  | Handles OAuth2 authorization requests (redirects to providers).                  |
| 9         | `OAuth2LoginAuthenticationFilter`           | Processes OAuth2 login responses.                                                |
| 10        | `OidcAuthorizationCodeAuthenticationFilter` | Processes OpenID Connect (OIDC) authentication.                                  |
| 11        | `Saml2WebSsoAuthenticationRequestFilter`    | Initiates SAML2 login.                                                           |
| 12        | `Saml2WebSsoAuthenticationFilter`           | Processes SAML2 login responses.                                                 |
| 13        | `UsernamePasswordAuthenticationFilter`      | Processes form-based login (username/password).                                  |
| 14        | `OpenIDAuthenticationFilter` *(legacy)*     | Handles OpenID login.                                                            |
| 15        | `DefaultLoginPageGeneratingFilter`          | Generates default login page if custom not provided.                             |
| 16        | `DefaultLogoutPageGeneratingFilter`         | Generates default logout page if custom not provided.                            |
| 17        | `ConcurrentSessionFilter`                   | Limits/maximizes concurrent user sessions.                                       |
| 18        | `DigestAuthenticationFilter`                | Handles HTTP Digest Authentication.                                              |
| 19        | `BearerTokenAuthenticationFilter`           | Handles Bearer/JWT token authentication.                                         |
| 20        | `BasicAuthenticationFilter`                 | Handles HTTP Basic Authentication.                                               |
| 21        | `RequestCacheAwareFilter`                   | Restores saved requests after login.                                             |
| 22        | `SecurityContextHolderAwareRequestFilter`   | Wraps request to add Spring Security-aware methods.                              |
| 23        | `JaasApiIntegrationFilter`                  | Integrates with JAAS API (Java Authentication & Authorization Service).          |
| 24        | `RememberMeAuthenticationFilter`            | Processes Remember-Me authentication.                                            |
| 25        | `AnonymousAuthenticationFilter`             | Provides anonymous authentication if no user is logged in.                       |
| 26        | `SessionManagementFilter`                   | Protects against session fixation and manages invalid sessions.                  |
| 27        | `ExceptionTranslationFilter`                | Handles access-denied and authentication-entry-point exceptions.                 |
| 28        | `FilterSecurityInterceptor`                 | Final filter: enforces URL/Method security (role/authority checks).              |
| 29        | `SwitchUserFilter`                          | Allows an admin to “switch” into another user account.                           |


 */