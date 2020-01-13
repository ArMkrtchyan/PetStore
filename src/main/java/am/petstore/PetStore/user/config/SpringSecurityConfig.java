package am.petstore.PetStore.user.config;

import am.petstore.PetStore.user.jwt.JwtConfigurer;
import am.petstore.PetStore.user.jwt.JwtTokenProvider;
import am.petstore.PetStore.user.model.ApiResponse;
import am.petstore.PetStore.user.service.AuthProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.OutputStream;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthProvider authProvider;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/signin").permitAll()
                .antMatchers("/user/signup/phone").permitAll()
                .antMatchers("/user/signup").permitAll()
                .antMatchers("/user/downloadFile/*").permitAll()
                .antMatchers("/user/device").permitAll()
                .antMatchers("/user/device/free").permitAll()
                .antMatchers(HttpMethod.GET, "/pets/findAll",
                        "/store/findAll",
                        "/product/findAll",
                        "/category/findAll").permitAll()
                .antMatchers("/user/findAll").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
            ApiResponse response = new ApiResponse(403, "Access Denied");
            response.setMessage("Access Denied");
            OutputStream out = httpServletResponse.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, response);
            out.flush();
        })
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider))
        ;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }


}
