package am.petstore.PetStore.user.jwt;

import am.petstore.PetStore.user.dao.DeviceDao;
import am.petstore.PetStore.user.dao.UserDao;
import am.petstore.PetStore.user.entity.UserModel;
import am.petstore.PetStore.user.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("aslkjedomjlwsdk")
    private String secretKey = "secret";
    private long validityInMilliseconds = 360000000; // 1h
    @Autowired
    private UserService userDetailsService;
    @Autowired
    UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DeviceDao deviceDao;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String phone, Collection<? extends GrantedAuthority> roles) {
        UserModel user = userDao.findByPhone(phone);
        Claims claims = Jwts.claims().setSubject(phone);
        claims.put("roles", roles);
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("phone", user.getId());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()//
                .setId(user.getId().toString())
                .setClaims(claims)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserModel userDetails = (UserModel) this.userDetailsService.loadUserByUsername(getPhone(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getPhone(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }


    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public void saveHeadersInfo(HttpServletRequest req) {
        try {
//            Device device = new Device();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            if (claims.getBody().getExpiration().before(new Date())) {
//                return false;
//            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }
}