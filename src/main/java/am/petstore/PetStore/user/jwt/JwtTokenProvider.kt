package am.petstore.PetStore.user.jwt

import am.petstore.PetStore.user.dao.DeviceDao
import am.petstore.PetStore.user.dao.UserDao
import am.petstore.PetStore.user.entity.UserModel
import am.petstore.PetStore.user.service.UserService
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider {
    @Value("aslkjedomjlwsdk")
    private var secretKey = "secret"
    private val validityInMilliseconds: Long = 360000000 // 1h
    @Autowired
    private val userDetailsService: UserService? = null
    @Autowired
    var userDao: UserDao? = null
    @Autowired
    private val bCryptPasswordEncoder: BCryptPasswordEncoder? = null
    @Autowired
    private val deviceDao: DeviceDao? = null

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(phone: String?, roles: Collection<GrantedAuthority?>?): String {
        val user = userDao!!.findByPhone(phone)
        val claims = Jwts.claims().setSubject(phone)
        claims["roles"] = roles
        claims["id"] = user?.id
        claims["email"] = user?.email
        claims["phone"] = user?.id
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)
        return Jwts.builder() //
                .setId(user?.id.toString())
                .setClaims(claims) //
                .signWith(SignatureAlgorithm.HS256, secretKey) //
                .compact()
    }

    fun getAuthentication(token: String?): Authentication {
        val userDetails = userDetailsService!!.loadUserByUsername(getPhone(token)) as UserModel
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getPhone(token: String?): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else null
    }

    fun saveHeadersInfo(req: HttpServletRequest?) {
        try { //            Device device = new Device();
//            device.setUid(req.getHeader("X-device-id"));
//            device.setApp_version(req.getHeader("X-version"));
//            device.setLanguage(req.getHeader("lang"));
//            device.setSdk_version(req.getHeader("X-version-sdk"));
//            device.setModel(req.getHeader("X-model"));
//            device.setPlatform(req.getHeader("X-platform"));
//            device.setFirst_install_date(req.getHeader("X-install-date"));
//            LoggerFactory.getLogger("JwtFilter").info(device.toString());
//            LoggerFactory.getLogger("JwtFilter").info("" + deviceDao);
//            LoggerFactory.getLogger("JwtFilter").info("" + deviceDao.existsByUid(device.getUid()));
//            if (device.getUid() != null) {
//                if (deviceDao.existsByUid(device.getUid())) {
//                    deviceDao.updateDeviceByUid(device.getUid(), device.getApp_version(), device.getFirst_install_date(), device.getLanguage(),
//                            device.getModel(), device.getPlatform(), device.getSdk_version());
//                } else {
//                    deviceDao.saveAndFlush(device);
//                }
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun validateToken(token: String?): Boolean {
        return try {
            val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            //            if (claims.getBody().getExpiration().before(new Date())) {
//                return false;
//            }
            true
        } catch (e: JwtException) {
            throw InvalidJwtAuthenticationException("Expired or invalid JWT token")
        } catch (e: IllegalArgumentException) {
            throw InvalidJwtAuthenticationException("Expired or invalid JWT token")
        }
    }
}