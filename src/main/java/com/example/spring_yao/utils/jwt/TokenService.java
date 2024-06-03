package com.example.spring_yao.utils.jwt;

import com.example.spring_yao.entity.UserBasicEntity;
import com.example.spring_yao.utils.jwt.loginreponse.LoginResponse;
import com.example.spring_yao.utils.jwt.loginrequest.LoginRequest;
import com.example.spring_yao.repository.UserBasicRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TokenService {
    private Key secretKey;

    private JwtParser jwtParser;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private UserBasicRepository userBasicRepository;

    @PostConstruct
    private void init() {
        try{
            //需注意此key長度需達到一定長度，否則編譯上會有錯誤
            String key = "YaoSpringBootPracticeForMyFeatureWorkProject";
            secretKey = Keys.hmacShaKeyFor(key.getBytes());

            jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        }catch (Exception e){
            log.error(String.format("密鑰產生錯誤 : %s",e.getMessage()));
        }
    }

    public LoginResponse createToken(LoginRequest request) {

        // 封裝帳密
        Authentication authToken = new UsernamePasswordAuthenticationToken(request.getAccount(), request.getPassword());
        // 執行帳密認證
        authToken = authenticationProvider.authenticate(authToken);

        // 以自定義的 UserBasicDetailsServiceImpl 認證成功後取得結果
        UserBasicDetails userDetails = (UserBasicDetails) authToken.getPrincipal();

        //產生accessToken
        Map<String,Object> accessToken = createAccessToken(userDetails.getUsername());

        //產生refreshToken
        Map<String,Object> refreshToken = createRefreshToken(userDetails.getUsername());

        //找出帳號資料，準備存入token
        UserBasicEntity userBasicEntity = userBasicRepository.getByAccount(request.getAccount());

        LoginResponse res = new LoginResponse();
        res.setAccessToken(accessToken.get("accessToken").toString());
        res.setAccessTokenDeadLine((Date) accessToken.get("accessTokenDeadLine"));
        res.setRefreshToken(refreshToken.get("refreshToken").toString());
        res.setRefreshTokenDeadLine((Date) refreshToken.get("refreshTokenDeadLine"));
        res.setUserName(userDetails.getRealUserName());
        res.setAccount(userDetails.getUsername());
        res.setUserAuthority(userDetails.getUserAuthority());
        res.setExpiryDate(userDetails.getExpiryDate());

        userBasicEntity.setToken(res.getAccessToken());
        userBasicRepository.save(userBasicEntity);

        return res;
    }

    private Map<String,Object> createAccessToken(String account) {
        // 有效時間（毫秒）
        long expirationMillis = Instant.now()
                .plusSeconds(3600)
                .getEpochSecond()
                * 1000;

        // 設置標準內容與自定義內容
        Claims claims = Jwts.claims();
        claims.setSubject("Access Token");
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(expirationMillis));
        claims.put("account", account);

        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact());
        map.put("accessTokenDeadLine",claims.getExpiration());

        // 簽名後產生 token
        return map;
    }

    public Map<String, Object> parseToken(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return new HashMap<>(claims);
    }

    private Map<String,Object> createRefreshToken(String account) {
        long expirationMillis = Instant.now()
                .plusSeconds(3600)
                .getEpochSecond()
                * 1000;

        Claims claims = Jwts.claims();
        claims.setSubject("Refresh Token");
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(expirationMillis));
        claims.put("account", account);

        Map<String,Object> map = new HashMap<>();
        map.put("refreshToken",Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact());
        map.put("refreshTokenDeadLine",claims.getExpiration());

        return map;
    }

    public Map<String,Object> refreshAccessToken(String refreshToken) {
        Map<String, Object> payload = parseToken(refreshToken);
        String account = (String) payload.get("account");

        return createAccessToken(account);
    }
}
