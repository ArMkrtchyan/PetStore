import am.store.PeetStore.user.AuthProvider
import am.store.PeetStore.user.jwt.JwtConfigurer
import am.store.PeetStore.user.jwt.JwtTokenProvider
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
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
                .antMatchers("/user/findAll").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler { httpServletRequest: HttpServletRequest?, httpServletResponse: HttpServletResponse, e: AccessDeniedException? ->
                    val out: OutputStream = httpServletResponse.outputStream
                    val mapper = ObjectMapper()
                    mapper.writeValue(out, HashMap<String, Any>().apply {
                        put("code", 400)
                        put("message", "Access denied")
                    })
                    out.flush()
                }
                .and()
                .apply(JwtConfigurer(jwtTokenProvider!!))
    }

    @Bean
    @Throws(Exception::class)
    fun passwordEncoder(): BCryptPasswordEncoder {
        val bCryptPasswordEncoder = BCryptPasswordEncoder()
        return bCryptPasswordEncoder
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