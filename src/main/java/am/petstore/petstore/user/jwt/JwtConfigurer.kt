package am.petstore.petstore.user.jwt

import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtConfigurer(private val jwtTokenProvider: JwtTokenProvider) : SecurityConfigurerAdapter<DefaultSecurityFilterChain?, HttpSecurity>() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        val customFilter = JwtTokenFilter(jwtTokenProvider)
        http.exceptionHandling()
                .authenticationEntryPoint(JwtAuthenticationEntryPoint())
                .and().addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

}