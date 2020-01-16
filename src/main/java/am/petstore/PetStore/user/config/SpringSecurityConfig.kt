package am.petstore.PetStore.user.config

import am.petstore.PetStore.user.jwt.JwtConfigurer
import am.petstore.PetStore.user.jwt.JwtTokenProvider
import am.petstore.PetStore.user.model.ApiResponse
import am.petstore.PetStore.user.service.AuthProvider
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.io.OutputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class SpringSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private val authProvider: AuthProvider? = null
    @Autowired
    private val jwtTokenProvider: JwtTokenProvider? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
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
                .antMatchers("/pets/create",
                        "/pets/update",
                        "/pets/delete",
                        "/store/create",
                        "/store/update",
                        "/store/delete",
                        "/product/create",
                        "/product/update",
                        "/product/delete",
                        "/category/create",
                        "/category/update",
                        "/category/delete"
                ).hasAnyAuthority("EDITOR","ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(JwtConfigurer(jwtTokenProvider!!))
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authProvider)
    }
}