package am.store.PeetStore.user.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtTokenFilter(jwtTokenProvider: JwtTokenProvider) : GenericFilterBean() {
    private val jwtTokenProvider: JwtTokenProvider
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, res: ServletResponse, filterChain: FilterChain) {
        jwtTokenProvider.saveHeadersInfo(req as HttpServletRequest)
        val token: String? = jwtTokenProvider.resolveToken(req)
        token?.let {
            if (jwtTokenProvider.validateToken(token)) {
                SecurityContextHolder.getContext().authentication = jwtTokenProvider.getAuthentication(token)
            }
        }
        filterChain.doFilter(req, res)
    }

    init {
        this.jwtTokenProvider = jwtTokenProvider
    }
}